public class HeroEntity extends Entity{
    private static final int defaultLvl = 8;
    private static final int defaultHP = 100;

    public HeroEntity(String name, int HP, int level) {
        super(name, defaultHP, defaultLvl, new HeroInventoryFactory());
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
