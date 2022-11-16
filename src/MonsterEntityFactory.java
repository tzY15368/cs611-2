import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterEntityFactory extends AbstractEntityFactory{
    private int monsterPossibility = 50;
    public MonsterEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    // generate monsters w/ respect to the squad's hero levels
    @Override
    public Squad fillWithSquad(Squad squad) {
        // takes the hero squad and returns a NEW SQUAD of monsters
        List<Entity> monsterList = new ArrayList<>();
        Squad monsterSquad = new Squad("MonsterSquad", monsterList,ioDriver,new OneByOneFightTurnStrategy(ioDriver));
        Random r = new Random();
        if(r.nextInt(100) > this.monsterPossibility){
            return monsterSquad;
        }
        int monsterCount = squad.listEntities().size();
        int monsterLevel = squad.getLevel();
        List<Entity> monsters = EntityManager.listMonsters();
        for(int i=0;i<monsterCount;i++){
            // TODO: MAKE MONSTERS
            Entity monster = monsters.get(r.nextInt(monsters.size()));
            monster.setSquad(monsterSquad);
            monsterList.add(monster.cloneByLevel(monsterLevel));
        }
        return monsterSquad;
    }

    @Override
    public void fillSquad(Squad squad) {

    }
}
