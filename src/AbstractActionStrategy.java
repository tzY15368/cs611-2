public abstract class AbstractActionStrategy {

    public AbstractActionStrategy(IODriver io){
        this.io = io;
    }

    protected IODriver io;

    public abstract EntityAction useStrategy();
}
