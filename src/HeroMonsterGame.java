import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;



public class HeroMonsterGame extends Game{

    private Playground playground;
    private SquadHolder[] shs;
    private Squad playerSquad;
    private final KeyInput[] movementKeys = new KeyInput[]{KeyInput.W, KeyInput.A, KeyInput.S, KeyInput.D};


    public HeroMonsterGame(String configPath){
        super(configPath, new TerminalIODriver());
        Supplier<String> getinfo = ()->this.getInfo();
        this.io.registerShowInfo(getinfo);
        Supplier<String> getMap = () -> this.getMap();
        this.io.registerShowMap(getMap);
        Squad squad = new Squad("Player-squad",new ArrayList<>(),io, new AllToAllFightTurnStrategy(io));
        this.playerSquad = squad;


        HeroEntityFactory factory = new HeroEntityFactory(io);
        factory.fillSquad(squad);

        this.io.showInfo("Got heroes: \n"+squad.toDetailString());

        SquadHoldable[] sh = new SquadHolder[]{
                new SquadHolder(squad, new Pos(0,0)),
        };
        this.shs = (SquadHolder[]) sh;

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
        if(this.playerSquad==null){
            return "Game hasn't started yet!\n";
        }
        String status = this.playerSquad.toDetailString();
        Squad opponent = playerSquad.getCurrentOpponent();
        if(opponent!=null){
            status += "\nOpponent:\n" + opponent.toDetailString();
        }
        return status;
    }

    public String getMap(){
        if(this.playground==null){
            return "Game hasn't started yet!\n";
        }
        return playground.showBoard();
    }
}
