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

    public void render(Graphics g){
        for (int y = 0; y < Game.TILE_IN_HEIGHT; y++) {
            for (int x = 0; x < Game.TILE_IN_WIDTH; x++) {
                int index = levelTest.getSpriteIndex(x, y);
                g.drawImage(levelSprite[index], x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }
    public void update(){

    }

    public void draw(Graphics g){

    }

    public Level getCurentLevel() {
        return levelTest;
    }
}
