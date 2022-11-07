import java.util.ArrayList;

public class HeroInventoryFactory extends AbstractInventoryFactory {
    private int defaultGold = 50;

    public HeroInventoryFactory(int startMoney){
        this.defaultGold = startMoney;
    }

    @Override
    public Inventory makeInventory(IODriver io, Entity ent) {
        return new Inventory(new ArrayList<>(),defaultGold, io, ent);
    }
}
