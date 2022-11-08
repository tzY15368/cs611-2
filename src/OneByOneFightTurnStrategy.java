import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OneByOneFightTurnStrategy extends AbstractFightTurnStrategy{

    public OneByOneFightTurnStrategy(IODriver io){
        super(io);
    }

    @Override
    public Iterator<Pair> useStrategy(Squad s1, Squad s2) {
        List<Pair> res = new ArrayList<>();
        for(int i=0;i<s1.listEntities().size();i++){
            res.add(new Pair(s1.listEntities().get(i), s2.listEntities().get(i)));
        }
        return res.iterator();
    }
}
