import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ValorGame extends Game{

    private Playground playground;
    private Squad heroSquad;
    private Squad monsterSquad;

    public ValorGame(String configPath) {
        super(configPath, new TerminalIODriver());

        Supplier<String> getinfo = ()->this.getInfo();
        this.io.registerShowInfo(getinfo);
        Supplier<String> getMap = () -> this.getMap();
        this.io.registerShowMap(getMap);

        AbstractEntityFactory heroFactory = new ValorHeroEntityFactory(io);
        AbstractEntityFactory monsterFactory = new ValorMonsterEntityFactory(io);
        // one monster squad and one hero squad
        this.heroSquad = new Squad("Hero-squad",new ArrayList<>(),io, new DistFightTurnStrategy(io));
        heroFactory.fillSquad(this.heroSquad);
        this.monsterSquad = monsterFactory.fillWithSquad(this.heroSquad);

        io.showInfo("Initializing playground...");
        Playground.initInstance(new ValorSpaceFactory(),new Squad[]{this.heroSquad, this.monsterSquad},io);
        this.playground = Playground.getInstance();
    }

    private boolean gameShouldStop(){
        boolean res = false;
        Space[] monsterRow = playground.getSpaceByRow(0);
        Space[] heroRow = playground.getSpaceByRow(-1);
        String tmpl = "Game ended: %s entered %s's nexus";
        for(Space s:monsterRow){
            for(Entity e:s.entities){
                if(e instanceof HeroEntity){
                    io.showInfo(String.format(tmpl,e,"monster"));
                    res = true;
                }
            }
        }
        for(Space s:heroRow){
            for(Entity e:s.entities){
                if(e instanceof MonsterEntity){
                    io.showInfo(String.format(tmpl,e,"hero"));
                    res = true;
                }
            }
        }
        return res;
    }

    @Override
    public void start() {
        io.showInfo(playground.showBoard());
        int cnt = 0;
        while(!gameShouldStop()){
            // heroes make their moves\
            io.showInfo("Round "+(++cnt));
            io.showInfo("It's heroes' move:");
            for(Entity ent: heroSquad.getAllEntities()){
                // if dead?
                io.showInfo(String.format("It's %s's turn",ent));
                ent.takeAction();
                io.showInfo(getMap());
            }

            // monsters make their moves
            io.showInfo("It's monsters' move");
            for(Entity ent: monsterSquad.getAllEntities()){
                ent.takeAction();
            }
            if(cnt%Constants.MONSTER_SPAWN_INTERVAL==Constants.MONSTER_SPAWN_INTERVAL-1){
                monsterSquad.spawnNewEntities();
                io.showInfo("Spawned new monsters");
            }
            io.showInfo(getMap());
            io.showInfo("Round over");
            io.showInfo("=================================");
        }
    }


    public String getInfo(){
        if(this.heroSquad==null){
            return "Game hasn't started yet!\n";
        }
        String status = "";
        Squad playerSquad = this.heroSquad;
        status += playerSquad.toDetailString();
        Squad opponent = this.monsterSquad;
        status += "\nOpponent:\n" + opponent.toDetailString();
        status += "\n";
        return status;
    }

    public String getMap(){
        if(this.playground==null){
            return "Game hasn't started yet!\n";
        }
        return playground.showBoard();
    }

}
