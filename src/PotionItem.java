public class PotionItem extends Item implements Cloneable{
    private int attrIncrease;
    private String[] attrAffected;

    public PotionItem(String name, int requiredLevel, int price, int durability) {
        super(name, requiredLevel, price, durability);
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
        return super.toString() + " attrIncrease:"+attrIncrease + "attrAffected"+attrAffected;
    }
}
