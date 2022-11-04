import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Squad {
    private String name;
    private UUID uuid;
    private List<Entity> liveEntities;
    private List<Entity> downEntities;

    public Squad(String name){
        this(name, new Entity[]{});
    }

    public Squad(String name, Entity[] entities){
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.liveEntities = new ArrayList<Entity>(List.of(entities));
        this.downEntities = new ArrayList<>();

    }

    public List<Entity> listEntities(){
        return this.liveEntities;
    }

    public String toString(){
        return "Squad<"+this.name+">";
    }

    public boolean isWiped(){
        return this.liveEntities.size() == 0;
    }

}
