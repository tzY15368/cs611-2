import java.util.List;

public class HeroEntityFactory extends AbstractEntityFactory{

    public HeroEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    @Override
    public Squad fillWithSquad(Squad squad) {
        return null;
    }

    @Override
    public void fillSquad(Squad squad) {

    }
}
