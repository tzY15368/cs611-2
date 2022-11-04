public class SpellItem extends Item implements Cloneable{
    private int damage;
    private int manaCost;

    public SpellItem(String name, int requiredLevel, int price, int durability) {
        super(name, requiredLevel, price, durability);
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
