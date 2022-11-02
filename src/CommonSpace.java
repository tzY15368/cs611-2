public class CommonSpace extends Space{

    @Override
    public String toString() {
        return this.isOccupied?"→ ":"| ";
    }

    @Override
    public boolean moveIn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void handleEvent() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveOut() {
        // TODO Auto-generated method stub
        
    }
}
