package dev.saeedkhanloo.simulation.characters;

public abstract class Character {

    protected final int id;

    protected final int dmg;

    protected final int maxHp;
    protected int hp;

    public Character(int id, int hp, int dmg) {
        this.id = id;
        this.hp = hp;
        this.dmg = dmg;
        this.maxHp = hp;
    }

    public int getId() { return id; }
    public int getDmg() { return dmg; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }

    public void setHp(int hp) { this.hp = hp; }

    public boolean isAlive() { return this.hp > 0; }

    public int dealDamageTo(Character target) { return target.receiveDamageFrom(this); }

    protected abstract int receiveDamageFrom(Character attacker);
}
