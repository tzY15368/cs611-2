class Pos {
    int x;
    int y;

    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return ("(" + x + ", "+y+")");
    }
}

class SquadHolder implements SquadHoldable{
    private Squad squad;
    private Pos pos;

    public SquadHolder(Squad s, Pos p){
        squad = s;
        pos = p;
    }

    @Override
    public Squad getSquad() {
        return this.squad;
    }

    @Override
    public void setPos(Pos p) {
        this.pos = p;
    }

    @Override
    public Pos getPos() {
        return this.pos;
    }
}