public class MarketSpace extends Space{

    public MarketSpace(IODriver io) {
        super(io);
    }

    @Override
    public String toString() {
        return this.isOccupied?"→M":"|M";
    }

    @Override
    public boolean moveIn() {
        this.isOccupied = true;
        io.showInfo("Market: welcome to the market");
        return true;
    }

    @Override
    public void handleEvent() {
        io.showInfo("market stuff");
        
    }

    @Override
    public void moveOut() {
        // TODO Auto-generated method stub
        this.isOccupied = false;
    }
}
