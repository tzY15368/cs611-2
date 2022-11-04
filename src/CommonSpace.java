public class CommonSpace extends Space{

    public CommonSpace(IODriver io) {
        super(io);
    }

    @Override
    public String toString() {
        return this.squads.size()!=0?"→ ":"| ";
    }

    @Override
    public boolean moveIn(Squad squad) {
        this.squads.add(squad);
        io.showInfo("Common: You are in common area");
        return true;
    }

    @Override
    public void handleEvent() {
        // TODO Auto-generated method stub
        io.showInfo("common stuff");
    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }
}
