package gui;

import api.ServerCommand;
import server_api.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainMenu extends JFrame {

    private JButton newGameButton;
    private JButton scoreButton;
    private JButton aboutButton;
    private JPanel menuPane;

    private final ServerController serverController;

    public MainMenu() throws IOException {

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                try {
                    serverController.send(ServerCommand.CLOSE);
                    serverController.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                dispose();
            }
        });

        serverController = new ServerController();

        newGameButton.addMouseListener(new ButtonMouseListener(newGameButton));
        scoreButton.addMouseListener(new ButtonMouseListener(scoreButton));
        aboutButton.addMouseListener(new ButtonMouseListener(aboutButton));

        newGameButton.addActionListener(e -> new FieldParamsDialog(this));

        aboutButton.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this,
                        serverController.send(ServerCommand.ABOUT));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        scoreButton.addActionListener(e -> new HighScoresFrame(serverController));

        this.setSize(75, 150);
        this.setVisible(true);
        this.setResizable(false);
        this.setContentPane(menuPane);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        menuPane = new JPanel();
        menuPane.setLayout(new GridBagLayout());
        newGameButton = new JButton();
        newGameButton.setText("New Game");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPane.add(newGameButton, gbc);
        scoreButton = new JButton();
        scoreButton.setText("High Score");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPane.add(scoreButton, gbc);
        aboutButton = new JButton();
        aboutButton.setText("About");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPane.add(aboutButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        menuPane.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        menuPane.add(spacer2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return menuPane;
    }

}
