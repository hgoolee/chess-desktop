package ui;

import util.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchFrame extends JFrame {

    private JPanel bannerPanel;
    private JLabel bannerLabel;

    private JPanel buttonsPanel;
    private JPanel newGameButtonPanel;
    private JPanel loadGameButtonPanel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JFileChooser loadGameFileChooser;

    public LaunchFrame() {
        super("Chess for Desktop | Main");
        this.setIconImage(new ImageIcon(getClass().getResource("/logo.jpg")).getImage());
        loadInterface();
    }

    private void loadInterface() {
        initializeBannerPanel();
        initializeButtonsPanel();

        this.setLayout(new BorderLayout());
        this.add(bannerPanel, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void initializeBannerPanel() {
        bannerLabel = new JLabel();
        bannerLabel.setIcon(new ImageIcon(getClass().getResource("/launch_banner.png")));
        bannerPanel = new JPanel();
        bannerPanel.add(bannerLabel);
        bannerPanel.setPreferredSize(new Dimension(600, 250));
        bannerPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void initializeButtonsPanel() {
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreferencesFrame preferencesFrame = Core.getPreferencesFrame();
                preferencesFrame = new PreferencesFrame();
                setVisible(false);
            }
        });
        newGameButtonPanel = new JPanel(new GridLayout(1, 1));
        newGameButtonPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 25));
        newGameButtonPanel.add(newGameButton);
        loadGameButton = new JButton("Load Game");
        
      // Save/load
        loadGameButton.setEnabled(true);
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("load button pressed (in LaunchFrame)");
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(new JFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    Core.loadFile = fileChooser.getSelectedFile();
                    System.out.println("File load from: " + Core.loadFile.getName());
                }
                else {
                    System.out.println("Save command cancelled");
                }

                PreferencesFrame preferencesFrame = Core.getPreferencesFrame();
                preferencesFrame = new PreferencesFrame();
                setVisible(false);
            }
        });
        // Save/load
        
        loadGameButtonPanel = new JPanel(new GridLayout(1, 1));
        loadGameButtonPanel.setBorder(BorderFactory.createEmptyBorder(40, 25, 40, 50));
        loadGameButtonPanel.add(loadGameButton);

        buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.setPreferredSize(new Dimension(600, 150));
        buttonsPanel.add(newGameButtonPanel);
        buttonsPanel.add(loadGameButtonPanel);
    }

}
