/*
 * Created by JFormDesigner on Sun May 10 18:49:46 NOVT 2020
 */

package gui;

import model.Field;
import score.ScoreItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import javax.swing.*;

/**
 * @author Alex Borzikov
 */
public class GameFrame extends JFrame {

    private final long start;
    private final Field field;

    private int lastPositionX = 0;
    private int lastPositionY = 0;

    public GameFrame(int size, int mines) {
        initComponents();
        this.setVisible(true);
        field = new Field(size, mines);
        start = System.currentTimeMillis();
        startTimer();
        Box box = new Box();
        this.setContentPane(box);
        this.addMouseMotionListener(new FieldMotionListener());
    }

    private class Box extends JPanel {

        private static final int SPACING = 5;
        private static final int CELL_SIZE = 20;

        @Override
        protected void paintComponent(Graphics g) {
            char[][] cells = field.getField();
            System.err.println(cells.length);
            for (int i = 0; i < cells.length; i++)
                for (int j = 0; j < cells.length; j++) {
                    if(lastPositionX >= i * CELL_SIZE + SPACING)
                        g.setColor(Color.red);
                    else
                        g.setColor(Color.darkGray);
                    g.fillRect(i * CELL_SIZE + SPACING, j * CELL_SIZE + SPACING,
                            CELL_SIZE - 2 * SPACING, CELL_SIZE - 2 * SPACING);
                }
        }
    }

    public class FieldMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // Nothing
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            System.out.println("Mouse at\n"
            + "x : " + e.getX()
            + "y : " + e.getY());

            lastPositionX = e.getX();
            lastPositionY = e.getY();

            getContentPane().repaint();
        }
    }

    private void startTimer() {
        Timer timer = new Timer(0, e -> {
            LocalTime time = LocalTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() - start), ZoneOffset.UTC);
            timeLabel.setText(ScoreItem.timeFormatter.format(time));
        });
        timer.start();
    }

    @Override
    public void paintComponents(Graphics g) {
        timeLabel.setText(String.valueOf(System.currentTimeMillis()));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Alex Borzikov
        timeLabel = new JLabel();
        fieldPanel = new JPanel();

        //======== this ========
        setTitle("Minesweeper");
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- timeLabel ----
        timeLabel.setText("Time :");
        contentPane.add(timeLabel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //======== fieldPanel ========
        {
            fieldPanel.setBackground(new Color(51, 51, 51));
            fieldPanel.setMinimumSize(new Dimension(400, 400));
            fieldPanel.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder ( 0
            , 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
            , new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) ,
            fieldPanel. getBorder () ) ); fieldPanel. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
            ) { if( "\u0062order" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            fieldPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)fieldPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
            ((GridBagLayout)fieldPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
            ((GridBagLayout)fieldPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)fieldPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        }
        contentPane.add(fieldPanel, new GridBagConstraints(3, 3, 3, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Alex Borzikov
    private JLabel timeLabel;
    private JPanel fieldPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
