import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HeroMonsterGame extends Game{

    private Playground playground;
    private Pos initialPos = new Pos(0,0);
    private IODriver io = new TerminalIODriver();
    private final KeyInput[] movementKeys = new KeyInput[]{KeyInput.W, KeyInput.A, KeyInput.S, KeyInput.D};
    private final KeyInput[] confirmKeys = new KeyInput[]{KeyInput.Y, KeyInput.N};

    public HeroMonsterGame(){
        this.playground = new Playground(new HeroMonsterSpaceFactory(), io, initialPos);
    }

    @Override
    public void start() {
        System.out.println("play");
        playground.printBoard();
        while(true){
            KeyInput ki = io.getKeyInput(movementKeys);
            playground.handleMove(ki.toMoveDir());
            playground.printBoard();
        }
    }
}
