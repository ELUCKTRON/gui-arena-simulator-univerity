package dev.saeedkhanloo.ui;

import javax.swing.*;

import java.awt.*;

import dev.saeedkhanloo.controller.SimulationController;
import dev.saeedkhanloo.simulation.characters.Character;
import java.util.ArrayList;
import javax.swing.Timer;

public class SimulationPanel extends JPanel {
    private final MainWindow mainWindow;
    private final JButton backButton;
    private final JButton startButton;
    private final JButton exitButton;
    private final JPanel healthBarPanel;
    private final JTextArea combatLogArea;
    private final SimulationController simulationController;
    private final JLabel playerNameLabel;
    private final java.util.Map<Object, JProgressBar> healthBars = new java.util.HashMap<>(); // Object can be
                                                                                              // replaced with
                                                                                              // Character if
                                                                                              // available
    private static final int HEALTH_BAR_WIDTH = 250;
    private static final int HEALTH_BAR_HEIGHT = 20;
    private static final int LABEL_WIDTH = 200;
    private static final int ROW_HEIGHT = 30;
    private Timer healthBarTimer;

    public SimulationPanel(MainWindow mainwindow, SimulationController simulationController) {
        this.simulationController = simulationController;
        this.mainWindow = mainwindow;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Player name and simulation speed at the top
        String playerName = simulationController.getPlayerName();
        int simSpeed = simulationController.getSimulationSpeed();
        playerNameLabel = new JLabel("Player: " + playerName + "    Simulation Speed: " + simSpeed);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.add(playerNameLabel);

        // Top control buttons (now under player info)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        backButton = new JButton("Back to Setup");
        startButton = new JButton("Start Simulation");
        exitButton = new JButton("Exit");
        topPanel.add(backButton);
        topPanel.add(startButton);
        topPanel.add(exitButton);
        playerInfoPanel.add(topPanel);
        add(playerInfoPanel, BorderLayout.PAGE_START);

        // Health bars area (scrollable)
        healthBarPanel = new JPanel();
        healthBarPanel.setLayout(new BoxLayout(healthBarPanel, BoxLayout.Y_AXIS));
        JScrollPane healthScroll = new JScrollPane(healthBarPanel);
        healthScroll.setPreferredSize(new Dimension(HEALTH_BAR_WIDTH + LABEL_WIDTH + 40, ROW_HEIGHT * 10)); // Show
                                                                                                            // 10
                                                                                                            // rows
        add(healthScroll, BorderLayout.CENTER);

        // Populate health bars
        drawHealthBars();

        // Combat log area (scrollable)
        combatLogArea = new JTextArea(10, 40);
        combatLogArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(combatLogArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Combat Log"));
        add(logScroll, BorderLayout.SOUTH);

        // Exit button action
        exitButton.addActionListener(e -> System.exit(0));
        // Start button action
        startButton.addActionListener(e -> {
            simulationController.startSimulation();
            startButton.setEnabled(false);
        });
        // Back button action
        backButton.addActionListener(e -> {
            mainWindow.showSetupPanel();
        });

        // Start a timer to update health bars every 150ms
        healthBarTimer = new Timer(150, e -> drawHealthBars());
        healthBarTimer.start();
    }

    public void drawHealthBars() {
        healthBarPanel.removeAll();
        ArrayList<Character> arenaCharacters = simulationController.getSimulation().getArena();

        ArrayList<Character> snapshot;
        synchronized (arenaCharacters) {
            snapshot = new ArrayList<>(arenaCharacters);
        }

        for (Character character : snapshot) {
            if (!character.isAlive()) continue;
            String name = character.getClass().getSimpleName() + " with id  : " + character.getId();
            int maxHealth = character.getMaxHp();
            int currentHealth = character.getHp();
            JPanel charPanel = new JPanel();
            charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.X_AXIS));
            charPanel.setMaximumSize(new Dimension(HEALTH_BAR_WIDTH + LABEL_WIDTH + 20, ROW_HEIGHT));
            charPanel.setPreferredSize(new Dimension(HEALTH_BAR_WIDTH + LABEL_WIDTH + 20, ROW_HEIGHT));
            JLabel nameLabel = new JLabel(name);
            nameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, HEALTH_BAR_HEIGHT));
            nameLabel.setMaximumSize(new Dimension(LABEL_WIDTH, HEALTH_BAR_HEIGHT));
            JProgressBar healthBar = new JProgressBar(0, maxHealth);
            healthBar.setValue(currentHealth);
            healthBar.setStringPainted(true);
            healthBar.setPreferredSize(new Dimension(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT));
            healthBar.setMaximumSize(new Dimension(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT));
            // Set color based on health percentage
            double percent = (double) currentHealth / maxHealth;
            if (percent > 0.6) {
                healthBar.setForeground(new java.awt.Color(0, 180, 0)); // green
            } else if (percent > 0.3) {
                healthBar.setForeground(new java.awt.Color(255, 140, 0)); // orange
            } else {
                healthBar.setForeground(java.awt.Color.RED);
            }
            charPanel.add(nameLabel);
            charPanel.add(Box.createHorizontalStrut(10));
            charPanel.add(healthBar);
            healthBarPanel.add(charPanel);
            healthBars.put(character, healthBar);
        }
        healthBarPanel.revalidate();
        healthBarPanel.repaint();
    }

    public void appendBattleLog(String message) {
        combatLogArea.append(message + "\n");
        combatLogArea.setCaretPosition(combatLogArea.getDocument().getLength());
    }

    // Optionally, add a method to stop the timer when the panel is hidden or closed
    public void stopHealthBarTimer() { if (healthBarTimer != null) { healthBarTimer.stop(); } }

}
