package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    //player attributes
    private float playerSpeed = 2.0f;
    private boolean isMoving = false; //private boolean moving thay thanh isMoving cho no de hieu hon
    private float verticalVelocity = 0; //thay doi boi gravity & suc nhay cua character
    private float horizontalVelocity = 1.5f*playerSpeed;
    private float gravity = 0.3f;
    private int groundLevel = 1050 - 300;

    // Charging Jump attributes
    private boolean isJumping = false;
    private boolean isChargingJump;
    private long chargeStartTime;
    private final int maxChargeTime = 2000; // 2000 millisecond = 2 seconds
    private final int baseJumpStrength = -10;
    private final int maxJumpStrength = -20;
    //private float jumpSpeed = 10.0f; // Speed at which the player jumps                      //old jump variable for old jump method
    //private double jumpAngle = 45.0; // Angle in degrees

    //player actions and rendering
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 50;//speed = 4 animations per second ~ 120 fps
    private int playerAction =	IDLE;
    private boolean left, up, right, down;


    public Player(float startX, float startY) {
        super(startX, startY);
        loadAnimation();
    }

    /*public void jump() {                       //old jump method
        if (!isJumping) {
            isJumping = true;
            double radians = Math.toRadians(jumpAngle);
            verticalVelocity = - (float) (jumpSpeed * Math.sin(radians));
            horizontalVelocity = (float) (jumpSpeed * Math.cos(radians)) * (left ? -1 : 1);
        }
    }*/
    // Start charging the jump
    public void startCharging() {
        if (!isJumping && !isChargingJump) {
            isChargingJump = true;
            chargeStartTime = System.currentTimeMillis();
        }
    }
    // Execute the jump
    public void executeJump() {
        if (isChargingJump) {
            isChargingJump = false;
            long chargeTime = Math.min(System.currentTimeMillis() - chargeStartTime, maxChargeTime);
            System.out.println("Charge time: " + chargeTime);
            double chargeRatio = (double) chargeTime / maxChargeTime;
            System.out.println("Charge ratio: " + chargeRatio);
            verticalVelocity = baseJumpStrength + (int)(chargeRatio * (maxJumpStrength - baseJumpStrength));
            System.out.println("Jump strength: " + verticalVelocity);
            isJumping = true;
        }
    }
    private void updateJumpState() {
        if (isJumping) {
            y += verticalVelocity;
            if(isLeft()){                   //directional jump
                x -= horizontalVelocity;
            } else if(isRight()){
                x += horizontalVelocity;
            }
            verticalVelocity += gravity;
            if (y >= groundLevel) {
                y = groundLevel;
                isJumping = false;
            }
        }
    }
    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
        updateJumpState();
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

        if(isMoving){
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

    private void updatePosition() {   //update position of player only on x axis
        isMoving = false;

        /*if(left && !right){                       //old movement control
            x -= playerSpeed;
            isMoving = true;
        } else if(right && !left){
            x += playerSpeed;
            isMoving = true;
        }

        if(up && !down){
            y -= playerSpeed;
            isMoving = true;
        } else if(down && !up){
            y += playerSpeed;
            isMoving = true;
        }

        if (isJumping) {
            x += horizontalVelocity;
            y += verticalVelocity;
            verticalVelocity += gravity;
            if (y >= groundLevel) {
                y = groundLevel;
                isJumping = false;
                verticalVelocity = 0;
            }
        }*/
        if (!isJumping) { // Normal movement control
            if (left) {
                x -= playerSpeed;
                isMoving = true;
            }
            if (right) {
                x += playerSpeed;
                isMoving = true;
            }
        }
    }

    private void loadAnimation() {
        /*InputStream is = getClass().getResourceAsStream("/Player/maincharacter.png");  // old way of loading image
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
        }*/
        // new way of loading the player sprite sheet
        InputStream is = getClass().getResourceAsStream("/Player/maincharacter.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[5][4]; // 5 actions, 4 frames each
            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j * 46, i * 48, 48, 48);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetDirBooleans(){
        left = false;
        up = false; // redundant but need for look up
        right = false;
        down = false; // redundant
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
