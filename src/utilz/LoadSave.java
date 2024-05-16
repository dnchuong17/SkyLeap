package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_PATH = "/Player/maincharacter.png";                                         //error
    public static final String LEVEL_ATLAS = "/backgrounds/outside_sprites.png";
    public static final String LEVEL_TEST = "/backgrounds/level_one_data.png"; // test level

    public static BufferedImage getSpriteAtlas(String filePath) throws IOException {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream(filePath);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] getLevelData(String filePath) throws IOException {
        BufferedImage img = getSpriteAtlas(filePath);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 0 && green == 0 && blue == 0) {
                    levelData[y][x] = 0;
                } else {
                    levelData[y][x] = 1;
                }
            }
        }
        return levelData;
    }
    public static int[][] getLevelData() throws IOException {
        int[][] levelData = new int[Game.TILE_IN_HEIGHT][Game.TILE_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LEVEL_TEST);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                levelData[y][x] = color.getRed();
                int value = color.getRed();
                if(value >= 48){
                    value = 0;
                } else {
                    levelData[y][x] = value;
                }
            }
        }
        return levelData;
    }
}
/*BufferedImage img = getSpriteAtlas(LEVEL_TEST);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 0 && green == 0 && blue == 0) {
                    levelData[y][x] = 0;
                } else {
                    levelData[y][x] = 1;
                }
            }
        }*/