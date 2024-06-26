package utilz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_PATH = "/Player/maincharacter1.png";                                         //error
    public static final String LEVEL_ATLAS = "/Tileset/level2_sprites.png";
    public static final String LEVEL_TEST = "/backgrounds/level_2_data_long.png";// test level
    public static final String MENU_BUTTONS = "/GameUI/button_atlas.png";
    public static final String MENU_BACKGROUND = "/GameUI/menu_background.png";
    public static final String HELP_IMAGE = "/GameUI/1.png";
    public static final String PAUSE_BACKGROUND = "/GameUI/pause_menu.png";
    public static final String SOUND_BUTTONS = "/GameUI/sound_button.png";
    public static final String URM_BUTTONS = "/GameUI/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "/GameUI/volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "/backgrounds/BackgroundMenu.png";
    public static final String PLAYING_BACKGROUND_IMG = "/backgrounds/backgroundForReal.png";
    public static final String PLAYING_BACKGROUND_IMG_3 = "/backgrounds/backgroundForReal_3.png";
    public static final String BANNER_IMG = "/backgrounds/banner.png";
    public static final String COMPLETED = "/GameUI/completed.png";


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

        BufferedImage img = getSpriteAtlas(LEVEL_TEST);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                levelData[y][x] = color.getRed();
                int value = color.getRed();
                if(value >= 104)
                    value = 0;
                levelData[y][x] = value;
            }
        }
        return levelData;
    }
}
