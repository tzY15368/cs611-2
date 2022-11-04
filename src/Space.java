public abstract class Space {
    protected boolean isOccupied = false;
    protected IODriver io;

    public Space(IODriver io){
        this.io = io;
    }

    public abstract String toString();

    public abstract boolean moveIn();

    // what does it return?
    public abstract void handleEvent();

    public abstract void moveOut();
}
