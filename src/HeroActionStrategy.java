import java.util.List;

public class HeroActionStrategy extends AbstractActionStrategy {
    public HeroActionStrategy(IODriver io) {
        super(io);
    }

    @Override
    public EntityAction useStrategy(Entity self) {
        io.showInfo("Select your move");
        int selection = io.getMenuSelection(List.of(EntityAction.values()),true);
        return EntityAction.values()[selection];
    }
}
