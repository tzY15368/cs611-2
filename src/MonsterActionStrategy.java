public class MonsterActionStrategy extends AbstractActionStrategy {
    public MonsterActionStrategy(IODriver io) {
        super(io);
    }

    @Override
    public EntityAction useStrategy(Entity self) {
        Entity ent = self.findOpponent();
        if(ent==null){
            return EntityAction.Move;
        }
        return EntityAction.Attack;
    }
}
