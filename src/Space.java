import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Space {
    protected List<Squad> squads;
    protected IODriver io;
    protected UUID uuid;

    public Space(IODriver io){
        this.io = io;
        this.squads = new ArrayList<>();
        this.uuid = UUID.randomUUID();
    }

    public abstract String toString();

    public abstract boolean moveIn(Squad squad);

    // what does it return?
    public abstract void handleEvent();

    public abstract void moveOut(Squad squad);
}
