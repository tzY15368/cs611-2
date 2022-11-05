import java.util.ArrayList;
import java.util.List;

public class SpellItem extends Item implements Cloneable{
    private int damage;
    private int manaCost;

    public SpellItem(String name, int requiredLevel, int price, int durability, int damage, int manaCost) {
        super(name, requiredLevel, price, durability);
        this.damage = damage;
        this.manaCost = manaCost;
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
                    Integer.MAX_VALUE,
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
