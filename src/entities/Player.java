package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 50;//speed = 4 animations per second ~ 120 fps
    private int playerAction =	IDLE;
    private boolean left, up, right, down;
    private boolean moving = false;
    private float playerSpeed = 2.0f;


    public Player(float x, float y) {
        super(x, y);
        loadAnimation();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();

    }
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)x, (int)y, 96, 96, null);
    }



    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmounts(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if(moving){
            playerAction = RUNNING;
        } else playerAction = IDLE;

        if(startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
        moving = false;

        if(left && !right){
            x -= playerSpeed;
            moving = true;
        } else if(right && !left){
            x += playerSpeed;
            moving = true;
        }

        if(up && !down){
            y -= playerSpeed;
            moving = true;
        } else if(down && !up){
            y += playerSpeed;
            moving = true;
        }

    }

    private void loadAnimation() {
        InputStream is = getClass().getResourceAsStream("/Player/maincharacter.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[5][4];
            for(int i = 0; i < animations.length; i+=1) {
                for(int j = 0; j < animations[i].length; j+=1) {
                    animations[i][j] = img.getSubimage(j * 46, i*48 , 48, 48);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                is.close(); //free up resoucres and avoid problems
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void resetDirBooleans(){
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
