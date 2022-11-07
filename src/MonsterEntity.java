import java.util.ArrayList;
import java.util.List;

public class MonsterEntity extends Entity{
    private int defense;
    private int damage;
    private int dodge;

    public MonsterEntity(){
        this("place-holder",0,0,0,0,"",null);
    }

    public static int getInitialHP(int level){
        return (int) (1.5 * level);
    }

    public MonsterEntity(String name, int level, int defense, int damage, int dodge, String namePrefix, IODriver io) {
        super(namePrefix + name, MonsterEntity.getInitialHP(level), level, new MonsterInventoryFactory(),io, new MonsterFightStrategy());
        this.defense = defense;
        this.damage = damage;
        this.dodge = dodge;
    }

    public MonsterEntity cloneByLevel(int targetLevel){
        MonsterEntity ent = new MonsterEntity(this.name, this.level, this.defense,
                this.damage, this.dodge, "", this.io);
        ent.level = targetLevel;
        float ratio = targetLevel / ent.level;
        ent.defense = (int) (ent.defense * ratio);
        ent.damage = (int) (ent.damage * ratio);
        ent.dodge = (int) (ent.dodge * ratio);
        ent.HP = (int) (ent.HP * ratio);
        return ent;
    }

    @Override
    public List<Entity> fromConfig(List<List<String>> cfg, String namePrefix, IODriver io) {
        List<Entity> res = new ArrayList<>();
        cfg.remove(0);
        for(List<String> cf: cfg){
            MonsterEntity me = new MonsterEntity(
                    cf.get(0),
                    Integer.parseInt(cf.get(1)),
                    Integer.parseInt(cf.get(3)),
                    Integer.parseInt(cf.get(2)),
                    Integer.parseInt(cf.get(4)),
                    namePrefix,
                    io
            );
            res.add(me);
        }
        return res;
    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void fight(Entity ent) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void revive() {

    }
}
