public class HeroMonsterSpaceFactory extends AbstractSpaceFactory{

    private final int boardX = 8;
    private final int boardY = 8;

    @Override
    public Space[][] makeSpaces() {
        Space[][] spaces = new Space[boardX][boardY];
        for(int i=0; i<spaces.length;i++){
            for(int j=0;j<spaces[0].length;j++){
                // ...
            }
        }
        return spaces;
    }
}
