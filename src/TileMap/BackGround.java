package TileMap;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
public class BackGround {
    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public BackGround(String s, double ms) {
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(s));
            moveScale = ms;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void update(){
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
        if (x < 0) {
            g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
        }
    }
}
