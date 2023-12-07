package View;

import Entity.Tiles.Tile;
import Utils.DrawUtils;

import javax.swing.*;
import java.awt.*;

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

    public static void drawTileInformation(Tile tile) {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(new Color(244, 255, 235));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(new Color(0, 0, 0));
                DrawUtils.drawText(DrawUtils.NAME, tile.NAME, (Graphics2D) g, this.getBounds());
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
        };
       JOptionPane.showMessageDialog(null, panel, tile.NAME, JOptionPane.PLAIN_MESSAGE);
    }
}
