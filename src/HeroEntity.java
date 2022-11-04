public class HeroEntity extends Entity{

    public HeroEntity(String name, int HP, int level, IODriver io) {
        super(name, HP, level, new HeroInventoryFactory(),io);
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
