package dev.saeedkhanloo.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final SetupPanel setupPanel;

    public MainWindow() {
        super("Arena Battle Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        setupPanel = new SetupPanel(this);

        cardPanel.add(setupPanel, "setup");

        setContentPane(cardPanel);
        cardLayout.show(cardPanel, "setup");
    }

    public void showSetupPanel() { cardLayout.show(cardPanel, "setup"); }
    public void addLayout(String layoutName, JPanel panel) { cardPanel.add(panel, layoutName); }
    public void showSimulationPanel() { cardLayout.show(cardPanel, "simulation"); }
}
