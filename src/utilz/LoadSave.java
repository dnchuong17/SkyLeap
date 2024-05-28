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
    public static final String MENU_BUTTONS = "/GameUI/button_atlas.png";
    public static final String MENU_BACKGROUND = "/GameUI/menu_background.png";
    public static final String HELP_IMAGE = "/GameUI/1.png";
    public static final String PAUSE_BACKGROUND = "/GameUI/pause_menu.png";
    public static final String SOUND_BUTTONS = "/GameUI/sound_button.png";



    public static BufferedImage getSpriteAtlas(String filePath) throws IOException {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream(filePath);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] getLevelData() throws IOException {
        int[][] levelData = new int[Game.TILE_IN_HEIGHT][Game.TILE_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LEVEL_TEST);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                levelData[y][x] = color.getRed();
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                    levelData[y][x] = value;
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