public class InaccessibleSpace extends Space{

    public InaccessibleSpace(IODriver io, AbstractEntityFactory abs) {
        super(io, abs);
    }

    @Override
    public boolean moveIn(Squad squad) {
        this.io.showInfo("Error: this place is inaccessible");
        return false;
    }

    @Override
    public boolean moveIn(Entity ent) {
        return false;
    }

    @Override
    public void handleEvent(Squad squad) {

    }

    @Override
    public void moveOut(Squad squad) {

    }

    @Override
    public String toString() {
        return "|XX";
    }
}
