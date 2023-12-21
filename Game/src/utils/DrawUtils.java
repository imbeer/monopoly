package utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DrawUtils {
    public static final Font NAME = new Font("Arial", Font.BOLD, 18);
    public static final Font PRICE = new Font("Arial", Font.BOLD, 18);

    public static void drawCenteredText(Font font, String text, Graphics2D g, Rectangle2D bounds) {
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (int) (bounds.getX() + (bounds.getWidth() - metrics.stringWidth(text)) / 2);
        int y = (int) (bounds.getY() + ((bounds.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
        g.drawString(text, x, y);
    }

    public static void drawText(Font font, String text, Graphics2D g, Rectangle2D bounds) {
        g.setFont(font);
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();
        g.drawString(text, x, y);
    }

    public static Rectangle2D getVerticalPartOfBounds(Rectangle2D orig, double firstLine, double secondLine) {
        firstLine = orig.getY() + orig.getHeight() * firstLine;
        secondLine = orig.getY() + orig.getHeight() * secondLine;
        return new Rectangle2D.Double(
                orig.getX(),
                firstLine,
                orig.getWidth(),
                secondLine - firstLine
        );
    }

    public static Rectangle2D getHorizontalPartOfBounds(Rectangle2D orig, double firstLine, double secondLine) {
        firstLine = orig.getX() + orig.getWidth() * firstLine;
        secondLine = orig.getX() + orig.getWidth() * secondLine;
        return new Rectangle2D.Double(
                firstLine,
                orig.getY(),
                secondLine - firstLine,
                orig.getHeight()
        );
    }

    public static double getNewBoundCentered(double fullLength, double shortLength) {
        return (fullLength - shortLength) / 2;
    }

    public static void colorImage(BufferedImage bImage, Color color) {
        for (int x = 0; x < bImage.getWidth(); x++) {
            for (int y = 0; y < bImage.getHeight(); y++) {
                int rgba = bImage.getRGB(x, y);
                Color col = new Color(rgba, true);
                if (col.getAlpha() >= 0.5f) {
                    bImage.setRGB(x, y, color.getRGB());
                }
            }
        }
    }



    public static BufferedImage scale(BufferedImage src, int w, int h)
    {
        BufferedImage img =
                new BufferedImage(w, h, src.getType());
        Graphics2D g2d = img.createGraphics();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(src, 0, 0, w, h, null);
        } finally {
            g2d.dispose();
        }
        return img;
    }
}

