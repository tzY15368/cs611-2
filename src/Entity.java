import java.util.UUID;

public abstract class Entity {
    protected int level;
    protected int HP;
    protected String name;
    protected UUID uuid;
    protected Inventory inventory;
    protected IODriver io;

    public Entity(String name, int HP, int level, AbstractInventoryFactory inventoryFactory, IODriver io){
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.HP = HP;
        this.level = level;
        this.inventory = inventoryFactory.makeInventory(io, this);
    }

    public int getHP() {
        return HP;
    }

    public abstract void trade(Entity ent);

    // mana dmg?
    public abstract void takeDamage(int damage);

    public abstract void fight(Entity ent);

    protected abstract void die();
    public abstract void revive();
}
