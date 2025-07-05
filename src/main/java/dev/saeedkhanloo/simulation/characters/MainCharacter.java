package dev.saeedkhanloo.simulation.characters;

import dev.saeedkhanloo.config.SimulationConfig;

public final class MainCharacter extends Character {

    private final double armor;

    public MainCharacter(int id) {
        super(id, SimulationConfig.MainCharacter.HP, SimulationConfig.MainCharacter.DMG);
        this.armor = SimulationConfig.MainCharacter.Armor;
    }

    @Override
    protected int receiveDamageFrom(Character attacker) {
        int dmg = (int) (attacker.getDmg() / armor);
        this.hp -= dmg;
        return dmg;
    }
}
