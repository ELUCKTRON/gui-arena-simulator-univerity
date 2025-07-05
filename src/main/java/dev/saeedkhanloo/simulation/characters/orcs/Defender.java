package dev.saeedkhanloo.simulation.characters.orcs;

import dev.saeedkhanloo.config.SimulationConfig;
import dev.saeedkhanloo.simulation.characters.Character;

public final class Defender extends Orc {
    public Defender(int id) {
        super(id, SimulationConfig.Orc.Defender.HP, SimulationConfig.Orc.Defender.DMG);
    }

    @Override
    protected int receiveDamageFrom(Character attacker) {
        int dmg = attacker.getDmg() / 2;
        this.hp -= dmg;
        return dmg;
    }
}
