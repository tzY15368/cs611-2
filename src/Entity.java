import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

enum EntityAction{
    Move,
    Attack,
    CastSpell,
    UsePotion,
    Equip, // change weapon or armor
    Teleport,
    Recall
}

public abstract class Entity {
    protected int level;
    protected int HP;
    protected String name;
    protected UUID uuid;
    protected Inventory inventory;
    protected IODriver io;
    protected AbstractActionStrategy strategy;
    protected int experience;
    private Pos pos;
    private Pos initialPos;
    private final KeyInput[] movementKeys = new KeyInput[]{KeyInput.W, KeyInput.A, KeyInput.S, KeyInput.D};

    public Entity(String name, int HP, int level, AbstractInventoryFactory inventoryFactory, IODriver io, AbstractActionStrategy strat){
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.HP = HP;
        this.level = level;
        this.inventory = inventoryFactory==null?null:inventoryFactory.makeInventory(io, this);
        this.io = io;
        this.strategy = strat;
        this.experience = level * Constants.HERO_EXP_PER_LVL;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos p){
        this.pos = p;
        if(initialPos==null){
            initialPos = p;
        }
    }

    public Pos getInitialPos(){
        return initialPos;
    }

    public boolean isDead(){
        return this.HP <=0;
    }

    // return new level
    public int gainExp(int newExp){
        this.experience += newExp;
        int oldLevel = this.level;
        int newLevel = this.experience / Constants.HERO_EXP_PER_LVL;
        if(newLevel != this.level){
            this.handleLevelUp(this.level, newLevel);
            this.level = newLevel;
        }
        // gain more activeitem slots
        getInventory().scaleActiveList(newLevel/oldLevel);
        return this.level;
    }

    public abstract void handleLevelUp(int old, int newLvl);

    public char toIdentifier(){
        return 'E';
    }

    public int getLevel(){
        return this.level;
    }

    public abstract Entity cloneByLevel(int level);

    public abstract List<Entity> fromConfig(List<List<String>> cfg, String namePrefix, IODriver io);

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
        io.showInfo(String.format("%s trading with %s", this.getName(), ent.getName()));
        io.showInfo("Buy phase:");
        while(true){
            if(targetInventory.listItem().size()==0){
                io.showInfo("Nothing is available for purchase...");
                break;
            }
            int selection = io.getMenuSelection(targetInventory.listItem(),false);
            if(selection==-1){
                break;
            }
            Item targetItem = targetInventory.getItemByIndex(selection);
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
        while(true){
            //io.showInfo("My inventory: " + this.inventory.dumpString());
            if(this.inventory.listItem().size()==0){
                io.showInfo("Nothing is available for sale...");
                break;
            }
            io.showInfo("My inventory:");
            int selection = io.getMenuSelection(inventory.listItem(),false);
            if(selection==-1)break;

            Item targetItem = targetInventory.getItemByIndex(selection);
            ent.inventory.addItem(targetItem);
            this.inventory.popItem(targetItem);
            int actualPrice = (int) (targetItem.getPrice() * Constants.TRADEIN_COEFFICIENT);
            this.inventory.setGold(this.inventory.getGold() + actualPrice);
            ent.inventory.setGold(this.inventory.getGold() - actualPrice);
        }
    };

    public abstract int getActualDamage(int damage);

    public void handleDamage(int damage){
        int delta = getActualDamage(damage);
        io.showInfo(String.format("%s actually took %d damage", this, delta));
        this.HP -= delta;
    };

    // returns actual damage
    public abstract int createDamage();

    public void useDamage(Entity ent){
        int dmg = createDamage();
        io.showInfo(String.format("%s dealt %d damage to %s",this,dmg, ent));
        ent.handleDamage(dmg);
    }

    public abstract void handleSpellUse(SpellItem spell, Entity ent);
    public abstract void handleSpellEffect(SpellItem spell);

    private void useSpell(Entity ent){
        List<SpellItem> spellOpts = new ArrayList<>();
        for(Item item : this.inventory.listItem()){
            if(item instanceof SpellItem){
                spellOpts.add((SpellItem) item);
            }
        }
        int selection = io.getMenuSelection(spellOpts,false);
        if(selection==-1){
            io.showInfo("Potion use is aborted");
            return;
        }
        SpellItem item = spellOpts.get(selection);
        boolean ok = this.inventory.useItem(item);
        if(!ok)return;
        this.handleSpellUse(item, ent);
        io.showInfo(String.format("%s used %s, dealt %d damage and reduced %s's %s",
                this,item,item.getDamage(),ent,item.getAffectedAttr()));
    }

    public abstract void handlePotionUse(PotionItem potion);

    private void usePotion(){
        List<PotionItem> potionOpts = new ArrayList<>();
        for(Item item : this.inventory.listItem()){
            if(item instanceof PotionItem){
                potionOpts.add((PotionItem) item);
            }
        }
        int selection = io.getMenuSelection(potionOpts,false);
        if(selection==-1){
            io.showInfo("Potion use is aborted");
            return;
        }
        PotionItem targetPotion = potionOpts.get(selection);
        boolean ok = this.inventory.useItem(targetPotion);
        if(!ok)return;
        this.handlePotionUse(targetPotion);
        io.showInfo(String.format("%s used %s and gained %d in %s", this,
                targetPotion, targetPotion.getAttrIncrease(), Arrays.toString(targetPotion.getAttrAffected())));
    }
    public void doFight(Entity ent){
        io.showInfo(String.format("EntityFight: %s is fighting %s",this, ent));
        this.fight(ent);
    }
    private void fight(Entity ent){
        io.showInfo(this+"'s move:");
        EntityAction ea = strategy.useStrategy();
        switch (ea){
            case Equip:
                this.inventory.replaceActiveItem();
                break;
            case Attack:
                this.useDamage(ent);
                break;
            case CastSpell:
                this.useSpell(ent);
                break;
            case UsePotion:
                this.usePotion();
                break;
        }
    }

    private Entity findOpponent(){
        return null;
    }

    public void takeAction(){
        EntityAction selection = this.strategy.useStrategy();
        switch (selection){
            case Move:
                KeyInput d = io.getKeyInput(movementKeys);
                MoveDir dir = d.toMoveDir();
                boolean ok = Playground.getInstance().handleEntityMove(dir, this);
                if(!ok){
                    io.showInfo("Error: move failed");
                }
                break;
            case Attack:
                Entity opponent = findOpponent();
                if(opponent==null){
                    io.showInfo("Error: no valid opponent");
                    break;
                }
                useDamage(opponent);
                break;
            case UsePotion:
                usePotion();
                break;
            case CastSpell:
                Entity dst = findOpponent();
                if(dst==null){
                    io.showInfo("Error: no valid opponent");
                    break;
                }
                useSpell(dst);
                break;
            case Equip:
                inventory.replaceActiveItem();
                break;
            case Recall:
                handleRecall();
                break;
            case Teleport:
                handleTeleport();
                break;
        }
    };

    private void handleTeleport(){
        KeyInput[] validKeys = Arrays.copyOf(movementKeys,6);
        validKeys[4] = KeyInput.Y;
        validKeys[5] = KeyInput.N;
        KeyInput selection = null;
        Pos currentPos = new Pos(0,0);
        io.showInfo("Current pos:"+currentPos);
        while(selection!=KeyInput.N && selection!=KeyInput.Y){
            selection = io.getKeyInput(validKeys);
            switch (selection){
                case W:
                    currentPos.x -= 1;
                case A:
                    currentPos.y -= 1;
                case S:
                    currentPos.x += 1;
                case D:
                    currentPos.y += 1;
            }
        }
        if(selection==KeyInput.N){
            io.showInfo("Teleport aborted");
            return;
        }
        boolean ok = Playground.getInstance().handleEntityTeleport(currentPos,this);
        if(ok){
            io.showInfo(String.format("%s teleported to %s",this,currentPos));
        } else {
            io.showInfo("teleport failed");
        }
    }

    private void handleRecall(){
        boolean ok = Playground.getInstance().handleEntityTeleport(this.getInitialPos(),this);
        if(ok){
            io.showInfo(String.format("%s recalled back to %s",this, this.getInitialPos()));
        }else{
            io.showInfo("recall failed");
        }
    }

    public abstract void revive();

    public String toString(){
        return String.format("%s (lv%d) (HP=%d)",this.name, this.level, this.getHP());
    }
}
