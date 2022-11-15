import java.util.List;
import java.util.Random;

public class CommonSpace extends Space{

    private Squad monsters;

    public CommonSpace(IODriver io, AbstractEntityFactory entityFactory) {
        super(io, entityFactory);
    }

    @Override
    public String toString() {
        return this.squads.size()!=0?"|H":"| ";
    }

    @Override
    public boolean moveIn(Squad squad) {
        this.squads.add(squad);
        io.showInfo(String.format("Common: %s entered common area",squad));
        return true;
    }

    @Override
    public boolean moveIn(Entity ent) {
        return false;
    }

    @Override
    public void handleEvent(Squad squad) {
        io.showInfo("You are in common space");
        this.monsters = this.entityFactory.fillWithSquad(squad);
        // let factory create monsters if necessary
        if(this.monsters.listEntities().size()==0){
            io.showInfo("Good news, there's no monsters here");
            return;
        }
        io.showInfo("There's monsters in this space:\n"+monsters.toDetailString());
        squad.fight(monsters);
    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }

}
