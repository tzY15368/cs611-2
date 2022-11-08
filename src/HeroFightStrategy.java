import java.util.List;

public class HeroFightStrategy extends AbstractFightStrategy {
    public HeroFightStrategy( IODriver io) {
        super(io);
    }

    @Override
    public EntityAction useStrategy(Entity ent) {
        io.showInfo("Select your move");
        int selection = io.getMenuSelection(List.of(EntityAction.values()),true);
        return EntityAction.values()[selection];
    }
}
