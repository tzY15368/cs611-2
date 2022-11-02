public class HeroMonsterGame extends Game{

    private Playground playground;
    private int playerInitialX = 0;
    private int playerInitialY = 0;

    public HeroMonsterGame(){
        this.playground = new Playground(new HeroMonsterSpaceFactory(),playerInitialX,playerInitialY);
    }

    @Override
    public void start() {
        System.out.println("play");
        playground.printBoard();
        // while(true){

        // }
    }
}
