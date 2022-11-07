public class TraderEntityFactory extends AbstractEntityFactory{
    public TraderEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    @Override
    public Squad fillWithSquad(Squad squad) {
        return null;
    }

    @Override
    public void fillSquad(Squad squad) {

        squad.addEntity(new TraderEntity("Merchant-"+squad.getID().toString(),this.ioDriver));
    }
}
