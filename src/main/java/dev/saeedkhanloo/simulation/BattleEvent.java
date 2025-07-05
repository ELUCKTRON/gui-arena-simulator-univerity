package dev.saeedkhanloo.simulation;

import dev.saeedkhanloo.simulation.characters.Character;

public class BattleEvent {
    public final Character attacker;
    public final Character target;
    public final int damage;

    public BattleEvent(Character attacker, Character target, int damage) {
        this.attacker = attacker;
        this.target = target;
        this.damage = damage;
    }
}
