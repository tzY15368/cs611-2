public class TraderInventoryFactory extends AbstractInventoryFactory{
    private ItemManager itemManager = ItemManager.getInstance();

    @Override
    public Inventory makeInventory(IODriver io, Entity ent) {

        return new Inventory(itemManager.genRandomTradableItems(),Integer.MAX_VALUE,io, ent);
    }
}
