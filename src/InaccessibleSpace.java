public class InaccessibleSpace extends Space{

    public InaccessibleSpace(IODriver io) {
        super(io);
    }

    @Override
    public boolean moveIn(Squad squad) {
        this.io.showInfo("Error: this place is inaccessible");
        return false;
    }

    @Override
    public void handleEvent() {

    }

    @Override
    public void moveOut(Squad squad) {

    }

    @Override
    public String toString() {
        return "|X";
    }
}
