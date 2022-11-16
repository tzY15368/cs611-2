import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOpponentOptStrategy {

    protected IODriver io;

    public AbstractOpponentOptStrategy(IODriver io){
        this.io = io;
    }

    protected List<Entity> getAllPossiblePos(Entity self){
        List<Entity> res = new ArrayList<>();

        Pos p = self.getPos();
        for(int i=1;i<=Constants.ATTACK_DISTANCE;i++){
            Pos[] dirs = new Pos[]{
                    new Pos(p.x-i, p.y),
                    new Pos(p.x+i,p.y),
                    new Pos(p.x, p.y-i),
                    new Pos(p.x, p.y+i)
            };
            for(Pos dir : dirs){
                try{
                    List<Entity> entities = Playground.getInstance().getSpaceByPos(dir).getEntities();
                    res.addAll(entities);
                } catch (Exception e){
                    continue;
                }
            }
        }
        return res;
    }

    public abstract Entity getOpponent(Entity self);

}
