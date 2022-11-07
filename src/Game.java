public abstract class Game {
    protected ConfigLoader cfg;
    protected IODriver io;

    public Game(String configPath, IODriver io){
        this.cfg = new ConfigLoader(configPath,io);
        this.io = io;
    }

    public abstract void start();
}
