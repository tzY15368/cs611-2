public class TraderEntity extends Entity{


    public TraderEntity(String name, IODriver io) {

        super(name, 0,0, new TraderInventoryFactory(),io);
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
