import java.util.Random;

public class ValorSpaceFactory extends AbstractSpaceFactory{
    @Override
    public Space[][] makeSpaces(IODriver io) {
        Space[][] spaces = new Space[Constants.PLAYGROUND_X][Constants.PLAYGROUND_Y];
        // inaccessible space
        for(int i=0;i<spaces.length;i++){
            spaces[i][2] = new InaccessibleSpace(io, null);
            spaces[i][5] = new InaccessibleSpace(io, null);
        }
        for(int i : new int[]{0,1,3,4,6,7}){
            spaces[0][i] = new NexusSpace(io, null, 'M');
            spaces[7][i] = new NexusSpace(io, new TraderEntityFactory(io),'H');
        }
        for(int i=0;i<spaces.length;i++){
            for(int j=0;j<spaces.length;j++){
                if(spaces[i][j]==null){
                    Random r = new Random();
                    SpecialSpaceType spaceType = SpecialSpaceType.values()[r.nextInt(4)];
                    spaces[i][j] = new ValorCommonSpace(io,null,spaceType);
                }
            }
        }
        return spaces;
    }
}
