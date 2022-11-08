import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HeroMonsterGame extends Game{

    private Playground playground;
    private SquadHolder[] shs;
    private final KeyInput[] movementKeys = new KeyInput[]{KeyInput.W, KeyInput.A, KeyInput.S, KeyInput.D};


    private class SquadHolder implements SquadHoldable{
        private Squad squad;
        private Pos pos;

        public SquadHolder(Squad s, Pos p){
            squad = s;
            pos = p;
        }

        @Override
        public Squad getSquad() {
            return this.squad;
        }

        @Override
        public void setPos(Pos p) {
            this.pos = p;
        }

        @Override
        public Pos getPos() {
            return this.pos;
        }
    }

    public HeroMonsterGame(String configPath){
        super(configPath, new TerminalIODriver());
        Squad squad = new Squad("Player-squad",new ArrayList<>(),io, new AllToAllFightTurnStrategy(io));
        HeroEntityFactory factory = new HeroEntityFactory(io);
        factory.fillSquad(squad);

        this.io.showInfo("Got heroes: \n"+squad.toDetailString());

        SquadHoldable[] sh = new SquadHolder[]{
                new SquadHolder(squad, new Pos(0,0)),
        };
        this.shs = (SquadHolder[]) sh;
        Supplier<String> getinfo = ()->this.getInfo();
        this.io.registerShowInfo(getinfo);
        Supplier<String> getMap = () -> this.getMap();
        this.io.registerShowMap(getMap);

        this.playground = new Playground(new HeroMonsterSpaceFactory(), sh, io);
    }
    @Override
    public void start() {
        System.out.println("playing with "+ this.shs.length+" squads");
        io.showInfo(playground.showBoard());
        while(true){
            for(SquadHolder sh: shs){
                KeyInput ki = io.getKeyInput(movementKeys);
                playground.handleMove(ki.toMoveDir(),sh.getSquad());
                io.showInfo(playground.showBoard());

            }
        }
    }

    public String getInfo(){
        return "current state as info";
    }

    public String getMap(){
        return playground.showBoard();
    }
}
