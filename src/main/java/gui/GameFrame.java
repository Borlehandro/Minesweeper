package gui;

import model.ExternalCell;
import model.Field;
import score.ScoreItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.*;
import java.util.Arrays;

public class GameFrame extends JFrame {
    private long gameTimeMillis;
    private Timer gameTimer;
    private final Field field;

    private JPanel gamePanel;
    private JPanel fieldPanel;
    private JLabel timeLabel;

    public GameFrame(int size, int mines) {
        $$$setupUI$$$();
        this.setVisible(true);
        field = new Field(size, mines);
        startTimer(System.currentTimeMillis());
        this.setContentPane(gamePanel);
        fieldPanel.addMouseMotionListener(new FieldMotionListener());
        fieldPanel.addMouseListener(new CellClickListener());
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        gamePanel = new JPanel();
        gamePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        timeLabel = new JLabel();
        timeLabel.setText("Time");
        gamePanel.add(timeLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gamePanel.add(fieldPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return gamePanel;
    }

    private void createUIComponents() {
        fieldPanel = new FieldPanel();
    }

    private class FieldPanel extends JPanel {

        int lastPositionX = 0;
        int lastPositionY = 0;

        int lastClickX = -1;
        int lastClickY = -1;

        private static final int SPACING = 5;
        private static final int CELL_SIZE = 50;

        @Override
        protected void paintComponent(Graphics g) {

            boolean success = true;

            ExternalCell[][] cells = field.getExternalCells();
            System.err.println("REPAINT!");

            // Debug
//            Cell[][] test = field.getCells();
//            for (Cell[] item : test) {
//                for (int j = 0; j < cells.length; j++) {
//                    System.out.print(item[j] + " ");
//                }
//                System.out.println();
//            }

            System.err.println("LastClick : " + lastClickX + " ; " + lastClickY);

            if (!field.isCompleted()) {
                loop:
                for (int i = 0; i < cells.length; i++)
                    for (int j = 0; j < cells.length; j++) {

                        if (lastClickY >= i * CELL_SIZE + SPACING && lastClickY <= CELL_SIZE * (i + 1) - SPACING
                                && lastClickX >= j * CELL_SIZE + SPACING && lastClickX <= CELL_SIZE * (j + 1) - SPACING) {
                            System.err.println("Click on : " + i + " " + j);
                            if (field.check(i, j)) {
                                i = -1;
                                lastClickX = -1;
                                lastClickY = -1;
                                continue loop;
                            } else {
                                System.err.println(ScoreItem.timeFormatter.format(LocalTime.ofInstant(Instant.ofEpochMilli(gameTimeMillis), ZoneOffset.UTC)));
                                success = false;
                            }
                        }

                        g.setColor(switch (cells[i][j]) {
                            case UNKNOWN -> {
                                if (lastPositionY >= i * CELL_SIZE + SPACING && lastPositionY <= CELL_SIZE * (i + 1) - SPACING
                                        && lastPositionX >= j * CELL_SIZE + SPACING && lastPositionX <= CELL_SIZE * (j + 1) - SPACING) {
                                    yield Color.red;
                                } else yield ExternalCell.UNKNOWN.getImage();
                            }
                            default -> cells[i][j].getImage();
                        });

                        g.fillRect(j * CELL_SIZE + SPACING, i * CELL_SIZE + SPACING,
                                CELL_SIZE - 2 * SPACING, CELL_SIZE - 2 * SPACING);

                    }

                if (field.isCompleted()) {
                    gameTimer.stop();
                    onSuccess();
                }

                if (!success) {
                    gameTimer.stop();
                    onFail();
                }
            }
        }
    }

    public class CellClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.err.println("Clicked : " + e.getX() + ";" + e.getY());

            ((FieldPanel) fieldPanel).lastClickX = e.getX();
            ((FieldPanel) fieldPanel).lastClickY = e.getY();

            gamePanel.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class FieldMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // Nothing
        }

        @Override
        public void mouseMoved(MouseEvent e) {
//            System.out.println("Mouse at\n"
//                    + "x : " + e.getX()
//                    + "y : " + e.getY());

            ((FieldPanel) (fieldPanel)).lastPositionX = e.getX();
            ((FieldPanel) (fieldPanel)).lastPositionY = e.getY();

            gamePanel.repaint();
        }
    }

    private void startTimer(long startTimeMillis) {
        gameTimer = new Timer(0, e -> {
            LocalTime time = LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - startTimeMillis), ZoneOffset.UTC);
            timeLabel.setText(ScoreItem.timeFormatter.format(time));
            gameTimeMillis = System.currentTimeMillis() - startTimeMillis;
        });
        gameTimer.start();
    }

    private void onSuccess() {
        new SaveScoreDialog(LocalTime.ofInstant(Instant.ofEpochMilli(gameTimeMillis), ZoneOffset.UTC));
    }

    private void onFail() {
        dispose();
    }

}
