import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Space {
    protected List<Squad> squads;
    protected IODriver io;
    protected UUID uuid;
    protected AbstractEntityFactory entityFactory;

    public Space(IODriver io, AbstractEntityFactory entityFactory){
        this.io = io;
        this.squads = new ArrayList<>();
        this.uuid = UUID.randomUUID();
        this.entityFactory = entityFactory;
    }

    public abstract String toString();

    public abstract boolean moveIn(Squad squad);

    public abstract boolean moveIn(Entity ent);

    // what does it return?
    public abstract void handleEvent(Squad squad);

    public abstract void moveOut(Squad squad);
}
