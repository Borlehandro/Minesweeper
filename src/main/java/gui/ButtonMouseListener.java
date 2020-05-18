package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMouseListener extends MouseAdapter {

    private final JButton button;

    public ButtonMouseListener(JButton button) {
        this.button = button;
        button.setBackground(Color.darkGray);
        button.setForeground(Color.lightGray);
    }

    public void mouseEntered(MouseEvent evt) {
        button.setBackground(Color.lightGray);
        button.setForeground(Color.white);
    }

    public void mouseExited(MouseEvent evt) {
        button.setBackground(Color.darkGray);
        button.setForeground(Color.lightGray);
    }
}
