import java.util.List;

public class TraderEntity extends Entity{


    public TraderEntity(String name, IODriver io) {

        super(name, 0,0, new TraderInventoryFactory(),io, new TraderFightStrategy());
    }

    @Override
    public Entity cloneByLevel(int level) {
        return null;
    }

    @Override
    public List<Entity> fromConfig(List<List<String>> cfg, String namePrefix, IODriver io) {
        return null;
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
