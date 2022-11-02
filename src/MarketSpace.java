public class MarketSpace extends Space{

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.isOccupied?"→M":"|M";
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
