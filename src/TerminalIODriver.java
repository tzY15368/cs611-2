import java.util.Arrays;
import java.util.Scanner;

public class TerminalIODriver implements IODriver{

    private final KeyInput[] defaultExpected = KeyInput.values();
    private KeyInput[] actualExpected = defaultExpected;

    private KeyInput getActualKeyInput(){
        String msg = "Expected input: "+ Arrays.toString(actualExpected) +", or Q for exit";
        System.out.println(msg);
        while(true) {
            Scanner s = new Scanner(System.in);
            String key = s.nextLine().strip().toUpperCase();
            if (key.equals("Q")) {
                this.showInfo("Exit");
                System.exit(0);
            }
            for (KeyInput ki : actualExpected) {
                if (ki.name().equals(key)) {
                    return ki;
                }
            }
            System.out.println("Error: " + msg);
        }
    }

    @Override
    public KeyInput getKeyInput() {
        this.actualExpected = defaultExpected;
        return this.getActualKeyInput();
    }

    public KeyInput getKeyInput(KeyInput[] expected){
        this.actualExpected = expected;
        return this.getActualKeyInput();
    }

    @Override
    public void showInfo(String info) {
        System.out.println(info);
    }
}
