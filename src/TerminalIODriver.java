import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class TerminalIODriver implements IODriver{

    private final KeyInput[] defaultExpected = KeyInput.values();
    private KeyInput[] actualExpected = defaultExpected;

    private Supplier<String> showMapCallback;
    private Supplier<String> showInfoCallback;

    private KeyInput getActualKeyInput(){
        String msg = "Expected input: "+ Arrays.toString(actualExpected) +", or : Q for exit, I for info, and M for map";
        while(true) {
            System.out.println(msg);
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

    // returns menu index
    @Override
    public int getMenuSelection(List s, boolean must) {
        int cur = 0;
        KeyInput[] wsyn = new KeyInput[]{KeyInput.Y, KeyInput.W, KeyInput.S, KeyInput.N};
        showInfo("============ Menu options ===========");
        for(int i=0;i<s.size();i++){
            showInfo(String.format("[%d]: %s",(i+1), s.get(i)));
        }
        showInfo("------------------------------------");

        while(true){
            showInfo(String.format("Current selection: [%d]: %s, W for prev, S for next," +
                    " Y for yes"+(must?"":" N for leave menu"), cur+1, s.get(cur)));
            KeyInput ki = getKeyInput(wsyn);
            switch (ki){
                case Y:
                    showInfo("You selected "+String.format("[%d]: %s",(cur+1), s.get(cur)));
                    return cur;
                case W:
                    cur = Math.max(0, cur-1);
                    break;
                case S:
                    cur = Math.min(cur+1, s.size()-1);
                    break;
                case N:
                    if(!must)return -1;
            }
        }
    }

    @Override
    public void showInfo(String info) {
        System.out.println(info);
    }
}
