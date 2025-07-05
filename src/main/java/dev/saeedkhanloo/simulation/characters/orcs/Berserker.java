package dev.saeedkhanloo.simulation.characters.orcs;

import dev.saeedkhanloo.config.SimulationConfig;
import dev.saeedkhanloo.simulation.characters.Character;

public final class Berserker extends Orc {
    public Berserker(int id) {
        super(id, SimulationConfig.Orc.Berserker.HP, SimulationConfig.Orc.Berserker.DMG);
    }

    @Override
    protected int receiveDamageFrom(Character attacker) {
        int dmg = (int) (attacker.getDmg() * 1.5);
        this.hp -= dmg;
        return dmg;
    }
}
