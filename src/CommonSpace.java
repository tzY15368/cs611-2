public class CommonSpace extends Space{

    public CommonSpace(IODriver io) {
        super(io);
    }

    @Override
    public String toString() {
        return this.isOccupied?"→ ":"| ";
    }

    @Override
    public boolean moveIn() {
        this.isOccupied = true;
        return true;
    }

    @Override
    public void handleEvent() {
        // TODO Auto-generated method stub
        io.showInfo("common stuff");
    }

    @Override
    public void moveOut() {
        this.isOccupied = false;
    }
}
