package levels;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelTwo;


    public LevelManager(Game game) {
        this.game = game;
//        levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importLevel2Sprites();
        levelTwo = new Level(LoadSave.GetLevelData());
    }

    private void importLevel2Sprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[104];
        for(int j = 0; j < 13; j++) {
            for(int i = 0; i < 8; i++){
                int index = j * 8 + i;
                levelSprite[index] = img.getSubimage(i * 16, j *16 , 16, 16);
            }
        }
    }

//    private void importLevel2Sprites() {
//        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
//        levelSprite = new BufferedImage[48];
//        for(int j = 0; j < 4; j++) {
//            for(int i = 0; i < 12; i++){
//                int index = j * 12 + i;
//                levelSprite[index] = img.getSubimage(i * 32, j *32 , 32, 32);
//            }
//        }
//    }

    public void draw(Graphics g) {
        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++){
            for(int i = 0; i < Game.TILES_IN_WIDTH; i++){
                int index = levelTwo.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i , TILES_SIZE * j, TILES_SIZE, TILES_SIZE,null);
            }
        }
    }
    public void update() {}

    public Level getCurentLevel() {
        return levelTwo;
    }


}
