import java.util.Random;

public class HeroMonsterSpaceFactory extends AbstractSpaceFactory {

    private final int boardX = 8;
    private final int boardY = 8;

    @Override
    public Space[][] makeSpaces() {
        Space[][] spaces = new Space[boardX][boardY];
        Random r = new Random();
        for (int i = 0; i < spaces.length; i++) {
            for (int j = 0; j < spaces[0].length; j++) {
                int val = r.nextInt(100);
                if (val < 20) {
                    spaces[i][j] = new InaccessibleSpace();
                } else if (val < 40) {
                    spaces[i][j] = new MarketSpace();
                } else {
                    spaces[i][j] = new CommonSpace();
                }
            }
        }
        return spaces;
    }
}
