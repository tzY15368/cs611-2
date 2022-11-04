public class InaccessibleSpace extends Space{

    public InaccessibleSpace(IODriver io) {
        super(io);
    }

    @Override
    public boolean moveIn() {
        this.io.showInfo("Error: this place is inaccessible");
        return false;
    }

    @Override
    public void handleEvent() {

    }

    @Override
    public void moveOut() {
        this.isOccupied = false;
    }

    @Override
    public String toString() {
        return "|X";
    }
}
