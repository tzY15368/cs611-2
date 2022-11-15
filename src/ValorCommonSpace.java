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
    }

    @Override
    public boolean moveIn(Squad squad) {
        io.showInfo("Squads dont move");
        return false;
    }

    @Override
    public boolean moveIn(Entity ent){
        if(this.entities.size()==0 || (this.entities.size()==1 && this.entities.get(0).getClass()!=ent.getClass())){
            this.entities.add(ent);
            applyBuff(ent,Constants.ATTR_BUFF_RATIO);
            return true;
        }
        return false;
    }

    @Override
    public void moveOut(Squad squad){
    }

    @Override
    public void moveOut(Entity ent){
        this.entities.remove(ent);
        applyBuff(ent, 1/Constants.ATTR_BUFF_RATIO);
    }

    private void applyBuff(Entity ent, float ratio){
        if(!(ent instanceof HeroEntity)){
            return;
        }
        io.showInfo(this+" Applying buff to "+ent);
        for(String attr:this.attrBuff){
            try {
                int originalValue = (int) ent.getClass().getField(attr).get(ent);
                int newVal = (int) (originalValue * ratio);
                ent.getClass().getField(attr).set(ent, newVal);
            } catch (Exception nos){
                io.showInfo("Warning: error applying buff:"+nos);
                continue;
            }
        }
        if(attrBuff.size()!=0){
            io.showInfo(String.format("%s got %fx buff on attributes %s",ent, ratio,attrBuff));
        }
    }


    public String toString(){
        String base = "|";
        if(this.entities.size()==0){
            base += "  ";
        }
        else if(this.entities.size()==1){
            base += this.entities.get(0).toIdentifier();
            base += " ";
        } else if(this.entities.size()==2){
            base += this.entities.get(0).toIdentifier();
            base += this.entities.get(1).toIdentifier();
        }
        return base;
    }

}
