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

    //New Map
    public void importOusideSprites() throws IOException {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[104];
        for (int j = 0; j < 13 ; j++) {
            for (int i = 0; i < 8; i++) {
                int index = j * 8 + i;
                levelSprite[index] = img.getSubimage(i * TILE_DEFAULT_SIZE, j * TILE_DEFAULT_SIZE, TILE_DEFAULT_SIZE, TILE_DEFAULT_SIZE);
            }
        }
    }


    public void update(){
    }

    public void draw(Graphics g, int lvlOffSet) {
        for (int j = 0  ; j < levelTest.getLevelData().length; j++) {
            for (int i = 0; i < TILE_IN_WIDTH; i++) {
                int index = levelTest.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILE_SIZE * i, TILE_SIZE * j - lvlOffSet, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }


    public Level getCurentLevel() {
        return levelTest;
    }

    public void reset() throws IOException {
        levelTest = new Level(LoadSave.getLevelData());
    }
}
