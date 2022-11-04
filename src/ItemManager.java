import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager {
    private static ItemManager instance;

    private List<Item> itemSample;

    public static ItemManager getInstance(){
        if(instance==null){
            instance = new ItemManager();
        }
        return instance;
    }

    private ItemManager(){
        // TODO:load items from config
    }

    public List<Item> genRandomTradableItems(){
        List<Item> result = new ArrayList<>();
        for(Item i : itemSample){
            Random r = new Random();
            if(r.nextInt(100)>50){
                int cnt = r.nextInt(3);
                for(int ii=0;ii<cnt;ii++){
                    result.add(i.clone());
                }
            }
        }
        return result;
    }
}
