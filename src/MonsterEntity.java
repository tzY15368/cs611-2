public class MonsterEntity extends Entity{
    public MonsterEntity(String name, int HP, int level) {
        super(name, HP, level, new MonsterInventoryFactory());
    }

    @Override
    public void trade(Entity ent) {

    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void fight(Entity ent) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void revive() {

    }
}
