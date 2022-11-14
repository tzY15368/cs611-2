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
        return this.squads.size()!=0?"|H ":"|N ";
    }

    @Override
    public boolean moveIn(Squad squad){
        this.squads.add(squad);
        io.showInfo(this.getClass().getName()+": "+squad+"entered nexus");
        return true;
    }

    @Override
    public void handleEvent(Squad squad) {

    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }
}
