import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        super(namePrefix + name, MonsterEntity.getInitialHP(level), level, new MonsterInventoryFactory(),io, new MonsterActionStrategy(io));
        this.defense = defense;
        this.damage = damage;
        this.dodge = dodge;
    }

    @Override
    public void handleLevelUp(int old, int newLvl) {

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
    public char toIdentifier(){
        return 'M';
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
    public int getActualDamage(int damage) {
        if(new Random().nextInt(100) < (float) this.dodge * 0.01){
            io.showInfo(String.format("%s dodged attack!", this));
            return 0;
        }
        return damage-this.defense;
    }

    @Override
    public int createDamage() {
        return this.damage;
    }

    @Override
    public void handleSpellUse(SpellItem spell, Entity ent) {

    }

    @Override
    public void handleSpellEffect(SpellItem spell) {
        switch (spell.getAffectedAttr().toLowerCase()){
            case "damage":
                this.damage = (int) (damage * Constants.SPELL_EFFECT_COEFFICIENT);
            case "defense":
                this.defense = (int) (defense * Constants.SPELL_EFFECT_COEFFICIENT);
            case "dodge":
                this.dodge = (int) (dodge * Constants.SPELL_EFFECT_COEFFICIENT);
        }
    }

    @Override
    public void handlePotionUse(PotionItem potion) {

    }

    @Override
    public void revive() {

    }
}
