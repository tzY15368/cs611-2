import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HeroMonsterGame extends Game{

    private Playground playground;
    private Pos initialPos = new Pos(0,0);
    private IODriver io = new TerminalIODriver();
    private SquadHolder[] shs;
    private final KeyInput[] movementKeys = new KeyInput[]{KeyInput.W, KeyInput.A, KeyInput.S, KeyInput.D};
    private final KeyInput[] confirmKeys = new KeyInput[]{KeyInput.Y, KeyInput.N};

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

    public HeroMonsterGame(){
        SquadHoldable[] sh = new SquadHolder[]{
                new SquadHolder(new Squad("empty-squad"), new Pos(0,0)),
        };
        this.shs = (SquadHolder[]) sh;
        this.playground = new Playground(new HeroMonsterSpaceFactory(), sh, io);
    }
    @Override
    public void start() {
        System.out.println("playing with "+ this.shs.length+" squads");
        playground.printBoard();
        while(true){
            for(SquadHolder sh: shs){
                KeyInput ki = io.getKeyInput(movementKeys);
                playground.handleMove(ki.toMoveDir(),sh.getSquad());

                playground.printBoard();

            }
        }
    }
}
