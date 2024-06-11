package Levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.Game.TILE_SIZE;

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
    public void importOusideSprites() throws IOException {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }


    public void update(){

    }

    public void draw(Graphics g) {
        for (int j = 0; j < Game.TILE_IN_HEIGHT; j++)
            for (int i = 0; i < Game.TILE_IN_WIDTH; i++) {
                int index = levelTest.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILE_SIZE * i, TILE_SIZE * j, TILE_SIZE, TILE_SIZE, null);
            }
    }
    public Level getCurentLevel() {
        return levelTest;
    }

    public void reset() throws IOException {
        levelTest = new Level(LoadSave.getLevelData());
    }
}
