import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeroEntity extends Entity implements Cloneable{

    private int mana;
    private int strength;
    private int dexterity;
    private int agility;
    private int startMoney;
    private Pos initialPos;
    private Pos currentPos;

    public static int getInitialHP(int level){
        return (int) (1.5 * level);
    }

    public HeroEntity(){
        this("place-holder",0,0,0,0,0,0,"",null);
    }

    public HeroEntity(String name, int level, int mana, int strength, int dexterity, int agility, int startMoney, String namePrefix,IODriver io) {
        super(namePrefix+"-"+name, HeroEntity.getInitialHP(level), level, new HeroInventoryFactory(startMoney),io, new HeroFightStrategy(io));
        this.mana = mana;
        this.startMoney = startMoney;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
    }

    @Override
    public void handleLevelUp(int old, int newLvl) {
        float ratio = Constants.HERO_LEVELUP_MANA_RATIO;
        this.mana = (int) (this.mana * ratio);
        this.strength = (int) (this.strength * ratio);
        this.dexterity = (int) (this.dexterity * ratio);
        this.agility = (int) (this.agility * ratio);
        this.HP = newLvl * Constants.HERO_LEVELUP_HP_RATIO;
    }

    @Override
    public Entity cloneByLevel(int level){
        return new HeroEntity(name, level==0?this.level:level, mana, strength, dexterity, agility, startMoney, "", io);
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
    public int getActualDamage(int damage) {
        if(new Random().nextInt(100) < this.agility * 0.002){
            io.showInfo(String.format("%s dodged attack!", this));
            return 0;
        }
        for(Item item: inventory.listItem()){
            if(item instanceof ArmorItem){
                ArmorItem ai = (ArmorItem) item;
                if(ai.dmgReduction <= damage){
                    damage -= ai.dmgReduction;
                    ai.dmgReduction = 0;
                    ai.setDurability(0);
                    inventory.useItem(item);
                    if(damage<=0)return 0;

                }

            }
        }
        return Math.max(damage, 0);
    }

    @Override
    public int createDamage() {
        List<WeaponItem> items = new ArrayList<>();
        for(Item item : this.inventory.listItem()){
            if(item instanceof WeaponItem){
                items.add((WeaponItem) item);
            }
        }
        int selection = io.getMenuSelection(items, true);
        WeaponItem weapon = items.get(selection);
        return (int) ((weapon.damage + strength)*0.05);
    }

    @Override
    public void handleSpellUse(SpellItem spell, Entity ent) {
        int dmg = spell.getDamage();
        dmg = dmg * (int)(1 + (float) this.dexterity / 10000);
        ent.handleDamage(dmg);
        ent.handleSpellEffect(spell);
    }

    @Override
    public void handleSpellEffect(SpellItem spell) {
        return;
    }

    @Override
    public void handlePotionUse(PotionItem potion) {
        int newVal = potion.getAttrIncrease();
        for(String s :potion.getAttrAffected()){
            s = s.toLowerCase();
            switch (s){
                case "health":
                    this.HP += newVal;
                    break;
                    case "strength":
                        this.strength += newVal;
                        break;
                case "mana":
                    this.mana += newVal;
                    break;
                case "agility":
                    this.agility += newVal;
                    break;
                case "dexterity":
                    this.dexterity += newVal;
                    break;
                default:
                    io.showInfo("Warning: attr "+s+" is not supported");
                    break;
            }
        }
    }

    @Override
    public void revive() {
        this.HP = (int) (this.HP * Constants.HERO_REVIVE_RATIO);
        this.mana = (int) (this.mana * Constants.HERO_REVIVE_RATIO);
    }
}
