package UI;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.UI.UrmButtons.*;


public class UrmButtons extends PauseButtons{
    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;


    public UrmButtons(int x, int y, int width, int height, int rowIndex) throws IOException {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() throws IOException {
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i< 3; i++){

            imgs[i] = temp.getSubimage(i * URM_SIZE_DEFAULT, rowIndex * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
        }
    }

    public void update(){
        index = 0;
        if (mouseOver){
            index = 1;
        }
        if (mousePressed)
            index =2;

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;

    }

    public void draw(Graphics g){
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null );

    }
}
