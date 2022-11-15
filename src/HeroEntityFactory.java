import java.util.ArrayList;
import java.util.List;

public class HeroEntityFactory extends AbstractEntityFactory{

    public HeroEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    @Override
    public Squad fillWithSquad(Squad squad) {
        // NOT SUPPORTED
        return null;
    }

    @Override
    public void fillSquad(Squad squad) {
        List<Entity> newEntities = new ArrayList<>();
        ioDriver.showInfo(String.format("Choose %d to %d heroes",
                Constants.HERO_PARTY_SIZE_MIN, Constants.HERO_PARTY_SIZE_MAX));
        this.ioDriver.showInfo("Hero menu:");

        while(newEntities.size()< Constants.HERO_PARTY_SIZE_MAX){
            ioDriver.showInfo(String.format("%d Chosen", newEntities.size()));
            int selection = ioDriver.getMenuSelection(EntityManager.listHeroes(),false);
            if(selection==-1){
                if(newEntities.size()>=Constants.HERO_PARTY_SIZE_MIN){
                    break;
                } else {
                    continue;
                }
            }
            newEntities.add(EntityManager.listHeroes().get(selection).cloneByLevel(0));
        }
        for(Entity ent : newEntities){
            squad.addEntity(ent);
        }
    }
}
