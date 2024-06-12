package entities;

import main.Game;
import utilz.LoadSave;
import utilz.HelpMeMethod;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMeMethod.*;
public class Player extends Entity {
    // Player attributes
    private float playerSpeed = 0.5f * Game.SCALE; // The speed at which the player moves
    private boolean isMoving = false; // Whether the player is currently moving
    private float horizontalVelocity = 0f; // The horizontal speed of the player
    private float verticalVelocity = 0f; // The vertical speed of the player

    // Jump attributes / gravity / fall speed
    private float airSpeed = 1.0f * Game.SCALE; // The speed at which the player falls
    private boolean isInAir = false; // Whether the player is currently in the air
    private boolean isJumping = false; // Whether the player is currently jumping
    private boolean hitWall = false; // Whether the player has hit a wall
    private float jumpTime = 0.0f; // The duration of the player's jump
    private final float BASE_JUMP_FORCE = -2.0f * Game.SCALE; // The base force applied for a jump
    private final float MAX_JUMP_CHARGE_FORCE = -5.0f * Game.SCALE; // The maximum force applied for a charged jump
    private final float JUMP_MULTIPLIER = 0.2f * Game.SCALE; // The multiplier for jump force
    private final long JUMP_CHARGE_START_TIME = 0; // The start time for jump charge
    private final float GRAVITY = 0.1f * Game.SCALE; // The gravity applied to the player
    private final float MAX_FALL_SPEED = 10.0f * Game.SCALE; // The maximum speed the player can fall
    private final float SIGNIFICANT_FALL_SPEED = 8.0f; // The speed considered significant for falling
    private float fallSpeedAfterCollision = 0.0f * Game.SCALE; // The fall speed after a collision
    private static final float MAX_CHARGE_TIME = 2500; // max charge time in milliseconds

    // Player actions and rendering
    private BufferedImage[][] animations; // Array to hold animation frames
    private int animationTick, animationIndex, animationSpeed = 50; // Animation variables
    private int playerAction = IDLE; // The current action of the player
    private boolean left, up, right, down, jump; // Movement flags
    private float xDrawOffset = 2 * Game.SCALE; // The offset for drawing the player on the x-axis
    private float yDrawOffset = 2 * Game.SCALE; // The offset for drawing the player on the y-axis

    // Store load save = level data
    private int[][] levelData; // The level data

    // Jump charge attributes
    private boolean isChargingJump = false; // Whether the player is charging a jump
    private long jumpChargeStartTime = 0; // The start time for the jump charge
    private float jumpChargeForce = BASE_JUMP_FORCE; // The force of the jump charge
    private boolean lastDirectionLeft = false; // Whether the last direction input was left
    private boolean lastDirectionRight = false; // Whether the last direction input was right
    private boolean canRedirectJump = false; // Whether the player can redirect the jump

    // Fall attributes
    private long fallStartTime = 0; // The start time of the fall
    private int flipX = 0;
    private int flipW = 1;

    public Player(float startX, float startY, int width, int height) throws IOException {
        super(startX, startY, width, height);
        initHitBox(x, y, (int) (12 * Game.SCALE), (int) (12 * Game.SCALE)); // Initialize the hitbox
        loadAnimation(); // Load the player's animations

    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!isEntityOnGround(hitBox, levelData)) {
            isInAir = true; // Set the player to be in the air if not on the ground
        }
    }

    private void applyGravity() {
        if (isInAir) {
            if (airSpeed < MAX_FALL_SPEED) {
                airSpeed += GRAVITY; // Apply gravity to the player's air speed
            }
        }
    }

    private void startJumpCharging() {
        isChargingJump = true; // Start charging the jump
        jumpChargeStartTime = System.currentTimeMillis(); // Record the start time
        canRedirectJump = false; // Disable jump redirection initially\

        if (left) {
            lastDirectionLeft = true; // Set the last direction to left
            lastDirectionRight = false; // Unset the last direction right
        } else if (right) {
            lastDirectionRight = true; // Set the last direction to right
            lastDirectionLeft = false; // Unset the last direction left
        } else {
            lastDirectionLeft = false; // Unset the last direction left
            lastDirectionRight = false; // Unset the last direction right
        }
        System.out.println("Jump charging started: lastDirectionLeft=" + lastDirectionLeft + ", lastDirectionRight=" + lastDirectionRight);
    }

    private void releaseJump() {
        long chargeTime = System.currentTimeMillis() - jumpChargeStartTime; // Calculate the charge time
        float chargeRatio = Math.min(chargeTime / (float) MAX_CHARGE_TIME, 1.0f); // Calculate the charge ratio
        jumpChargeForce = BASE_JUMP_FORCE + chargeRatio * (MAX_JUMP_CHARGE_FORCE - BASE_JUMP_FORCE); // Calculate the jump charge force

        isChargingJump = false; // Stop charging the jump
        isInAir = true; // Set the player to be in the air
        airSpeed = jumpChargeForce; // Set the air speed to the jump charge force

        // Apply horizontal velocity based on last direction input
        if (lastDirectionLeft) {
            horizontalVelocity = -playerSpeed; // Set the horizontal velocity to left
        } else if (lastDirectionRight) {
            horizontalVelocity = playerSpeed; // Set the horizontal velocity to right
        } else {
            horizontalVelocity = 0; // Set the horizontal velocity to 0 if no direction was pressed
        }

        System.out.println("Jump released: airSpeed=" + airSpeed + ", horizontalVelocity=" + horizontalVelocity + ", chargeTime=" + chargeTime + "ms, jumpChargeForce=" + jumpChargeForce);
    }

    public void update() {
        updatePosition(); // Update the player's position
        updateAnimationTick(); // Update the animation tick
        setAnimation(); // Set the current animation

        if (isInAir && fallStartTime == 0) {
            fallStartTime = System.currentTimeMillis(); // Start fall timer
        } else if (!isInAir && fallStartTime != 0) {
            long fallDuration = System.currentTimeMillis() - fallStartTime;
            fallStartTime = 0; // Reset fall timer

            if (fallDuration >= 2000) {
                triggerSignificantFallEvent();
            } else if (fallDuration > 0) {
                triggerMinorFallEvent();
            }
        }
    }

    private void triggerSignificantFallEvent() {
        System.out.println("Significant fall event triggered.");
        // Implement the logic for significant fall event
    }

    private void triggerMinorFallEvent() {
        System.out.println("Minor fall event triggered.");
        // Implement the logic for minor fall event
    }

    private void updateAirbornePosition() {
        System.out.println("Updating airborne position...");
        System.out.println("Current position: x=" + hitBox.x + ", y=" + hitBox.y + ", airSpeed=" + airSpeed + ", horizontalVelocity=" + horizontalVelocity);

        if (HelpMeMethod.canMoveHere(hitBox.x + horizontalVelocity, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += horizontalVelocity; // Update the x position
            hitBox.y += airSpeed; // Update the y position
            applyGravity(); // Apply gravity

            if (airSpeed > MAX_FALL_SPEED) {
                airSpeed = MAX_FALL_SPEED; // Limit the air speed to max fall speed
            }

            System.out.println("Airborne: x=" + hitBox.x + ", y=" + hitBox.y + ", airSpeed=" + airSpeed + ", horizontalVelocity=" + horizontalVelocity);
        } else {
            boolean collidedHorizontally = HelpMeMethod.isEntityCollidingHorizontally(hitBox, horizontalVelocity, levelData);
            System.out.println("Collision detected. Horizontal collision: " + collidedHorizontally);

            if (collidedHorizontally) {
                if (horizontalVelocity > 0) {
                    hitBox.x = HelpMeMethod.getEntityXPosNextToWall(hitBox, horizontalVelocity) - 1; // Adjust to the left of the wall
                } else {
                    hitBox.x = HelpMeMethod.getEntityXPosNextToWall(hitBox, horizontalVelocity) + 1; // Adjust to the right of the wall
                }
                horizontalVelocity = -horizontalVelocity * 0.5f; // Rebound effect with reduced speed
                System.out.println("Horizontal collision: adjusted position to avoid sticking. x=" + hitBox.x);
            }

            boolean collidedVertically = !HelpMeMethod.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData);
            System.out.println("Vertical collision: " + collidedVertically);

            if (collidedVertically) {
                if (airSpeed > 0) {
                    // If falling, set the player on the ground
                    hitBox.y = HelpMeMethod.getEntityYPosUnderRoofOrAboveGround(hitBox, airSpeed); // Get the correct y position after collision
                    airSpeed = 0; // Stop vertical movement
                    isInAir = false; // Set the player to be on the ground
                    System.out.println("Vertical collision: player landed. y=" + hitBox.y);
                } else if (airSpeed < 0) {
                    // If jumping, stop upward movement
                    airSpeed = 0;
                    System.out.println("Vertical collision: player hit the roof.");
                }
            }

            //horizontalVelocity = 0; // Stop horizontal movement
        }
    }


    public void render(Graphics g, int lvlOffSet) {
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset) + flipX, (int) (hitBox.y - yDrawOffset) - lvlOffSet, (int) (Game.TILE_DEFAULT_SIZE * Game.SCALE) * flipW, (int) (Game.TILE_DEFAULT_SIZE * Game.SCALE), null);
        renderHitBox(g); // Render the hitbox for debugging
    }



    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0; // Reset the animation tick
            animationIndex++; // Move to the next animation frame
            if (animationIndex >= GetSpriteAmounts(playerAction)) {
                animationIndex = 0; // Loop the animation frames
            }
            System.out.println("Animation tick: animationIndex=" + animationIndex);
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (isChargingJump) {
            playerAction = CHARGING; // Ensure the player action is idle while charging jump
        } else if (isMoving) {
            playerAction = RUNNING; // Set the player action to running if moving
        } else {
            playerAction = IDLE; // Set the player action to idle if not moving
        }

        if (startAnimation != playerAction) {
            resetAnimationTick(); // Reset the animation tick if the action changes
            System.out.println("Animation changed: playerAction=" + playerAction);
        }
    }

    private void resetAnimationTick() {
        animationTick = 0; // Reset the animation tick
        animationIndex = 0; // Reset the animation index
    }

    private void updatePosition() {
        if (jump) {
            if (!isChargingJump && !isInAir) {
                startJumpCharging(); // Start charging the jump if not already charging or in the air
            } else if (isChargingJump && (System.currentTimeMillis() - jumpChargeStartTime >= MAX_CHARGE_TIME)) {
                releaseJump(); // Release the jump if charging time exceeds max charge time
            } else if (isChargingJump && (System.currentTimeMillis() - jumpChargeStartTime >= 500)) {
                canRedirectJump = true; // Allow jump redirection after 0.5 seconds of charging
            }
        } else {
            if (isChargingJump) {
                releaseJump(); // Release the jump if the jump key is released
            }
        }

        // Handle horizontal movement for jump redirection
        if (canRedirectJump) {
            if (left) {
                lastDirectionLeft = true; // Set the last direction to left
                lastDirectionRight = false; // Unset the last direction right
            } else if (right) {
                lastDirectionRight = true; // Set the last direction to right
                lastDirectionLeft = false; // Unset the last direction left
            }
        }
        // Prevent movement while charging the jump
        if (isChargingJump) {
            isMoving = false;
            return;
        }

        float xTempSpeed = 0;

        // Handle horizontal movement
        if (left) {
            xTempSpeed = -playerSpeed; // Move left
            flipX = width;
            flipW = -1;
            lastDirectionLeft = true; // Set the last direction to left
            lastDirectionRight = false; // Unset the last direction right
        }
        if (right) {
            xTempSpeed = playerSpeed; // Move right
            flipX = 0;
            flipW = 1;
            lastDirectionRight = true; // Set the last direction to right
            lastDirectionLeft = false; // Unset the last direction left
        }

        // Update the x position
        if (isInAir) {
            hitBox.x += horizontalVelocity;
        }
        else if (xTempSpeed != 0) {
            updateXPosition(xTempSpeed);
        }

        isMoving = (xTempSpeed != 0);
        System.out.println("Updated position: x=" + hitBox.x + ", y=" + hitBox.y + ", isMoving=" + isMoving + ", horizontalVelocity=" + horizontalVelocity);

        if (!isInAir && !isEntityOnGround(hitBox, levelData)) {
            isInAir = true;
            System.out.println("Became airborne");
        }

        if (isInAir) {
            updateAirbornePosition();
        }
    }

    private void resetIsInAir() {
        isInAir = false; // Set the player to be on the ground
        airSpeed = 0.05f; // Slight positive value for bounce effect
        System.out.println("Landed: airSpeed=" + airSpeed);
    }

    private void updateXPosition(float xTempSpeed) {
        if (HelpMeMethod.isEntityCollidingHorizontally(hitBox, xTempSpeed, levelData)) {
            horizontalVelocity = -horizontalVelocity * 0.5f; // Rebound effect with reduced speed
            System.out.println("Horizontal collision with rebound: horizontalVelocity=" + horizontalVelocity);
        } else if (HelpMeMethod.canMoveHere(hitBox.x + xTempSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xTempSpeed; // Update the x position
        } else {
            hitBox.x = HelpMeMethod.getEntityXPosNextToWall(hitBox, xTempSpeed); // Get the correct x position after collision
            System.out.println("Horizontal collision: x=" + hitBox.x);
        }

    }

    private void loadAnimation() throws IOException {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_PATH); // Load the player sprite atlas
        animations = new BufferedImage[5][4]; // Initialize the animations array (5 actions, 4 frames each)
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 32, i * 32, 32, 32); // Load each frame of the animation
            }
        }
        System.out.println("Animations loaded");
    }

    public void resetDirBooleans() {
        left = false; // Reset the left flag
        up = false; // Reset the up flag (redundant but needed for looking up)
        right = false; // Reset the right flag
    }

    public boolean isLeft() {
        return left; // Return whether the left flag is set
    }

    public void setLeft(boolean left) {
        this.left = left; // Set the left flag
    }

    public boolean isUp() {
        return up; // Return whether the up flag is set
    }

    public void setUp(boolean up) {
        this.up = up; // Set the up flag
    }

    public boolean isRight() {
        return right; // Return whether the right flag is set
    }

    public void setRight(boolean right) {
        this.right = right; // Set the right flag
    }

    public boolean isJumping() {
        return jump; // Return whether the jump flag is set
    }

    public void setJumping(boolean jumping) {
        this.jump = jumping; // Set the jump flag
    }

    // Getters and setters for testing purposes
    public float getYVelocity() {
        return airSpeed; // Return the air speed
    }

    public float getXVelocity() {
        return horizontalVelocity; // Return the horizontal velocity
    }

    public boolean getIsJumping() {
        return isJumping; // Return whether the player is jumping
    }

    public float getJumpTime() {
        return jumpTime; // Return the jump time
    }

    public void setYVelocity(float airSpeed) {
        this.airSpeed = airSpeed; // Set the air speed
    }

    public void setXVelocity(float horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity; // Set the horizontal velocity
    }
}


