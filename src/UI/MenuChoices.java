package UI;

import gameStates.Gamestate;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.UI.Buttons.*;

public class MenuChoices {
    private int xPos;
    private int yPos;
    private int rowIndex;
    private int index;
    private int xOffsetCenter = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver;
    private boolean mousePressed;
    private Rectangle bounds;

    private boolean keyPressed, keyOver;

    public MenuChoices(int xPos, int yPos, int rowIndex, Gamestate state) throws IOException {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        this.loadImgs();
        this.initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(this.xPos - this.xOffsetCenter, this.yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImgs() throws IOException {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);

        for (int i = 0; i < 3; i++) {
           imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void update(int choice) {
        if (choice == rowIndex) {
            if (keyPressed) {
                index = 2;
            }
            else
                index = 1;
        }
        else index = 0;
    }

    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public boolean isMouseOver() {
        return this.mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return this.mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void applyGamestate() {
        Gamestate.state = this.state;
    }

    public boolean isKeyPressed() {
        return keyPressed;
    }

    public void setKeyPressed(boolean keyPressed) {
        this.keyPressed = keyPressed;
    }

    public boolean isKeyOver() {
        return keyOver;
    }

    public void setKeyOver(boolean keyOver) {
        this.keyOver = keyOver;
    }

    public void resetBools() {
        this.mouseOver = false;
        this.mousePressed = false;

        this.keyOver = false;
        this.keyPressed = false;
    }

    public Gamestate getState(){
        return state;
    }


}
