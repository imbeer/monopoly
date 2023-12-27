package org.example.view;

import org.example.entity.players.Player;
import org.example.entity.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MessageBoxProxy {

    public static void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean getAnswer(String message, String title) {
        int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    public static String getStringAnswer(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
    }

    public static boolean drawTileInformation(Tile tile, Player activePlayer) {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(244, 255, 235));
                tile.drawInBounds((Graphics2D) g, new Rectangle2D.Double(0, 0, 200, 200));
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
        };
       if (tile.canBeUpgraded(activePlayer)) {
           int result = JOptionPane.showConfirmDialog(null, panel, "Do you want to upgrade?", JOptionPane.YES_NO_OPTION);
           return result == JOptionPane.YES_OPTION;
       } else {
           JOptionPane.showMessageDialog(null, panel, tile.name, JOptionPane.PLAIN_MESSAGE);
           return false;
       }
    }
}
