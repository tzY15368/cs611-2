import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        this.itemSample = new ArrayList<>();
    }

    public static void registerItems(ConfigLoader cf){
        Map<String, List<List<String>>> data = cf.getData();
        ItemManager instance = ItemManager.getInstance();
        data.forEach((String key, List<List<String>> value)->{
            key = key.toLowerCase();
            if(key.contains("spell")){
                instance.itemSample.addAll(new SpellItem().fromConfig(value));
            } else if(key.contains("potions")){
                instance.itemSample.addAll(new PotionItem().fromConfig(value));
            } else if(key.contains("armory")){
                instance.itemSample.addAll(new ArmorItem().fromConfig(value));
            } else if(key.contains("weaponry")){
                instance.itemSample.addAll(new WeaponItem().fromConfig(value));
            }
        });
        System.out.println("ItemManager: added "+ItemManager.getInstance().itemSample.size()+" items");
    }

    public List<Item> genRandomTradableItems(){
        List<Item> result = new ArrayList<>();
        for(Item i : itemSample){
            Random r = new Random();
            if(r.nextInt(100)>50){
                int cnt = r.nextInt(2);
                for(int ii=0;ii<cnt;ii++){
                    result.add(i.clone());
                }
            }
        }
        return result;
    }
}
