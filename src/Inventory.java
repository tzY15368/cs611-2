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
        boolean res = item.usedBy(ent);
        if(!res)return false;
        if(item.getDurability()==0){
            io.showInfo(String.format("Inventory(%s): item %s is used up", this.ent, item));
            this.items.remove(item);
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

    public void addItem(Item item){
        this.items.add(item);
    }

    public String toString(){
        return String.format("<Inventory (%s)>",this.ent);
    }

    public String dumpString() {
        StringBuilder sb = new StringBuilder("Available items:\n");
        for(int i=0;i<this.items.size();i++){
            sb.append("|"+(i+1));
            sb.append("|"+this.items.get(i).toString()+"|\n");
        }
        sb.append("---------------------------");
        return sb.toString();
    }
}
