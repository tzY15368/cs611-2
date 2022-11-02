public class Playground {
    private Space[][] board;
    private Pos playerpos;

    public Playground(AbstractSpaceFactory spaceFactory, int x, int y){
        this.board = spaceFactory.makeSpaces();
        this.playerpos = new Pos(x,y);
    }


    // returns true if move successful, false if not, forexample running into non-accessible spaces
    public boolean handleMove(MoveDir md){
        // check pos valid
        if(!this.board[playerpos.x][playerpos.y].moveIn()){
            return false;
        }
        // ...
    }

}
