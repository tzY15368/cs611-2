import java.util.ArrayList;
import java.util.List;

public class HeroInventoryFactory extends AbstractInventoryFactory {
    private int defaultGold = 50;

    public HeroInventoryFactory(int startMoney){
        this.defaultGold = startMoney;
    }

    @Override
    public Inventory makeInventory(IODriver io, Entity ent) {
        WeaponItem def = new WeaponItem("hands",0,0,Integer.MAX_VALUE,2,99999);
        List<Item> items = new ArrayList<>();
        items.add(def);
        return new Inventory(items,defaultGold, io, ent);
    }
}
