import java.util.ArrayList;
import java.util.List;

public class MonsterEntityFactory extends AbstractEntityFactory{
    public MonsterEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    // generate monsters w/ respect to the squad's hero levels
    @Override
    public Squad fillWithSquad(Squad squad) {
        // takes the hero squad and returns a NEW SQUAD of monsters
        int monsterCount = squad.listEntities().size();
        List<Entity> monsterList = new ArrayList<>();
        for(int i=0;i<monsterCount;i++){

        }
        Squad monsterSquad = new Squad("MonsterSquad", monsterList);
        return monsterSquad;
    }

    @Override
    public void fillSquad(Squad squad) {

    }
}
