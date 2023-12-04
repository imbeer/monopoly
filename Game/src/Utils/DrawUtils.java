package Utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class DrawUtils {

    public static final Font NAME = new Font("Arial", Font.BOLD, 18);
    public static final Font PRICE = new Font("Arial", Font.BOLD, 18);

    public static void drawText(Font font, String text, Graphics2D g, Rectangle2D bounds) {
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (int) (bounds.getX() + (bounds.getWidth() - metrics.stringWidth(text)) / 2);
        int y = (int) (bounds.getY() + ((bounds.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
        g.drawString(text, (int) x, (int) y);
    }

    public static Rectangle2D getPartOfBounds(Rectangle2D orig, double firstLine, double secondLine) {
        firstLine = orig.getY() + orig.getHeight() * firstLine;
        secondLine = orig.getY() + orig.getHeight() * secondLine;
        return new Rectangle2D.Double(
                orig.getX(),
                firstLine,
                orig.getWidth(),
                secondLine - firstLine
        );
    }

    public static double getNewBoundCentered(double fullLength, double shortLength) {
        return (fullLength - shortLength) / 2;
    }
}

