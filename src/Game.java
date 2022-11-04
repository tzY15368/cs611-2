public abstract class Game {
    protected ConfigLoader cfg;

    public Game(String configPath){
        this.cfg = new ConfigLoader(configPath);
    }

    public abstract void start();
}
