public class MonsterActionStrategy extends AbstractActionStrategy {
    public MonsterActionStrategy(IODriver io) {
        super(io);
    }

    @Override
    public EntityAction useStrategy() {
        return EntityAction.Attack;
    }
}
