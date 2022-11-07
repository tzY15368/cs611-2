import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotionItem extends Item implements Cloneable{
    private int attrIncrease;
    private String[] attrAffected;

    public PotionItem(String name, int requiredLevel, int price, int durability, int attrIncrease, String attrAffected) {
        super(name, requiredLevel, price, durability);
        this.attrIncrease = attrIncrease;
        this.attrAffected = attrAffected.split("/");
    }

    @Override
    public List<Item> fromConfig(List<List<String>> cfg) {
        List<Item> res = new ArrayList<>();
        cfg.remove(0);
        for(List<String> cf : cfg){
            PotionItem wi = new PotionItem(
                    cf.get(0),
                    Integer.parseInt(cf.get(2)),
                    Integer.parseInt(cf.get(1)),
                    1,
                    Integer.parseInt(cf.get(3)),
                    (cf.get(4))
            );
            res.add(wi);
        }
        return res;
    }

    public PotionItem() {

    }

    @Override
    public PotionItem clone() {
         PotionItem c = (PotionItem) super.clone();
         c.attrAffected = attrAffected;
         c.attrIncrease = attrIncrease;
         return c;
    }

    @Override
    public boolean usedBy(Entity ent) {
        return false;
    }

    public String toString(){
        return super.toString() + " attrIncrease:"+attrIncrease + " attrAffected"+ Arrays.toString(attrAffected);
    }
}
