import java.util.Iterator;

public abstract class AbstractFightTurnStrategy{
    protected IODriver io;

    public AbstractFightTurnStrategy(IODriver io){
        this.io = io;
        io.showInfo("Fight turn strategy: "+this.getClass().getName());
    }

    public abstract Iterator<Pair> useStrategy(Squad s1, Squad s2);

}
