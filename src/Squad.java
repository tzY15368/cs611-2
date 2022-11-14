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
    private Squad currentOpponent;

    public Squad(String name, List<Entity> entities, IODriver io, AbstractFightTurnStrategy fightTurnStrategy){
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.liveEntities = entities;
        this.downEntities = new ArrayList<>();
        this.io = io;
        this.fightTurnStrategy = fightTurnStrategy;
    }
    public Class<?> getEntityType(){
        return this.liveEntities.get(0).getClass();
    }

    public int getLevel(){
        int sum = 0;
        for(Entity ent: liveEntities){
            sum += ent.getLevel();
        }
        return sum / (liveEntities.size()+downEntities.size());
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

    public Squad getCurrentOpponent(){
        return currentOpponent;
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

    private void fightInPairs(Squad s1, Squad s2){
        Iterator<Pair> pairs = s1.fightTurnStrategy.useStrategy(s1, s2);
        while(pairs.hasNext()){
            Pair item = pairs.next();
            item.first.doFight(item.second);
            for(Entity ent : this.liveEntities){
                if(ent.isDead()){
                    io.showInfo(String.format("%s is down!",ent));
                    this.downEntities.add(ent);
                }
            }
            for(Entity ent : this.downEntities){
                this.liveEntities.remove(ent);
            }


            for(Entity ent : s2.liveEntities){
                if(ent.isDead()){
                    this.io.showInfo(String.format("%s is down!",ent));
                    s2.downEntities.add(ent);
                }
            }
            for(Entity ent: s2.downEntities){
                s2.liveEntities.remove(ent);
            }

        }
    }

    public void fight(Squad targetSquad){
        this.currentOpponent = targetSquad;
        io.showInfo(String.format("%s is fighting %s!", this, targetSquad));
        boolean prompt = false;
        while(!(this.isWiped()||targetSquad.isWiped())){
            if(prompt){
                io.showInfo("No squad is dead yet, keep fighting.");
            } else {
                prompt = true;
            }
            this.fightInPairs(this, targetSquad);
            if(targetSquad.isWiped())break;
            io.showInfo("Player's moves finished, monsters' move");
            fightInPairs(targetSquad, this);
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
        this.currentOpponent = null;
    }

}
