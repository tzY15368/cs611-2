import java.util.Iterator;

public class DistFightTurnStrategy extends AbstractFightTurnStrategy{
    public DistFightTurnStrategy(IODriver io) {
        super(io);
    }

    @Override
    public Iterator<Pair> useStrategy(Squad s1, Squad s2) {
        return null;
    }
}
