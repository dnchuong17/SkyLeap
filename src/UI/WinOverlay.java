package UI;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinOverlay {
    public BufferedImage backGroundImg;
    private  int bgX, bgY,bgW,bgH;
    public WinOverlay() throws IOException {
        loadSprites();

    }

    public void loadSprites() throws IOException {
        bgW = 124;
        bgH = 41;
        bgX = Game.GAME_WIDTH / 2 - 50;
        bgY = Game.GAME_HEIGHT / 2;
        backGroundImg = LoadSave.getSpriteAtlas(LoadSave.COMPLETED);
    }
    public void update(){

    }
    public  void draw(Graphics g){
        g.drawImage(backGroundImg,bgX,bgY,null);
    }
}
