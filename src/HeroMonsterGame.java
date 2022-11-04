public class HeroMonsterGame extends Game{

    private Playground playground;
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

    public HeroMonsterGame(String configPath){
        super(configPath);
        Squad emptySquad = new Squad("empty-squad");
        Squad s2 = new Squad("squad-test",new Entity[]{
                new HeroEntity("hero1",999,999,io),
                new HeroEntity("hero2",888,888,io),
        });
        SquadHoldable[] sh = new SquadHolder[]{
                new SquadHolder(s2, new Pos(0,0)),
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
