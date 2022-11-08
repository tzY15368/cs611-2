import java.util.ArrayList;
import java.util.List;

public class ArmorItem extends Item implements  Cloneable{
    public int dmgReduction;

    public ArmorItem(String name, int requiredLevel, int price, int durability, int dmgReduction) {
        super(name, requiredLevel, price, durability);
        this.dmgReduction = dmgReduction;
    }

    public ArmorItem() {

    }

    @Override
    public List<Item> fromConfig(List<List<String>> cfg) {
        List<Item> res = new ArrayList<>();
        cfg.remove(0);
        for(List<String> cf : cfg){
            ArmorItem wi = new ArmorItem(
                    cf.get(0),
                    Integer.parseInt(cf.get(2)),
                    Integer.parseInt(cf.get(1)),
                    Integer.MAX_VALUE,
                    Integer.parseInt(cf.get(3))
            );
            res.add(wi);
        }
        return res;
    }

    @Override
    public ArmorItem clone(){
        ArmorItem c = (ArmorItem) super.clone();
        c.dmgReduction = dmgReduction;
        return c;
    }

    @Override
    public boolean usedBy(Entity ent) {
        return false;
    }

    public String toString(){
        return super.toString() + "dmgReduction:"+dmgReduction;
    }
}
