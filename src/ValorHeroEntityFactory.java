import java.util.ArrayList;
import java.util.List;

public class ValorHeroEntityFactory extends HeroEntityFactory{

    private int[] laneTaken = new int[]{0,0,0};
    private int[] lanePos = new int[]{0,3,6};
    public ValorHeroEntityFactory(IODriver ioDriver) {
        super(ioDriver);
    }

    @Override
    public void fillSquad(Squad squad){
        super.fillSquad(squad);
        ioDriver.showInfo("Your squad:"+squad.toDetailString());
        for(Entity ent:squad.getAllEntities()){
            List<Integer> startLanes = new ArrayList<>();
            for(int j=0;j<laneTaken.length;j++){
                if(laneTaken[j]<2){
                    startLanes.add(j+1);
                }
            }
            ioDriver.showInfo(String.format("Select hero %s's starting nexus",ent));
            int selection = ioDriver.getMenuSelection(startLanes,true);
            int laneSelection = startLanes.get(selection)-1;
            laneTaken[laneSelection]=laneTaken[laneSelection]+1;
            Pos loc = new Pos(7,lanePos[laneSelection]);
            ent.setPos(loc);
        }
    }
}
