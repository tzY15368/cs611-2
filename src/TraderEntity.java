import java.util.List;

public class TraderEntity extends Entity{


    public TraderEntity(String name, IODriver io) {

        super(name, 0,0, new TraderInventoryFactory(),io, new TraderFightStrategy(io));
    }

    @Override
    public void handleLevelUp(int old, int newLvl) {

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
    public int getActualDamage(int damage) {
        return 0;
    }

    @Override
    public int createDamage() {
        return 0;
    }

    @Override
    public void handleSpellUse(SpellItem spell, Entity ent) {

    }

    @Override
    public void handleSpellEffect(SpellItem spell) {

    }

    @Override
    public void handlePotionUse(PotionItem potion) {

    }

    @Override
    public void revive() {

    }
}
