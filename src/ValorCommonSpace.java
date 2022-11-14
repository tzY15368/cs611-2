import java.util.ArrayList;
import java.util.List;

interface AttrEffects{
    public List<String> getAffectedAttr();
}

enum SpecialSpaceType implements AttrEffects{
    Plain{
        @Override
        public List<String> getAffectedAttr() {
            List<String> res = new ArrayList<>();
            return res;
        }
    },
    Bush {
        @Override
        public List<String> getAffectedAttr() {
            List<String> res = new ArrayList<>();
            res.add("dexterity");
            return res;
        }
    },
    Cave {
        @Override
        public List<String> getAffectedAttr() {
            List<String> res = new ArrayList<>();
            res.add("agility");
            return res;
        }
    },
    Koulou {
        @Override
        public List<String> getAffectedAttr() {
            List<String> res = new ArrayList<>();
            res.add("strength");
            return res;
        }
    }
}

public class ValorCommonSpace extends CommonSpace{
    private List<String> attrBuff;

    public ValorCommonSpace(IODriver io, AbstractEntityFactory entityFactory, SpecialSpaceType spaceType) {
        super(io, entityFactory);
        this.attrBuff = spaceType.getAffectedAttr();
    }

    @Override
    public void handleEvent(Squad squad) {
        io.showInfo(String.format("%s is in %s",squad, this.getClass().getSimpleName()));
        // apply the attr buffs
        for(Entity ent : squad.listEntities()){
            for(String attr:this.attrBuff){
                try {
                    int originalValue = (int) ent.getClass().getField(attr).get(ent);
                    float newVal = originalValue * Constants.ATTR_BUFF_RATIO;
                    ent.getClass().getField(attr).set(ent, newVal);
                } catch (Exception nos){
                    io.showInfo("Warning: error applying buff:"+nos);
                    continue;
                }
            }
            if(attrBuff.size()!=0){
                io.showInfo(String.format("%s got %fx buff on attributes %s",ent, Constants.ATTR_BUFF_RATIO,attrBuff));
            }
        }
        // fight?
    }

    private char squadToIden(int index){
        if(this.squads.size()<index+1){
            return ' ';
        }
        Class<?> cls = squads.get(index).getEntityType();
        if(cls.getName().toLowerCase().contains("hero")){
            return 'H';
        } else {
            return 'M';
        }
    }

    @Override
    public String toString(){
        return "|"+squadToIden(0)+squadToIden(1);
    }

}
