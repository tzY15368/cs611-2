import java.util.UUID;

public abstract class Entity {
    protected int level;
    protected int HP;
    protected String name;
    protected UUID uuid;
    protected Inventory inventory;
    protected IODriver io;

    private final KeyInput[] tradeOptions = new KeyInput[]{KeyInput.W, KeyInput.S, KeyInput.Y, KeyInput.N};

    private final float tradeInCoefficient = 0.5F;

    public Entity(String name, int HP, int level, AbstractInventoryFactory inventoryFactory, IODriver io){
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.HP = HP;
        this.level = level;
        this.inventory = inventoryFactory.makeInventory(io, this);
        this.io = io;
    }

    public int getLevel(){
        return this.level;
    }

    public UUID getID(){
        return this.uuid;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getName(){
        return this.name;
    }

    public int getHP() {
        return HP;
    }

    public void trade(Entity ent){
        Inventory targetInventory = ent.getInventory();
        int cur = 0;
        io.showInfo(String.format("%s trading with %s", this.getName(), ent.getName()));
        io.showInfo("Buy phase:");
        while(true){
            io.showInfo(String.format("%s's inventory:",ent.getName()) + targetInventory.dumpString());
            if(targetInventory.listItem().size()==0){
                io.showInfo("Nothing is available for purchase...");
                break;
            }
            io.showInfo("Select the item you want to buy, W for next, S for prev, Y for buy, N to leave menu, current select: " + (cur+1));
            KeyInput ki = io.getKeyInput(tradeOptions);
            if(ki == KeyInput.N)break;
            if(ki == KeyInput.W){
                cur = Math.max(0, cur-1); continue;
            }
            if(ki == KeyInput.S){
                cur = Math.min(targetInventory.listItem().size()-1, cur+1); continue;
            }
            Item targetItem = targetInventory.getItemByIndex(cur);
            if(this.inventory.getGold() < targetItem.getPrice()){
                io.showInfo("Error: you cannot purchase this item, you are too poor");
                continue;
            }
            if (this.level < targetItem.getRequiredLevel()) {
                io.showInfo("Error: you cannot purchase this item, your level is too low");
                continue;
            }
            // checks are OK, go ahead with transfer
            targetInventory.popItem(targetItem);
            this.inventory.addItem(targetItem);
            targetInventory.setGold(targetInventory.getGold()+targetItem.getPrice());
            this.inventory.setGold(this.inventory.getGold()- targetItem.getPrice());
            io.showInfo(String.format("Trade: %s bought %s from %s for %d gold",this.name, targetItem.getName(),ent.name, targetItem.getPrice()));
        }
        io.showInfo("Sell phase:");
        cur = 0;
        while(true){
            io.showInfo("My inventory: " + targetInventory.dumpString());
            if(this.inventory.listItem().size()==0){
                io.showInfo("Nothing is available for sale...");
                break;
            }
            io.showInfo("Select the item you want to sell, W for next, S for prev, Y for buy, N to leave menu, current select: " + (cur+1));
            KeyInput ki = io.getKeyInput(tradeOptions);
            if(ki == KeyInput.N)break;
            if(ki == KeyInput.W){
                cur = Math.max(0, cur-1); continue;
            }
            if(ki == KeyInput.S){
                cur = Math.min(targetInventory.listItem().size()-1, cur+1); continue;
            }
            Item targetItem = targetInventory.getItemByIndex(cur);
            ent.inventory.addItem(targetItem);
            this.inventory.popItem(targetItem);
            int actualPrice = (int) (targetItem.getPrice() * tradeInCoefficient);
            this.inventory.setGold(this.inventory.getGold() + actualPrice);
            ent.inventory.setGold(this.inventory.getGold() - actualPrice);
        }
    };

    // mana dmg?
    public abstract void takeDamage(int damage);

    public abstract void fight(Entity ent);

    protected abstract void die();
    public abstract void revive();

    public String toString(){
        return "<"+this.getClass()+">";
    }
}
