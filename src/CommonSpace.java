import java.util.List;
import java.util.Random;

public class CommonSpace extends Space{

    private Squad monsters;
    private KeyInput[] confirmKeys = new KeyInput[]{KeyInput.Y, KeyInput.N};

    public CommonSpace(IODriver io, AbstractEntityFactory entityFactory) {
        super(io, entityFactory);
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
    public void handleEvent(Squad squad) {
        io.showInfo("You are in common space");
        this.monsters = this.entityFactory.fillWithSquad(squad);
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
