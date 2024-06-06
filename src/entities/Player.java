package entities;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMeMethod.*;

public class Player extends Entity {
    // Player attributes
    private float playerSpeed = 1.5f * Game.SCALE;
    private boolean isMoving = false;
    private float horizontalVelocity = 3.0f * playerSpeed;
    private float verticalVelocity = 3.0f * playerSpeed;

    // Jump attributes / gravity / fall speed
    private float airSpeed = 1.0f * Game.SCALE;
    private boolean isInAir = false;
    private boolean isJumping = false;
    private boolean hitWall = false;
    private float jumpTime = 0.0f;
    private final float BASE_JUMP_FORCE = -4.0f * Game.SCALE;
    private final float MAX_JUMP_FORCE = -7.0f * Game.SCALE;
    private final float JUMP_MULTIPLIER = 0.1f * Game.SCALE;
    private final long JUMP_CHARGE_START_TIME = 0;
    private final float GRAVITY = 0.1f * Game.SCALE;
    private final float MAX_FALL_SPEED = 10.0f * Game.SCALE;
    private final float SIGNIFICANT_FALL_SPEED = 8.0f;
    private float fallSpeedAfterCollision = 0.0f * Game.SCALE;
    private static final float MAX_CHARGE_TIME = 2000; // max charge time in milliseconds

    // Player actions and rendering
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 50; // speed = 4 animations per second ~ 120 fps
    private int playerAction = IDLE;
    private boolean left, up, right, down, jump;
    private float xDrawOffset = 2 * Game.SCALE;
    private float yDrawOffset = 2 * Game.SCALE;
    // Store load save = level data
    private int[][] levelData;

    public Player(float startX, float startY, int width, int height) throws IOException {
        super(startX, startY, width, height);
        loadAnimation();
        initHitBox(x, y, (int) (28 * Game.SCALE), (int) (28 * Game.SCALE)); // something wrong here with the hitbox when 32*Game.SCALE
        // work fine with 28*Game.SCALE
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!isEntityOnGround(hitBox, levelData)) {
            isInAir = true;
        }
    }
    private void applyGravity() {
        if (isInAir) {
            if (airSpeed < MAX_FALL_SPEED) {
                airSpeed += GRAVITY ;
            }
        }
    }
    private void updateJumpState() {
        if (isInAir) {
            return;
        }
        isInAir = true;
        airSpeed = BASE_JUMP_FORCE;
        System.out.println("Jump started: airSpeed=" + airSpeed);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    private void updateAirbornePosition() {
        if (canMoveHere(hitBox.x + horizontalVelocity, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += horizontalVelocity;
            hitBox.y += airSpeed;
            applyGravity();
            if (airSpeed > MAX_FALL_SPEED) {
                airSpeed = MAX_FALL_SPEED;
            }
            System.out.println("Airborne: x=" + hitBox.x + ", y=" + hitBox.y + ", airSpeed=" + airSpeed);
        } else {
            hitBox.y = getEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed);
            airSpeed = 0;
            horizontalVelocity = 0;
            isInAir = false;
            System.out.println("Collision detected: y=" + hitBox.y);
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y - yDrawOffset), (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), null);
        renderHitBox(g); // for debugging
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmounts(playerAction)) {
                animationIndex = 0;
            }
            System.out.println("Animation tick: animationIndex=" + animationIndex);
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (isMoving) {
            playerAction = RUNNING;
        } else playerAction = IDLE;

        if (startAnimation != playerAction) {
            resetAnimationTick();
            System.out.println("Animation changed: playerAction=" + playerAction);
        }
    }

    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
        isMoving = false;
        if (jump) {
            updateJumpState();
        }
        if (!left && !right && !isInAir) {
            return;
        }
        float xTempSpeed = 0;

        // Handle horizontal movement
        if (left) {
            xTempSpeed = -playerSpeed;
            System.out.println("Moving left: xTempSpeed=" + xTempSpeed);
        }
        if (right) {
            xTempSpeed = playerSpeed;
            System.out.println("Moving right: xTempSpeed=" + xTempSpeed);
        }

        if (!isInAir) {
            if (!isEntityOnGround(hitBox, levelData)) {
                isInAir = true;
                System.out.println("Became airborne");
            }
        }
        if (isInAir) {
            if (canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                applyGravity();
                updateXPosition(xTempSpeed);
            } else {
                hitBox.y = getEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed);
                xTempSpeed = - horizontalVelocity;
                if (airSpeed > 0) {
                    resetIsInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                    updateXPosition(xTempSpeed);
                }
                System.out.println("Vertical collision: y=" + hitBox.y);
            }
        } else {
            updateXPosition(xTempSpeed);
        }
        isMoving = true;
        System.out.println("Updated position: x=" + hitBox.x + ", y=" + hitBox.y + ", isMoving=" + isMoving);
    }

    private void resetIsInAir() {
        isInAir = false;
        airSpeed = 0.1f; // Slight positive value for bounce effect
        System.out.println("Landed: airSpeed=" + airSpeed);
    }

    private void updateXPosition(float xTempSpeed) {
        if (canMoveHere(hitBox.x + xTempSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xTempSpeed;
            isMoving = true;
        } else {
            hitBox.x = getEntityXPosNextToWall(hitBox, xTempSpeed);
            System.out.println("Horizontal collision: x=" + hitBox.x);
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
        System.out.println("Animations loaded");
    }

    public void resetDirBooleans() {
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

    // Getters and setters for testing purposes
    public float getYVelocity() {
        return airSpeed;
    }

    public float getXVelocity() {
        return horizontalVelocity;
    }

    public boolean getIsJumping() {
        return isJumping;
    }

    public float getJumpTime() {
        return jumpTime;
    }

    public void setYVelocity(float airSpeed) {
        this.airSpeed = airSpeed;
    }

    public void setXVelocity(float horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity;
    }
}
