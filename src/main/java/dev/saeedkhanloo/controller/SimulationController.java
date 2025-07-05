package dev.saeedkhanloo.controller;

import dev.saeedkhanloo.simulation.Simulation;
import java.util.LinkedHashMap;
import dev.saeedkhanloo.simulation.BattleEvent;
import java.util.concurrent.Flow;
import dev.saeedkhanloo.ui.SimulationPanel;
import javax.swing.SwingUtilities;

public class SimulationController {

    private String playerName;
    private int simulationSpeed;
    private Simulation simulation;

    public SimulationController(
            String playerName, int simulationSpeed, LinkedHashMap<String, Integer> creaturesList,
            boolean freeForAll) {

        this.playerName = playerName;
        this.simulationSpeed = simulationSpeed;

        this.simulation = new Simulation(creaturesList.size());
        if (freeForAll) { this.simulation.setFreeForAll(true); }
        this.simulation.setSimulationSpeed(simulationSpeed);

        for (String creature : creaturesList.keySet()) {
            for (int i = 0; i < creaturesList.get(creature); i++) {
                simulation.addCharacter(creature, i + 1);
            }
        }

    }

    public void startSimulation() { simulation.start(); }

    public String getPlayerName() { return playerName; }

    public Simulation getSimulation() { return simulation; }

    public int getSimulationSpeed() { return simulationSpeed; }

    public void listenToEvents(SimulationPanel ui) {
        simulation.getEventPublisher().subscribe(new Flow.Subscriber<BattleEvent>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(BattleEvent event) {
                String attackerName =
                        event.attacker.getClass().getSimpleName() + " with id " + event.attacker.getId();
                String targetName =
                        event.target.getClass().getSimpleName() + " with id " + event.target.getId();
                int dmg = event.damage;

                ui.appendBattleLog(attackerName + " hit " + targetName + " for " + dmg);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace(); // or log to UI
            }

            @Override
            public void onComplete() {
                ui.appendBattleLog("Simulation complete and winner is : "
                        + simulation.getArena().get(0).getClass().getSimpleName() + " with id "
                        + simulation.getArena().get(0).getId());

                // Ensure UI update happens on the EDT
                SwingUtilities.invokeLater(ui::drawHealthBars);
                ui.stopHealthBarTimer();
            }
        });

    }

}
