package dev.saeedkhanloo.ui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dev.saeedkhanloo.controller.SimulationController;

public class SetupPanel extends JPanel {
    private final JTextField playerNameField;
    private final Map<String, JSpinner> creatureSpinners;
    private final JSpinner speedSpinner;
    private final JButton exitButton;
    private final JButton startButton;
    private final JCheckBox freeForAllCheckBox;

    public SetupPanel(MainWindow mainWindow) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("Battle simulation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(10));
        JLabel subtitle = new JLabel("Choose fighting characters:");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(subtitle);
        centerPanel.add(Box.createVerticalStrut(15));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setOpaque(false);

        // Player name
        formPanel.add(new JLabel("Player name:"));
        playerNameField = new JTextField();
        formPanel.add(playerNameField);

        // Creature spinners
        creatureSpinners = new LinkedHashMap<>();
        String[] creatures =
                {"Main Characters", "Fighter", "Defender", "Berserker", "Red Dragon", "Black Dragon"};
        for (String creature : creatures) {
            formPanel.add(new JLabel(creature + ":"));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
            creatureSpinners.put(creature, spinner);
            formPanel.add(spinner);
        }

        // Simulation speed
        formPanel.add(new JLabel("Simulation speed (ms):"));
        speedSpinner = new JSpinner(new SpinnerNumberModel(500, 50, 5000, 50));
        formPanel.add(speedSpinner);

        // Free For All checkbox
        formPanel.add(new JLabel("Free For All:"));
        freeForAllCheckBox = new JCheckBox();
        formPanel.add(freeForAllCheckBox);

        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        exitButton = new JButton("Exit");
        startButton = new JButton("Start Simulation");
        buttonPanel.add(exitButton);
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        exitButton.addActionListener(e -> System.exit(0));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkedHashMap<String, Integer> creaturesList = new LinkedHashMap<>();
                for (String creature : creatureSpinners.keySet()) {
                    creaturesList.put(creature, (Integer) creatureSpinners.get(creature).getValue());
                }
                int simulationSpeed = (Integer) speedSpinner.getValue();
                String playerName = playerNameField.getText();

                SimulationController simulationController =
                        new SimulationController(playerName, simulationSpeed, creaturesList,
                                freeForAllCheckBox.isSelected());

                SimulationPanel simulationPanel = new SimulationPanel(mainWindow, simulationController);

                simulationController.listenToEvents(simulationPanel);

                mainWindow.addLayout("simulation", simulationPanel);
                mainWindow.showSimulationPanel();
            }
        });
    }

    public SetupPanel() { this(null); }

    public String getPlayerName() { return playerNameField.getText(); }
    public int getCreatureCount(String creature) {
        return (Integer) creatureSpinners.get(creature).getValue();
    }
    public int getSimulationSpeed() { return (Integer) speedSpinner.getValue(); }
}
