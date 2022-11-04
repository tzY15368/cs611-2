import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Supplier;

public class TerminalIODriver implements IODriver{

    private final KeyInput[] defaultExpected = KeyInput.values();
    private KeyInput[] actualExpected = defaultExpected;

    private Supplier<String> showMapCallback;
    private Supplier<String> showInfoCallback;

    private KeyInput getActualKeyInput(){
        String msg = "Expected input: "+ Arrays.toString(actualExpected) +", or : Q for exit, I for info, and M for map";
        System.out.println(msg);
        while(true) {
            Scanner s = new Scanner(System.in);
            String key = s.nextLine().strip().toUpperCase();
            if (key.equals("Q")) {
                this.showInfo("Exit");
                System.exit(0);
            } else if(key.equals("I")) {
                this.showInfo(this.showInfoCallback.get());
                continue;
            } else if(key.equals("M")){
                this.showInfo(this.showMapCallback.get());
                continue;
            }
            for (KeyInput ki : actualExpected) {
                if (ki.name().equals(key)) {
                    return ki;
                }
            }
            System.out.println("Error: " + msg);
        }
    }

//    @Override
//    public KeyInput getKeyInput() {
//        this.actualExpected = defaultExpected;
//        return this.getActualKeyInput();
//    }

    @Override
    public void registerShowInfo(Supplier<String> func) {
        this.showInfoCallback = func;
    }

    @Override
    public void registerShowMap(Supplier<String> func) {
        this.showMapCallback = func;
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
