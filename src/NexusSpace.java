import java.util.ArrayList;

public class NexusSpace extends Space{
    private Squad merchantSquad;
    private Entity merchant;
    public NexusSpace(IODriver io, AbstractEntityFactory entityFactory, char prefix) {
        super(io, entityFactory);

        // ???
        if(entityFactory!=null){
            merchantSquad = new Squad("market-"+this.uuid,new ArrayList<>(), io, null);
            entityFactory.fillSquad(merchantSquad);
            this.merchant = merchantSquad.listEntities().get(0);
        }
    }

    @Override
    public String toString(){
        String base = "|";
        if(this.entities.size()==0){
            base += "NN";
        }
        else if(this.entities.size()==1){
            base += "N";
            base += this.entities.get(0).toIdentifier();
        } else if(this.entities.size()==2){
            base += this.entities.get(0).toIdentifier();
            base += this.entities.get(1).toIdentifier();
        }
        return base;
    }

    @Override
    public boolean moveIn(Squad squad){
        io.showInfo("Squads don't move in this game");
        return false;
    }

    @Override
    public boolean moveIn(Entity ent) {
        if(this.entities.size()==0 || (this.entities.size()==1 && this.entities.get(0).getClass()!=ent.getClass())){
            this.entities.add(ent);
            ent.trade(this.merchant);
            return true;
        }
        io.showInfo("Only one entity of each class can be present in one space");
        return false;
    }

    @Override
    public void handleEvent(Squad squad) {

    }


    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }

    @Override
    public void moveOut(Entity ent) {
        this.entities.remove(ent);
    }
}
