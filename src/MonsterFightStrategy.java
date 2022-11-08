public class MonsterFightStrategy extends AbstractFightStrategy {
    public MonsterFightStrategy(IODriver io) {
        super(io);
    }

    @Override
    public EntityAction useStrategy(Entity ent) {
        return EntityAction.Attack;
    }
}
