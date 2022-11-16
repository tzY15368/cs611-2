import java.util.List;

public class MonsterOpponentOptStrategy extends AbstractOpponentOptStrategy{
    public MonsterOpponentOptStrategy(IODriver io) {
        super(io);
    }

    @Override
    public Entity getOpponent(Entity self) {
        List<Entity> possibleEntities = getAllPossiblePos(self);
        if(possibleEntities.size()==0)return null;
        return possibleEntities.get(0);
    }
}
