package UI;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButtons{
    private BufferedImage[] imgs;
    private BufferedImage slider;
    private boolean mouseOver, mousePressed;
    private int index = 0;

    private int buttonX, minX, maxX;
    private float floatValue = 0f;
    public VolumeButton(int x, int y, int width, int height) throws IOException {
        super(x + width/2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH/2;
        buttonX = x + width/2;
        this.x = x;
        this.width = width;
        minX = x + VOLUME_WIDTH/2;
        maxX = x + width -VOLUME_WIDTH/2;
        loadImgs();
    }

    private void loadImgs() throws IOException {
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0  ; i < imgs.length; i++ ){
            imgs[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }

        slider = temp.getSubimage(3* VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
    }

    public void update(){
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
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
        g.drawImage(slider, x, y, width, height , null);
        g.drawImage(imgs[index], buttonX - VOLUME_WIDTH/2, y, VOLUME_WIDTH -20, height, null);

    }

    public void changeX(int x){
        if (x < minX)
            buttonX = minX;
        else if (x > maxX)
            buttonX = maxX;
        else buttonX = x;
        updatefloatValue();
        bounds.x = buttonX - VOLUME_WIDTH/2;
    }

    private void updatefloatValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        floatValue = value/range;
    }

    public float getFloatValue(){
        return floatValue;
    }

}
