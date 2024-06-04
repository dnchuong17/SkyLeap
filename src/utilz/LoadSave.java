package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_ATLAS = "Player/maincharacter1.png";
    public static final String LEVEL_ATLAS = "backgrounds/level2_sprites.png";
//    public static final String LEVEL_ATLAS = "backgrounds/outside_sprites.png";
    public static final String LEVEL_TWO_DATA = "backgrounds/level_2_data(1).png";
//    public static final String LEVEL_TWO_DATA = "backgrounds/level_one_data.png";

    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
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

    public static int[][] GetLevelData(){
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_TWO_DATA);

        for(int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                lvlData[j][i] = color.getRed();
                if(value >= 104){
                    value = 0;
                    lvlData[j][i] = value;
                }
            }
        }
        return lvlData;
    }

//    public static int[][] GetLevelData() {
//        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
//        BufferedImage img = GetSpriteAtlas(LEVEL_TWO_DATA);
//
//        for (int j = 0; j < img.getHeight(); j++){
//            for (int i = 0; i < img.getWidth(); i++) {
//                Color color = new Color(img.getRGB(i, j));
//                int value = color.getRed();
//                lvlData[j][i] = color.getRed();
//                if (value >= 48) {
//                    value = 0;
//                    lvlData[j][i] = value;
//                }
//            }
//        }
//            return lvlData;
//    }

}
