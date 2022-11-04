public class ArmorItem extends Item implements  Cloneable{
    private int dmgReduction;

    public ArmorItem(String name, int requiredLevel, int price, int durability, int dmgReduction) {
        super(name, requiredLevel, price, durability);
        this.dmgReduction = dmgReduction;
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
