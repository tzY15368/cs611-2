interface IODriver {
    KeyInput getKeyInput();
    KeyInput getKeyInput(KeyInput[] d);
    void showInfo(String info);
}

public class Playground {
    private Space[][] board;
    private Pos playerpos;
    private IODriver io;

    public Playground(AbstractSpaceFactory spaceFactory, IODriver io, Pos initialPos){
        this.board = spaceFactory.makeSpaces(io);
        this.playerpos = initialPos;
        this.board[playerpos.x][playerpos.y].moveIn();
        this.io = io;
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
    public boolean handleMove(MoveDir md){
        Pos targetPos = null;
        switch (md) {
            case Up:
                targetPos = new Pos(playerpos.x-1, playerpos.y);
                break;
            case Down:
                targetPos = new Pos(playerpos.x+1, playerpos.y);
                break;
            case Left:
                targetPos = new Pos(playerpos.x, playerpos.y-1);
                break;
            case Right:
                targetPos = new Pos(playerpos.x, playerpos.y+1);
                break;
            default:
                break;
        }
        // check pos valid
        if(targetPos.x >= this.board.length || targetPos.y >= this.board[0].length || targetPos.x <0 || targetPos.y < 0){
            io.showInfo("Error: out of playground bounds");
            return false;
        }

        if(!this.board[targetPos.x][targetPos.y].moveIn()){
            return false;
        }
        this.board[playerpos.x][playerpos.y].moveOut();
        this.playerpos = targetPos;
        this.board[targetPos.x][targetPos.y].handleEvent();
        return true;
    }

}
