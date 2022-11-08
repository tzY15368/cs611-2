import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int gold;
    private Entity ent;
    private IODriver io;
    private List<Item> activeItems;
    private float activeItemListSize = Constants.DEFAULT_INVENTORY_ACTIVE_SIZE;

    public Inventory(List<Item> items, int gold, IODriver io, Entity owner){
        this.items = items;
        this.gold = gold;
        this.ent = owner;
        this.io = io;
        this.activeItems = new ArrayList<>();
    }

    public void scaleActiveList(float coefficient){
        this.activeItemListSize *= coefficient;
    }

    public int getGold(){
        return this.gold;
    }

    public Item getItemByIndex(int index){
        return this.items.get(index);
    }

    public void setGold(int g){
        this.gold = g;
    }

    public List<Item> listItem(){
        return this.items;
    }

    public boolean useItem(Item item){
        if(!items.contains(item)){
            io.showInfo(String.format("Inventory(%s):invalid item", this.ent));
            return false;
        }
        if(item.getRequiredLevel() > this.ent.level){
            io.showInfo("Your level is too low to use this");
            return false;
        }
        boolean res = item.usedBy(ent);
        if(!res){
            io.showInfo("use failed");
            return false;
        }
        if(item.getDurability()<=0){
            io.showInfo(String.format("Inventory(%s): item %s is used up", this.ent, item));
            this.items.remove(item);
            this.activeItems.remove(item);
        }
        return true;
    }

    public Item popItem(Item item){
        if(!items.contains(item)){
            return item;
        }
        this.items.remove(item);
        return item;
    }

    public void replaceActiveItem(){
        io.showInfo("Your active item slots:");
        io.showInfo(this.activeItems.toString());
        io.showInfo("----------------------------");
        io.showInfo("Select the new item you want to place in the active item slots from your inventory");
        int selection = io.getMenuSelection(this.items, false);
        if(selection==-1)return;
        Item newItem = this.items.get(selection);

        io.showInfo("Select the item you want to replace in the active item slots");
        selection = io.getMenuSelection(this.activeItems,false);
        if(selection==-1)return;

        Item removed = this.activeItems.remove(selection);
        this.activeItems.add(newItem);
        io.showInfo(String.format("%s replaced %s in his active item slot to %s",ent,removed,newItem));
    }

    public void addItem(Item item){

        this.items.add(item);
        if(this.activeItems.size() < (int)this.activeItemListSize){
            this.activeItems.add(item);
        }
    }

    public String toString(){
        return String.format("<Inventory (%s)>",this.ent);
    }

}
