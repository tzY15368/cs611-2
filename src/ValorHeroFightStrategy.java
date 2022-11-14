import java.util.Iterator;

public class ValorHeroFightStrategy extends AbstractFightTurnStrategy{
    public ValorHeroFightStrategy(IODriver io) {
        super(io);
    }

    @Override
    public Iterator<Pair> useStrategy(Squad s1, Squad s2) {
        return null;
    }

}
