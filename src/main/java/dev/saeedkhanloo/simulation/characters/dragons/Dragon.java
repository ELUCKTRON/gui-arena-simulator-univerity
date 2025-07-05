package dev.saeedkhanloo.simulation.characters.dragons;

import dev.saeedkhanloo.simulation.characters.Character;

public abstract class Dragon extends Character {

    protected int dmgThreshold;

    public Dragon(int id, int hp, int dmg, int dmgThreshold) {
        super(id, hp, dmg);
        this.dmgThreshold = dmgThreshold;
    }

    @Override
    protected int receiveDamageFrom(Character attacker) {
        int dmg = Math.clamp(attacker.getDmg() - this.dmgThreshold, 0, this.hp);
        this.hp -= dmg;
        return dmg;
    }
}
