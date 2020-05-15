/*
 * Created by JFormDesigner on Sun May 10 18:52:18 NOVT 2020
 */

package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Alex Borzikov
 */
public class AboutDialog extends JDialog {

    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton okButton;


    public AboutDialog(Window owner) {
        super(owner);
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {

        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

        dialogPane.setLayout(new BorderLayout());

        contentPanel.setLayout(new GridBagLayout());
        ((GridBagLayout) contentPanel.getLayout()).columnWidths = new int[]{0, 0, 0};
        ((GridBagLayout) contentPanel.getLayout()).rowHeights = new int[]{0, 0, 0, 0};
        ((GridBagLayout) contentPanel.getLayout()).columnWeights = new double[]{0.0, 0.0, 1.0E-4};
        ((GridBagLayout) contentPanel.getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 1.0E-4};

        dialogPane.add(contentPanel, BorderLayout.CENTER);

        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
        ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

        okButton.setText("OK");
        buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        dialogPane.add(buttonBar, BorderLayout.SOUTH);

        contentPane.add(dialogPane, BorderLayout.CENTER);

        pack();

        setLocationRelativeTo(getOwner());
    }
}