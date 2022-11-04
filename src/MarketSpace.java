public class MarketSpace extends Space{
    TraderEntity merchant;

    public MarketSpace(IODriver io) {
        super(io);
        this.merchant = new TraderEntity("Merchant-"+this.uuid.toString(),io);
    }

    @Override
    public String toString() {
        return this.squads.size()!=0?"→M":"|M";
    }

    @Override
    public boolean moveIn(Squad squad) {
        this.squads.add(squad);
        io.showInfo("Market: welcome to the market");
        return true;
    }

    @Override
    public void handleEvent() {
        io.showInfo("market stuff");
        
    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }
}
