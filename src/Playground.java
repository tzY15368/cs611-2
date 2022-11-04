interface IODriver {
    KeyInput getKeyInput();
    KeyInput getKeyInput(KeyInput[] d);
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

    private SquadHoldable getSquadHolder(Squad squad){
        for(SquadHoldable sh:this.squads){
            if(squad == sh.getSquad()){
                return sh;
            }
        }
        return null;
    }

    public void printBoard(){
        System.out.flush();
        StringBuilder sb = new StringBuilder("---------------------------\n");
        for (Space[] spaces : board) {
            for (int j = 0; j < board.length; j++) {
                sb.append(spaces[j]);
            }
            sb.append("|\n");
        }
        sb.append("---------------------------");
        io.showInfo(sb.toString());
    }


    // returns true if move successful, false if not, forexample running into non-accessible spaces
    public boolean handleMove(MoveDir md, Squad squad){
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
        this.board[targetPos.x][targetPos.y].handleEvent();

        String msg = String.format("Playground: Squad %s moved %s to %s", squad, md, targetPos);
        io.showInfo(msg);
        return true;
    }

}
