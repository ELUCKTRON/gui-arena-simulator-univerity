package dev.saeedkhanloo.simulation.characters.dragons;

import dev.saeedkhanloo.config.SimulationConfig;

public class BlackDragon extends Dragon {
    public BlackDragon(int id) {
        super(id, SimulationConfig.Dragon.Black.HP, SimulationConfig.Dragon.Black.DMG,
                SimulationConfig.Dragon.Black.DMG_THRESHOLD);

    }

}
