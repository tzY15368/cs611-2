public class Playground {
    private Space[][] board;
    private Pos playerpos;

    public Playground(AbstractSpaceFactory spaceFactory, int x, int y){
        this.board = spaceFactory.makeSpaces();
        this.playerpos = new Pos(x,y);
    }

    public void printBoard(){
        System.out.flush();
        System.out.println("---------------------------");
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                System.out.print(board[i][j]);
            }
            System.out.print("|\n");
        }
        System.out.println("---------------------------");
    }


    // returns true if move successful, false if not, forexample running into non-accessible spaces
    public boolean handleMove(MoveDir md){
        // check pos valid
        if(!this.board[playerpos.x][playerpos.y].moveIn()){
            return false;
        }
        return true;
        // ...
    }

}
