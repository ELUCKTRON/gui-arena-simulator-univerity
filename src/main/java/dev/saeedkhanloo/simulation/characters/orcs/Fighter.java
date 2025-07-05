package dev.saeedkhanloo.simulation.characters.orcs;

import dev.saeedkhanloo.config.SimulationConfig;
import dev.saeedkhanloo.simulation.characters.Character;

public final class Fighter extends Orc {
    public Fighter(int id) { super(id, SimulationConfig.Orc.Fighter.HP, SimulationConfig.Orc.Fighter.DMG); }

    @Override
    protected int receiveDamageFrom(Character attacker) {
        this.hp -= attacker.getDmg();
        return attacker.getDmg();
    }
}
