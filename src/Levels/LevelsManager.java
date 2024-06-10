package Levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.Game.*;

public class LevelsManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage[] levelTestSprite;
    private Level levelTest;

    public LevelsManager(Game game) throws IOException {
        this.game = game;
        importOusideSprites();
        levelTest = new Level(LoadSave.getLevelData());
    }

    //Old Map
//    public void importOusideSprites() throws IOException {
//        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
//        levelSprite = new BufferedImage[48];
//        for (int j = 0; j < 4; j++) {
//            for (int i = 0; i < 12; i++) {
//                int index = j * 12 + i;
//                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
//            }
//        }
//    }

    //New Map
    public void importOusideSprites() throws IOException {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[104];
        for (int j = 0; j < 13 ; j++) {
            for (int i = 0; i < 8; i++) {
                int index = j * 8 + i;
                levelSprite[index] = img.getSubimage(i * 16, j * 16, 16, 16);
            }
        }
    }


    public void update(){

    }

    //Bigger Lvl X
//    public void draw(Graphics g, int lvlOffSet) {
//        for (int j = 0; j < Game.TILE_IN_HEIGHT; j++)
//            for (int i = 0; i < levelTest.getLevelData()[0].length; i++) {
//                int index = levelTest.getSpriteIndex(i, j);
//                g.drawImage(levelSprite[index], TILE_SIZE * i - lvlOffSet, TILE_SIZE * j, TILE_SIZE, TILE_SIZE, null);
//            }
//    }


//    public void draw(Graphics g, int lvlOffSet) {
//        for (int j = 0 ; j < levelTest.getLevelData().length ; j++) {
//            for (int i = 0; i < TILE_IN_WIDTH; i++) {
//                int index = levelTest.getSpriteIndex(i, j);
//                g.drawImage(levelSprite[index], TILE_SIZE * i, TILE_SIZE * j - lvlOffSet, TILE_SIZE, TILE_SIZE, null);
//            }
//        }
//    }

    public void draw(Graphics g, int lvlOffSet) {
        for (int j = 0  ; j < levelTest.getLevelData().length; j++) {
            for (int i = 0; i < TILE_IN_WIDTH; i++) {
                int index = levelTest.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILE_SIZE * i, TILE_SIZE * j - (levelTest.getLevelData().length*TILE_SIZE) + GAME_HEIGHT - lvlOffSet , TILE_SIZE, TILE_SIZE, null);
            }
        }
    }


    public Level getCurentLevel() {
        return levelTest;
    }
}
