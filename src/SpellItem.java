import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpellItem extends Item implements Cloneable{
    private int damage;
    private int manaCost;

    public SpellItem(String name, int requiredLevel, int price, int durability, int damage, int manaCost) {
        super(name, requiredLevel, price, durability);
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public int getDamage(){
        return damage;
    }

    public String getAffectedAttr(){
        if(this.getName().toLowerCase().contains("ice")){
            return "damage";
        }
        if(this.getName().toLowerCase().contains("fire")){
            return "defense";
        }
        if(this.getName().toLowerCase().contains("lightning")){
            return "dodge";
        }
        return null;
    }

    @Override
    public List<Item> fromConfig(List<List<String>> cfg) {
        List<Item> res = new ArrayList<>();
        cfg.remove(0);
        for(List<String> cf : cfg){
            SpellItem wi = new SpellItem(
                    cf.get(0),
                    Integer.parseInt(cf.get(2)),
                    Integer.parseInt(cf.get(1)),
                    new Random().nextInt(3),
                    Integer.parseInt(cf.get(3)),
                    Integer.parseInt(cf.get(4))
            );
            res.add(wi);
        }
        return res;
    }

    public SpellItem() {

    }

    @Override
    public SpellItem clone() {
        SpellItem c = (SpellItem) super.clone();
        c.damage = damage;
        c.manaCost = manaCost;
        return c;
    }

    @Override
    public boolean usedBy(Entity ent) {
        return false;
    }

    public String toString(){
        return super.toString() + " damage:"+damage+" manaCost:"+damage;
    }
}
