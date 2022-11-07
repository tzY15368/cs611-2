import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityManager {
    private static EntityManager instance;

    private List<Entity> entities;

    public static EntityManager getInstance(){
        if(instance==null){
            instance = new EntityManager();
        }
        return instance;
    }

    public EntityManager(){
        this.entities = new ArrayList<>();
    }

    public static List<Entity> listHeroes(){
        List<Entity> res = new ArrayList<>();
        for(Entity ent : instance.entities){
            for(HeroType ht : HeroType.values()){
                if(ent.name.toLowerCase().contains(ht.name().toLowerCase())){
                    res.add(ent);
                }
            }
        }
        return res;
    }

    public static List<Entity> listMonsters(){
        List<Entity> res = new ArrayList<>();
        for(Entity ent : instance.entities){
            for(MonsterType ht : MonsterType.values()){
                if(ent.name.toLowerCase().contains(ht.name().toLowerCase())){
                    res.add(ent);
                }
            }
        }
        return res;
    }

    public static void registerEntities(ConfigLoader cf, IODriver io){
        Map<String, List<List<String>>> data = cf.getData();
        EntityManager instance = EntityManager.getInstance();
        data.forEach((String key, List<List<String>> value)->{
            key = key.toLowerCase();

            // heros
            List<Entity> gotEntities = new ArrayList<>();
            for(HeroType ht : HeroType.values()){
                if(key.contains(ht.name().toLowerCase())){
                    gotEntities.addAll(new HeroEntity().fromConfig(value,ht.name(),io));
                }
            }

            // monsters
            for(MonsterType mt : MonsterType.values()){
                if(key.contains(mt.name().toLowerCase())){
                    gotEntities.addAll(new MonsterEntity().fromConfig(value, mt.name(),io));
                }
            }

            instance.entities.addAll(gotEntities);
        });
        System.out.println("EntityManager: added "+getInstance().entities.size()+" entities");
    }
}
