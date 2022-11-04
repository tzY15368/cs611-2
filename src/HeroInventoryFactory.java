import java.util.ArrayList;

public class HeroInventoryFactory extends AbstractInventoryFactory {
    private final int defaultGold = 50;

    @Override
    public Inventory makeInventory(IODriver io, Entity ent) {
        return new Inventory(new ArrayList<>(),defaultGold, io, ent);
    }
}
