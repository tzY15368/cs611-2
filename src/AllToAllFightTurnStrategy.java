import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllToAllFightTurnStrategy extends AbstractFightTurnStrategy{

    private class MyIterator implements Iterator<Pair>{

        public MyIterator(Squad s1, Squad s2, IODriver io){
            this.s1 = s1;
            this.s2 = s2;
            this.io = io;
        }
        private IODriver io;
        private int s1Idx = 0;
        private Squad s1;
        private Squad s2;


        @Override
        public boolean hasNext() {
            return s1Idx != s1.listEntities().size();
        }

        @Override
        public Pair next() {
            Entity e1 = s1.listEntities().get(s1Idx);
            s1Idx++;
            io.showInfo(String.format("%s, select the %s you want to fight:",e1, s2.listEntities().get(0).getClass()));
            int selection = io.getMenuSelection(s2.listEntities(), true);
            Entity e2 = s2.listEntities().get(selection);
            return new Pair(e1,e2);
        }
    }
    
    public AllToAllFightTurnStrategy(IODriver io){
        super(io);
    }

    @Override
    public Iterator<Pair> useStrategy(Squad s1, Squad s2) {
        return new MyIterator(s1, s2, io);
    }
}
