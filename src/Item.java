public class Item implements Cloneable{
    private String name;
    private int requiredLevel;
    private int price;
    private int durability;

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            clone.durability = this.durability;
            clone.requiredLevel = this.requiredLevel;
            clone.price = this.price;
            clone.name = this.name;
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public int getDurability(){
        return this.durability;
    }

    public boolean usedBy(Entity ent){
        return true;
    }
}
