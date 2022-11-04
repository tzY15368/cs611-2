import java.util.List;

public class Inventory {
    private List<Item> items;
    private int gold;
    private Entity ent;
    private IODriver io;

    public Inventory(List<Item> items, int gold, IODriver io, Entity owner){
        this.items = items;
        this.gold = gold;
        this.ent = owner;
        this.io = io;
    }

    public List<Item> listItem(){
        return this.items;
    }

    public boolean useItem(Item item){
        if(!items.contains(item)){
            io.showInfo(String.format("Inventory(%s):invalid item", this.ent));
            return false;
        }
        boolean res = item.usedBy(ent);
        if(!res)return false;
        if(item.getDurability()==0){
            io.showInfo(String.format("Inventory(%s): item %s is used up", this.ent, item));
            this.items.remove(item);
        }
        return true;
    }
}
