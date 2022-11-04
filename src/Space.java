import java.util.ArrayList;
import java.util.List;

public abstract class Space {
    protected List<Squad> squads;
    protected IODriver io;

    public Space(IODriver io){
        this.io = io;
        this.squads = new ArrayList<>();
    }

    public abstract String toString();

    public abstract boolean moveIn(Squad squad);

    // what does it return?
    public abstract void handleEvent();

    public abstract void moveOut(Squad squad);
}
