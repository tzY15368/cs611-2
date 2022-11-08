import java.util.ArrayList;

public class MarketSpace extends Space{
    Entity localMerchant;

    public MarketSpace(IODriver io, AbstractEntityFactory entityFactory) {
        super(io, entityFactory);
        Squad localSquad = new Squad("market-"+this.uuid,new ArrayList<>(), io, null);
        this.entityFactory.fillSquad(localSquad);
        this.localMerchant = localSquad.listEntities().get(0);
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
    public void handleEvent(Squad squad) {
        io.showInfo(String.format("%s: Would you like to enter market?",squad));
        KeyInput ki = io.getKeyInput(new KeyInput[]{KeyInput.Y, KeyInput.N});
        if(ki==KeyInput.N){
            return;
        }
        for(Entity ent: squad.listEntities()){
            ent.trade(this.localMerchant);
        }
    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }
}
