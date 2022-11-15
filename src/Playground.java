import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

interface IODriver {
    void registerShowInfo(Supplier<String> func);
    void registerShowMap(Supplier<String> func);
    KeyInput getKeyInput(KeyInput[] d);
    int getMenuSelection(List s, boolean must);
    void showInfo(String info);
}

interface SquadHoldable {
    Squad getSquad();
    void setPos(Pos p);
    Pos getPos();
}

public class Playground {
    private Space[][] board;
    private SquadHoldable[] squads;
    private IODriver io;
    private Squad[] sparseSquads;

    // TODO: make squad factories
    public Playground(AbstractSpaceFactory spaceFactory, SquadHoldable[] squads, IODriver io){
        this.board = spaceFactory.makeSpaces(io);
        this.squads = squads;
        for(SquadHoldable s: this.squads){
            // TODO: MAKE SURE POS IS VALID?
            this.board[s.getPos().x][s.getPos().y].moveIn(s.getSquad());
        }
        this.io = io;
    }

    // spareSquads don't move themselves, the entities inside MUST have valid positions
    public Playground(AbstractSpaceFactory spaceFactory, Squad[] sparseSquads, IODriver io){
        this.sparseSquads = sparseSquads;
        this.io = io;
        this.board = spaceFactory.makeSpaces(io);
        for(Squad s:sparseSquads){
            for(Entity ent:s.getAllEntities()){
                Pos p = ent.getPos();
                boolean res = this.board[p.x][p.y].moveIn(ent);
                if(!res){
                    io.showInfo(String.format("Move-in failed for entity %s",ent));
                    System.exit(-1);
                }
            }
        }
    }

    private SquadHoldable getSquadHolder(Squad squad){
        for(SquadHoldable sh:this.squads){
            if(squad == sh.getSquad()){
                return sh;
            }
        }
        return null;
    }

    public String showBoard(){
        StringBuilder sb = new StringBuilder("---------------------------\n");
        for (Space[] spaces : board) {
            for (int j = 0; j < board.length; j++) {
                sb.append(spaces[j]);
            }
            sb.append("|\n");
        }
        sb.append("---------------------------");
        return sb.toString();
    }

    public boolean handleEntityMove(MoveDir md, Entity ent){
        return false;
    }

    // returns true if move successful, false if not, forexample running into non-accessible spaces
    public boolean handleSquadMove(MoveDir md, Squad squad){
        Pos targetPos = null;
        SquadHoldable sh = this.getSquadHolder(squad);
        assert sh != null;
        switch (md) {
            case Up:
                targetPos = new Pos(sh.getPos().x-1, sh.getPos().y);
                break;
            case Down:
                targetPos = new Pos(sh.getPos().x+1, sh.getPos().y);
                break;
            case Left:
                targetPos = new Pos(sh.getPos().x, sh.getPos().y-1);
                break;
            case Right:
                targetPos = new Pos(sh.getPos().x, sh.getPos().y+1);
                break;
            default:
                break;
        }
        // check pos valid
        if(targetPos.x >= this.board.length || targetPos.y >= this.board[0].length || targetPos.x <0 || targetPos.y < 0){
            io.showInfo("Error: out of playground bounds");
            return false;
        }

        if(!this.board[targetPos.x][targetPos.y].moveIn(squad)){
            return false;
        }
        this.board[sh.getPos().x][sh.getPos().y].moveOut(squad);
        sh.setPos(targetPos);
        this.board[targetPos.x][targetPos.y].handleEvent(squad);

        String msg = String.format("Playground: Squad %s moved %s to %s", squad, md, targetPos);
        io.showInfo(msg);
        return true;
    }

}
