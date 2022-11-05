import java.util.List;

public abstract class AbstractEntityFactory {
    protected IODriver ioDriver;

    public AbstractEntityFactory(IODriver ioDriver){
        this.ioDriver = ioDriver;
    }

    public abstract Squad fillWithSquad(Squad squad);

    public abstract void fillSquad(Squad squad);
}
