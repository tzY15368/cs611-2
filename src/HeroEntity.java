import java.util.ArrayList;
import java.util.List;

public class HeroEntity extends Entity implements Cloneable{

    private int mana;
    private int strength;
    private int dexterity;
    private int agility;
    private int startMoney;

    public static int getInitialHP(int level){
        return (int) (1.5 * level);
    }

    public HeroEntity(){
        this("place-holder",0,0,0,0,0,0,"",null);
    }

    public HeroEntity(String name, int level, int mana, int strength, int dexterity, int agility, int startMoney, String namePrefix,IODriver io) {
        super(namePrefix+"-"+name, HeroEntity.getInitialHP(level), level, new HeroInventoryFactory(startMoney),io, new HeroFightStrategy());
        this.mana = mana;
        this.startMoney = startMoney;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
    }

    @Override
    public Entity cloneByLevel(int level){
        return new HeroEntity(name, level, mana, strength, dexterity, agility, startMoney, "", io);
    }


    @Override
    public List<Entity> fromConfig(List<List<String>> cfg, String namePrefix, IODriver io) {
        List<Entity> res = new ArrayList<>();
        cfg.remove(0);
        for(List<String> cf: cfg){
            HeroEntity he = new HeroEntity(
                    cf.get(0),
                    Integer.parseInt(cf.get(6)),
                    Integer.parseInt(cf.get(1)),
                    Integer.parseInt(cf.get(2)),
                    Integer.parseInt(cf.get(4)),
                    Integer.parseInt(cf.get(3)),
                    Integer.parseInt(cf.get(5)),
                    namePrefix,
                    io
            );
            res.add(he);
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
