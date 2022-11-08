public abstract class AbstractFightStrategy {

    public AbstractFightStrategy(IODriver io){
        this.io = io;
    }

    protected IODriver io;

    public abstract EntityAction useStrategy(Entity ent);
}
