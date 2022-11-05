import java.util.List;
import java.util.Random;

public class CommonSpace extends Space{

    private int monsterPossibility = 50;
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
        Random r = new Random();
        if(r.nextInt(100) > this.monsterPossibility){
            io.showInfo("Good news, there's no monsters here");
            return;
        }
        this.monsters = this.entityFactory.fillWithSquad(squad);
        io.showInfo("There's monsters in this space:\n"+monsters.toDetailString());

    }

    @Override
    public void moveOut(Squad squad) {
        this.squads.remove(squad);
    }


}
