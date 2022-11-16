import java.util.List;

public class HeroOppoentOptStrategy extends AbstractOpponentOptStrategy{
    public HeroOppoentOptStrategy(IODriver io) {
        super(io);
    }

    @Override
    public Entity getOpponent(Entity self) {
        List<Entity> possibleEntities = getAllPossiblePos(self);
        if(possibleEntities.size()==0){
            io.showInfo("Error: no possible opponents");
            return null;
        }
        io.showInfo("Select your opponent:");
        int selection = io.getMenuSelection(possibleEntities,false);
        if(selection==-1){
            io.showInfo("selection aborted");
            return null;
        }
        return possibleEntities.get(selection);
    }
}
