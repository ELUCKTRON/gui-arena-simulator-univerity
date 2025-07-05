package dev.saeedkhanloo.simulation;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.List;

import dev.saeedkhanloo.simulation.characters.Character;
import dev.saeedkhanloo.simulation.characters.orcs.Fighter;
import dev.saeedkhanloo.simulation.characters.orcs.Defender;
import dev.saeedkhanloo.simulation.characters.orcs.Berserker;
import dev.saeedkhanloo.simulation.characters.dragons.RedDragon;
import dev.saeedkhanloo.simulation.characters.dragons.BlackDragon;
import dev.saeedkhanloo.simulation.characters.MainCharacter;
import dev.saeedkhanloo.simulation.characters.orcs.Orc;
import dev.saeedkhanloo.simulation.characters.dragons.Dragon;

public class Simulation {

    private final SubmissionPublisher<BattleEvent> eventPublisher = new SubmissionPublisher<>();

    private final ScheduledThreadPoolExecutor executor;
    private final ArrayList<Character> arena;

    private volatile boolean isRunning = false;
    private boolean freeForAll;
    private int simulationSpeed;

    public Simulation(int arenaSize) {
        this.executor = new ScheduledThreadPoolExecutor(arenaSize);
        this.arena = new ArrayList<>(arenaSize);
        this.freeForAll = false;
        this.simulationSpeed = 500;
    }

    public void setFreeForAll(boolean value) { this.freeForAll = value; }
    public boolean isFreeForAll() { return freeForAll; }

    public void setSimulationSpeed(int value) { this.simulationSpeed = value; }
    public int getSimulationSpeed() { return simulationSpeed; }

    public ArrayList<Character> getArena() { return arena; }
    public SubmissionPublisher<BattleEvent> getEventPublisher() { return eventPublisher; }

    public void addCharacter(String character, int id) {

        switch (character) {
            case "Fighter":
                arena.add(new Fighter(id));
                break;
            case "Defender":
                arena.add(new Defender(id));
                break;
            case "Berserker":
                arena.add(new Berserker(id));
                break;
            case "Red Dragon":
                arena.add(new RedDragon(id));
                break;
            case "Black Dragon":
                arena.add(new BlackDragon(id));
                break;
            case "Main Characters":
                arena.add(new MainCharacter(id));
                break;
            default:
                throw new IllegalArgumentException("Invalid character: " + character);
        }
    }

    public void start() {
        isRunning = true;

        for (var character : arena) {
            executor.scheduleWithFixedDelay(() -> {
                synchronized (arena) {
                    if (!isRunning || !character.isAlive()) { return; } // we dead

                    var array = arena.stream().filter(c -> c != character).toArray(Character[]::new);

                    // we won
                    if (array.length == 0) {
                        finishSimulation();
                        eventPublisher.close();
                        return;
                    }

                    // Pick a target that is not in the same group as the attacker, unless freeForAll
                    Character target = array[ThreadLocalRandom.current().nextInt(array.length)];
                    if (!freeForAll) {
                        List<Character> validTargets = arena.stream()
                                .filter(c -> c != character && (freeForAll || !isSameGroup(character, c)))
                                .toList();
                        if (validTargets.isEmpty()) {
                            finishSimulation();
                            eventPublisher.close();
                            return;
                        }
                        target = validTargets.get(ThreadLocalRandom.current().nextInt(validTargets.size()));
                    }

                    var dmg = character.dealDamageTo(target);

                    eventPublisher.submit(new BattleEvent(character, target, dmg));

                    if (!target.isAlive()) { arena.remove(target); }
                }
            }, 0, simulationSpeed, TimeUnit.MILLISECONDS);
        }
    }

    private void finishSimulation() {
        isRunning = false;

        executor.shutdown();
        try {

            boolean isTerminated = executor.awaitTermination(600, TimeUnit.MILLISECONDS);
            if (!isTerminated) { executor.shutdownNow(); }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Optional: clean-up, logs, or event trigger
        }
    }

    // Helper function to check if two characters are in the same group
    private boolean isSameGroup(Character a, Character b) {
        // Orcs
        if (a instanceof Orc &&
                b instanceof Orc)
            return true;
        // Dragons
        if (a instanceof Dragon &&
                b instanceof Dragon)
            return true;
        // MainCharacter
        if (a instanceof MainCharacter &&
                b instanceof MainCharacter)
            return true;
        return false;
    }

}
