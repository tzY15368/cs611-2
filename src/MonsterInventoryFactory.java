import java.util.ArrayList;

public class MonsterInventoryFactory extends AbstractInventoryFactory{

    @Override
    public Inventory makeInventory(IODriver io, Entity ent) {
        return new Inventory(new ArrayList<>(), 0, io, ent);
    }
}
