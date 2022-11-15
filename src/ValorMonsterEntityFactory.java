import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ValorMonsterEntityFactory extends AbstractEntityFactory{

    public ValorMonsterEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    @Override
    public Squad fillWithSquad(Squad squad) {
        List<Entity> monsterList = new ArrayList<>();
        Squad monsterSquad = new Squad("monsters-squad",monsterList,
                ioDriver,new DistFightTurnStrategy(ioDriver));
        Random r = new Random();
        List<Entity> monsters = EntityManager.listMonsters();
        for(int i=0;i<Constants.VALOR_MONSTER_LANE.length;i++){
            int lvl = squad.getAllEntities().get(i).getLevel();
            Entity monster = monsters.get(r.nextInt(monsters.size()));
            Entity actualMonster = monster.cloneByLevel(lvl);
            actualMonster.setPos(new Pos(0,Constants.VALOR_MONSTER_LANE[i]));
            monsterList.add(actualMonster);
        }
        return monsterSquad;
    }

    @Override
    public void fillSquad(Squad squad) {

    }
}
