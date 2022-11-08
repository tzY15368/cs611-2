import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Cloneable{
    private String name;
    private int requiredLevel;
    private int price;
    private int durability;

    public Item(){

    }
    public Item(String name, int requiredLevel, int price, int durability){
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.price = price;
        this.durability = durability;
    }

    public abstract List<Item> fromConfig(List<List<String>> cfg);


    public void setDurability(int durability) {
        this.durability = durability;
    }

    public String getName(){
        return this.name;
    }

    public int getPrice(){
        return this.price;
    }

    public int getRequiredLevel(){
        return this.requiredLevel;
    }

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

    public abstract boolean usedBy(Entity ent);

    @Override
    public String toString() {
        return String.format("%s - %s: price:%d, level:%d, durability: %d",this.getClass(),name,price,requiredLevel,durability);
    }
}
