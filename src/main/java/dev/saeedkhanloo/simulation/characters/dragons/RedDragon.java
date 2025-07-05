package dev.saeedkhanloo.simulation.characters.dragons;

import dev.saeedkhanloo.config.SimulationConfig;

public final class RedDragon extends Dragon {
    public RedDragon(int id) {
        super(id, SimulationConfig.Dragon.Red.HP, SimulationConfig.Dragon.Red.DMG,
                SimulationConfig.Dragon.Red.DMG_THRESHOLD);
    }
}
