import java.util.UUID;

public abstract class Entity {
    protected int level;
    protected int HP;
    protected String name;
    protected UUID uuid;

    public Entity(String name, int HP, int level){
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.HP = HP;
        this.level = level;
    }

    public int getHP() {
        return HP;
    }

    // mana dmg?
    public abstract void takeDamage(int damage);

    public abstract void fight(Entity ent);

    protected abstract void die();
    public abstract void revive();
}
