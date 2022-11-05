import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class WeaponItem extends Item implements Cloneable{
    private int requiredHands;
    private int damage;

    public WeaponItem(String name, int requiredLevel, int price, int durability, int requiredHands, int damage) {
        super(name, requiredLevel, price, durability);
        this.requiredHands = requiredHands;
        this.damage = damage;
    }

    @Override
    public List<Item> fromConfig(List<List<String>> cfg) {
        List<Item> res = new ArrayList<>();
        cfg.remove(0);
        for(List<String> cf : cfg){
            WeaponItem wi = new WeaponItem(
                    cf.get(0),
                    Integer.parseInt(cf.get(2)),
                    Integer.parseInt(cf.get(1)),
                    Integer.MAX_VALUE,
                    Integer.parseInt(cf.get(4)),
                    Integer.parseInt(cf.get(3))
            );
            res.add(wi);
        }
        return res;
    }

    public WeaponItem() {

    }

    @Override
    public WeaponItem clone() {
        WeaponItem c = (WeaponItem) super.clone();
        c.requiredHands = requiredHands;
        c.damage = damage;
        return  c;
    }

    @Override
    public boolean usedBy(Entity ent) {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " requiredHands:"+requiredHands+" damage:"+damage;
    }
}
