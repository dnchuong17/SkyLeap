package entities;

import main.Game;
import utilz.LoadSave;
import utilz.Constants.PlayerConstants;
import static utilz.HelpMeMethod.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import static main.Game.GAME_HEIGHT;
import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{
    //player attributes
    private float playerSpeed = 2.0f;
    private boolean isMoving = false; //private boolean moving thay thanh isMoving cho no de hieu hon
    private float verticalVelocity = 0; //thay doi boi gravity & suc nhay cua character
    private float horizontalVelocity = 1.5f*playerSpeed;


    // jump attributes / gravity / fall speed
    private boolean isInAir = false;
    private boolean isJumping = false;
    private boolean isChargingJump = false;
    private long chargeStartTime;
    private final int maxChargeTime = 2000; // 2000 millisecond = 2 seconds
    private final float baseJumpStrength = -3.0f * Game.SCALE;    // act as a minimum jump strength = jumpSpeed
    private final float maxJumpStrength = -7.0f * Game.SCALE;
    private float gravity = 0.1f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private float airSpeed = 1.0f * Game.SCALE;

    //player actions and rendering
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 50;//speed = 4 animations per second ~ 120 fps
    private int playerAction =	IDLE;
    private boolean left, up, right, down, jump;
    private float xDrawOffset = 1* Game.SCALE;
    private float yDrawOffset = 2* Game.SCALE;
    // store load save = level data
    private int[][] levelData;

    public Player(float startX, float startY, int width , int height) throws IOException {
        super(startX, startY, width, height);
        loadAnimation();
        initHitBox(x, y, 40* Game.SCALE, 40* Game.SCALE);
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if(!IsEntityOnGround(hitBox, levelData)){
            isInAir = true;
        }
    }

    private void updateJumpState() {
        if(isInAir){return;}
        isInAir = true;
        airSpeed = baseJumpStrength;
    }
    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)(hitBox.x - xDrawOffset), (int)(hitBox.y - yDrawOffset), 96, 96, null);
        renderHitBox(g); // for debugging
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
        if(jump){
            updateJumpState();
        }
        if(!left && !right && !isInAir){
            return;
        }
        float xTempSpeed = 0;
        float yTempSpeed = 0;

        // Handle horizontal movement
        if (left) { xTempSpeed = -playerSpeed;}
        if (right) { xTempSpeed = playerSpeed;}

        //
        if (isInAir){
            if(canMoveHere(hitBox.x, hitBox.y +airSpeed, hitBox.width, hitBox.height, levelData)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xTempSpeed);
            }else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed);
                if(airSpeed > 0){
                    resetIsInAir();
                }else {
                    airSpeed = fallSpeedAfterCollision;
                    updateXPosition(xTempSpeed);
                }
            }
        }else {
            updateXPosition(xTempSpeed);   
        }
        isMoving = true;
    }

    private void resetIsInAir() {
        isInAir = false;
        airSpeed = 0;
    }

    private void updateXPosition(float xTempSpeed) {
        if (canMoveHere(hitBox.x + xTempSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xTempSpeed;
            isMoving = true;
        }else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xTempSpeed);
        }
    }

    private void loadAnimation() throws IOException {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_PATH);
        animations = new BufferedImage[5][4]; // 5 actions, 4 frames each
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 48, i * 48, 48, 48);
            }
        }
    }

    public void resetDirBooleans(){
        left = false;
        up = false; // redundant but need for look up
        right = false;
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
    public boolean isJumping() {
        return jump;
    }
    public void setJumping(boolean jumping) {
        this.jump = jumping;
    }
}
/* Start charging the jump
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
    }*/