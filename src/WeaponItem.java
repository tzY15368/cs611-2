public class WeaponItem extends Item implements Cloneable{
    private int requiredHands;
    private int damage;

    public WeaponItem(String name, int requiredLevel, int price, int durability) {
        super(name, requiredLevel, price, durability);
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
