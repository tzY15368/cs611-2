import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        String configPath = null;
        String argOpt = "--config";
        for(int i=0;i<args.length;i++){
            if(args[i].equals(argOpt) && i<args.length-1){
                configPath = args[i+1];
                break;
            }
        }
        if(configPath==null){
            System.out.println("Usage: add `--config <CONFIGPATH>` to command line argument");
            System.exit(-1);
        }
        Game game = new HeroMonsterGame(configPath);
        game.start();
    }
}
