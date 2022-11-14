import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ValorGame extends Game{

    private Playground playground;
    private SquadHolder[] shs;
    private Squad[] playerSquads;

    public ValorGame(String configPath) {
        super(configPath, new TerminalIODriver());

        Supplier<String> getinfo = ()->this.getInfo();
        this.io.registerShowInfo(getinfo);
        Supplier<String> getMap = () -> this.getMap();
        this.io.registerShowMap(getMap);
        this.shs = new SquadHolder[Constants.SQUAD_CNT];
        this.playerSquads = new Squad[Constants.SQUAD_CNT];
        int[] laneTaken = new int[]{0,0,0};
        int[] lanePos = new int[]{0,3,6};
        for(int i=0;i<Constants.SQUAD_CNT;i++){
            this.playerSquads[i] = new Squad(String.format("Hero-s-%d",i),new ArrayList<>(),io, new ValorHeroFightStrategy(io));
            HeroEntityFactory factory = new HeroEntityFactory(io);
            factory.fillSquad(this.playerSquads[i]);
            this.io.showInfo("Got heroes: \n"+this.playerSquads[i].toDetailString());
            List<Integer> startLanes = new ArrayList<>();
            for(int j=0;j<laneTaken.length;j++){
                if(laneTaken[j]<2){
                    startLanes.add(j+1);
                }
            }
            io.showInfo("Select this hero's starting nexus");
            int selection = io.getMenuSelection(startLanes,true);
            int laneSelection = startLanes.get(selection)-1;

            laneTaken[laneSelection]=laneTaken[laneSelection]+1;
            Pos loc = new Pos(7,lanePos[laneSelection]);
            lanePos[laneSelection] = lanePos[laneSelection]+1;
            SquadHoldable sh = new SquadHolder(this.playerSquads[i],loc);
            this.shs[i] = (SquadHolder) sh;
        }
        io.showInfo("Initializing playground...");
        this.playground = new Playground(new ValorSpaceFactory(),this.shs,io);
    }


    @Override
    public void start() {
        io.showInfo(playground.showBoard());
    }

    public String getInfo(){
        if(this.playerSquads==null){
            return "Game hasn't started yet!\n";
        }
        String status = "";
        for(int i=0;i<this.playerSquads.length;i++){
            status += String.format("%d:---",i);
            Squad playerSquad = this.playerSquads[i];
            status += playerSquad.toDetailString();
            Squad opponent = playerSquad.getCurrentOpponent();
            if(opponent!=null){
                status += "\nOpponent:\n" + opponent.toDetailString();
            }
            status += "\n";
        }
        return status;
    }

    public String getMap(){
        if(this.playground==null){
            return "Game hasn't started yet!\n";
        }
        return playground.showBoard();
    }

}
