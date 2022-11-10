import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

class Pair {
    Entity first;
    Entity second;

    public Pair(Entity f, Entity s){
        this.first = f;
        this.second = s;
    }
}

public class Squad {
    private String name;
    private UUID uuid;
    private List<Entity> liveEntities;
    private List<Entity> downEntities;
    private IODriver io;
    private AbstractFightTurnStrategy fightTurnStrategy;

    public Squad(String name, List<Entity> entities, IODriver io, AbstractFightTurnStrategy fightTurnStrategy){
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.liveEntities = entities;
        this.downEntities = new ArrayList<>();
        this.io = io;
        this.fightTurnStrategy = fightTurnStrategy;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Alive:\n");
        for(Entity ent : this.listEntities()){
            sb.append(ent.toString() + "\n");
        }
        if(this.downEntities.size()!=0){
            sb.append("Down:\n");
            for(Entity ent : this.downEntities){
                sb.append(ent.toString() + "\n");
            }
        }
        return this.toString() + sb;
    }

    public String toString(){
        return "Squad<"+this.name+">";
    }

    public boolean isWiped(){
        return this.liveEntities.size() == 0;
    }

    public void fight(Squad targetSquad){
        io.showInfo(String.format("%s is fighting %s!", this, targetSquad));
        while(!(this.isWiped()||targetSquad.isWiped())){
            io.showInfo("No squad is dead yet, keep fighting.");
            Iterator<Pair> pairs = this.fightTurnStrategy.useStrategy(this, targetSquad);
            while(pairs.hasNext()){
                Pair item = pairs.next();
                item.first.doFight(item.second);
                for(Entity ent : this.liveEntities){
                    if(ent.isDead()){
                        this.liveEntities.remove(ent);
                        this.downEntities.add(ent);
                    }
                }
                for(Entity ent : targetSquad.liveEntities){
                    if(ent.isDead()){
                        targetSquad.liveEntities.remove(ent);
                        targetSquad.downEntities.add(ent);
                    }
                }
            }
        }
        if(this.isWiped()){
            io.showInfo("Everyone's dead! End of game");
            System.exit(0);
        } else if(targetSquad.isWiped()){
            io.showInfo("All monsters are dead! Heroes are upgraded.");
            int monsterLvl = targetSquad.getLevel();
            for(Entity ent : this.listEntities()){
                // gain gold
                ent.getInventory().setGold(ent.inventory.getGold() + monsterLvl * Constants.MONSTER_LVL_GOLD_RATIO);

                // gain exp
                int newLevel = ent.gainExp((int) (targetSquad.downEntities.size() * Constants.MONSTER_LVL_EXP_RATIO));
                ent.HP = (int) (ent.HP * Constants.HERO_LEVELUP_MANA_RATIO);

                io.showInfo(ent.toString()+"HP And mana increased by "+Constants.HERO_LEVELUP_MANA_RATIO);

            }

            // revive with half hp and mana, nothing gained
            for(Entity ent : this.downEntities){
                ent.revive();
                io.showInfo("Revived: "+ent);
            }

            this.liveEntities.addAll(this.downEntities);
            this.downEntities.clear();
        } else {
            io.showInfo("invalid state!");
            System.exit(-2);
        }
    }

}
