package view;

import utils.DrawUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class NextTurnButton {
    private Rectangle2D bounds;

    private final ImageIcon BUTTON_ICON = new ImageIcon("src/Assets/monopoly_next_turn_button.png");
    public NextTurnButton() {
    }

    public Rectangle2D getBounds() {
        return bounds;
    }

    public void draw(Graphics2D g2d, Rectangle2D bounds) {
        Image image = BUTTON_ICON.getImage();
        this.bounds = bounds;
        BufferedImage bufferedImage = new BufferedImage(
                BUTTON_ICON.getIconWidth(),
                BUTTON_ICON.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.drawImage(image, 0, 0, null);
        imageGraphics.dispose();
        bufferedImage = DrawUtils.scale(bufferedImage, (int) bounds.getWidth(), (int) bounds.getHeight());
        g2d.drawImage(bufferedImage, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
    }

}
