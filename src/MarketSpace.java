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
        // only talks to the latest squad in town
        Squad squad = squads.get(squads.size()-1);
        io.showInfo(String.format("%s: Would you like to enter market?",squad));
        KeyInput ki = io.getKeyInput(new KeyInput[]{KeyInput.Y, KeyInput.N});
        if(ki==KeyInput.N){
            return;
        }
        for(Entity ent: squad.listEntities()){
            ent.trade(this.merchant);
        }
    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }
}
