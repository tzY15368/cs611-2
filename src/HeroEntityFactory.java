import java.util.ArrayList;
import java.util.List;

public class HeroEntityFactory extends AbstractEntityFactory{
    private int startHeroes = 2;
    private KeyInput[] wsy = new KeyInput[]{KeyInput.Y, KeyInput.W, KeyInput.S};

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
        ioDriver.showInfo(String.format("Choose %d heroes",startHeroes));
        this.ioDriver.showInfo("Hero menu:");

        while(newEntities.size()!=startHeroes){
            int selection = ioDriver.getMenuSelection(EntityManager.listHeroes());
            if(selection!=-1){
                newEntities.add(EntityManager.listHeroes().get(selection).cloneByLevel(0));
            }
            ioDriver.showInfo(String.format("Choose %d more",(startHeroes-newEntities.size())));
        }
        for(Entity ent : newEntities){
            squad.addEntity(ent);
        }
    }
}
