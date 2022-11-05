import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Squad {
    private String name;
    private UUID uuid;
    private List<Entity> liveEntities;
    private List<Entity> downEntities;

    public Squad(String name){
        this(name, new ArrayList<>());
    }

    public Squad(String name, List<Entity> entities){
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.liveEntities = entities;
        this.downEntities = new ArrayList<>();

    }

    public int getLevel(){
        int sum = 0;
        for(Entity ent: liveEntities){
            sum += ent.getLevel();
        }
        return sum / liveEntities.size();
    }

    public UUID getID(){
        return this.uuid;
    }

    public List<Entity> listEntities(){
        return this.liveEntities;
    }

    public void addEntity(Entity ent){
        this.liveEntities.add(ent);
    }

    public String toDetailString(){
        return "";
    }

    public String toString(){
        return "Squad<"+this.name+">";
    }

    public boolean isWiped(){
        return this.liveEntities.size() == 0;
    }

}
