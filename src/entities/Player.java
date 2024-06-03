package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMeMethod.*;

public class Player extends Entity{
    //player attributes
    private float playerSpeed = 1.5f;
    private boolean isMoving = false; //private boolean moving thay thanh isMoving cho no de hieu hon
    private float horizontalVelocity = 2.0f*playerSpeed;


    // jump attributes / gravity / fall speed
    private boolean isInAir = false;
    private final float BASE_JUMP_FORCE = -5.0f * Game.SCALE;    // act as a minimum jump strength = jumpSpeed
    private final float MAX_JUMP_FORCE = -7.0f * Game.SCALE;
    private float gravity = 0.1f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private float airSpeed = 0.5f * Game.SCALE;
    private boolean isJumping = false;
    private static final float MAX_CHARGE_TIME = 2000; // max charge time in milliseconds

    //player actions and rendering
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 50;//speed = 4 animations per second ~ 120 fps
    private int playerAction =	IDLE;
    private boolean left, up, right, down, jump;
    private float xDrawOffset = 2* Game.SCALE;
    private float yDrawOffset = 2* Game.SCALE;
    // store load save = level data
    private int[][] levelData;


    public Player(float startX, float startY, int width , int height) throws IOException {
        super(startX, startY, width, height);
        loadAnimation();
        initHitBox(x, y, (int)(28* Game.SCALE),(int)(28* Game.SCALE));        // something wrong here with the hitbox when 32*Game.SCALE
                                                                            // work fine with 28*Game.SCALE
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if(!isEntityOnGround(hitBox, levelData)){
            isInAir = true;
        }
    }


    private void updateJumpState() {
        if(isInAir){return;}
        isInAir = true;
        airSpeed = BASE_JUMP_FORCE;
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    private void updateAirbornePosition() {
        // Similar to existing logic but consider horizontal velocity due to jump direction
        if (canMoveHere(hitBox.x + horizontalVelocity, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += horizontalVelocity;
            hitBox.y += airSpeed;
            airSpeed += gravity; // Apply gravity
        } else {
            // Handle collision with the ground or walls
            hitBox.y = getEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed);
            airSpeed = 0;
            horizontalVelocity = 0;
            isInAir = false;
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)(hitBox.x-xDrawOffset), (int)(hitBox.y-yDrawOffset), 32, 32, null);
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

        if (!isInAir) {
            if(!isEntityOnGround(hitBox, levelData)){
                isInAir = true;
            }
        }
        if (isInAir){
            if(canMoveHere(hitBox.x, hitBox.y +airSpeed, hitBox.width, hitBox.height, levelData)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPosition(xTempSpeed);
            }else {
                hitBox.y = getEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed);
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
            hitBox.x = getEntityXPosNextToWall(hitBox, xTempSpeed);
        }
    }

    private void loadAnimation() throws IOException {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_PATH);
        animations = new BufferedImage[5][4]; // 5 actions, 4 frames each
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 32, i * 32, 32, 32);
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
