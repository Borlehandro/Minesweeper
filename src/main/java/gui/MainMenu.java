/*
 * Created by JFormDesigner on Sun May 10 15:26:25 NOVT 2020
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.intellij.uiDesigner.core.*;

/**
 * @author unknown
 */
public class MainMenu extends JFrame {
    public MainMenu() {
        this.setVisible(true);
        initComponents();
    }

    private void newGameButtonActionPerformed(ActionEvent e) {
        FieldParamsDialog fieldDialog = new FieldParamsDialog(this);
    }

    private void scoreButtonActionPerformed(ActionEvent e) {
        ScoreDialog scoreDialog = new ScoreDialog(this);
    }

    private void aboutButtonActionPerformed(ActionEvent e) {
        AboutDialog aboutDialog = new AboutDialog(this);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Alex Borzikov
        button4 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        setTitle("Minesweeper");
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayoutManager(6, 9, new Insets(0, 0, 0, 0), 5, -1));

        //---- button4 ----
        button4.setText("New Game");
        button4.addActionListener(e -> newGameButtonActionPerformed(e));
        contentPane.add(button4, new GridConstraints(3, 4, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- button2 ----
        button2.setText("High Scores");
        button2.setForeground(new Color(153, 153, 153));
        button2.addActionListener(e -> scoreButtonActionPerformed(e));
        contentPane.add(button2, new GridConstraints(4, 4, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- button3 ----
        button3.setText("About");
        button3.setForeground(new Color(102, 102, 102));
        button3.addActionListener(e -> aboutButtonActionPerformed(e));
        contentPane.add(button3, new GridConstraints(5, 4, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Alex Borzikov
    private JButton button4;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
