public abstract class Space {
    protected boolean isOccupied = false;

    public abstract String toString();

    public abstract boolean moveIn();

    // what does it return?
    public abstract void handleEvent();

    public abstract void moveOut();
}
