package utilz;

import main.Game;
import java.awt.geom.Rectangle2D;
import static main.Game.*;

public class HelpMeMethod {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        System.out.println("canMoveHere called with x: " + x + ", y: " + y + ", width: " + width + ", height: " + height);
        boolean topLeft = isSolid(x, y, levelData);
        boolean topRight = isSolid(x + width, y, levelData);
        boolean bottomLeft = isSolid(x, y + height, levelData);
        boolean bottomRight = isSolid(x + width, y + height, levelData);
        System.out.println("topLeft: " + topLeft + ", topRight: " + topRight + ", bottomLeft: " + bottomLeft + ", bottomRight: " + bottomRight);
        return !topLeft && !topRight && !bottomLeft && !bottomRight;
    }

    private static boolean isSolid(float x, float y, int[][] levelData) {
        int maxHeight = levelData.length * TILE_SIZE;
        if (x < 0 || x >= Game.GAME_WIDTH) {
            System.out.println("x out of bounds: " + x);
            return true;
        }
        if (y < 0 || y >= maxHeight) {
            System.out.println("y out of bounds: " + y);
            return true;
        }
        int xIndex = (int) (x / Game.TILE_SIZE);
        int yIndex = (int) (y / Game.TILE_SIZE);
        System.out.println("Checking tile at [" + yIndex + "][" + xIndex + "]");
        int tileValue = levelData[yIndex][xIndex];
        System.out.println("Tile value: " + tileValue);
        // Adjust this condition based on your game's tile data
        return (tileValue >= 104 || tileValue < 0 || tileValue != 94);
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xTempSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILE_SIZE);  // current tile column based on the entity's x-coordinate.
        if (xTempSpeed > 0) {
            // If moving right:
            int tileXPos = currentTile * Game.TILE_SIZE;  // Calculate the starting x-coordinate of the current tile.
            int xOffset = (int) (Game.TILE_SIZE - hitBox.width);  // Calculate the offset to position the entity right inside the boundary.
            return tileXPos + xOffset - 1;  // Return the computed position, adjusted by -1 to avoid overlapping into the next tile.
        } else {
            // If moving left:
            return currentTile * Game.TILE_SIZE;  // Return the starting x-coordinate of the current tile.
        }
    }

    public static float getEntityYPosUnderRoofOrAboveGround(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            // If falling:
            int tileYPos = (currentTile + 1) * Game.TILE_SIZE;
            System.out.println("Falling: tileYPos=" + tileYPos + ", hitBox.height=" + hitBox.height);
            return tileYPos - hitBox.height - 1; // Adjusted by -1 to avoid overlap
        } else {
            // If jumping:
            int tileYPos = currentTile * Game.TILE_SIZE;
            System.out.println("Jumping: tileYPos=" + tileYPos);
            return tileYPos;
        }
    }

    public static boolean isEntityOnGround(Rectangle2D.Float hitBox, int[][] levelData) {
        float bottomLeftX = hitBox.x;
        float bottomLeftY = hitBox.y + hitBox.height + 1;
        float bottomRightX = hitBox.x + hitBox.width;
        float bottomRightY = hitBox.y + hitBox.height + 1;
        boolean bottomLeftSolid = isSolid(bottomLeftX, bottomLeftY, levelData);
        boolean bottomRightSolid = isSolid(bottomRightX, bottomRightY, levelData);
        System.out.println("isEntityOnGround: bottomLeftSolid=" + bottomLeftSolid + ", bottomRightSolid=" + bottomRightSolid);
        return bottomLeftSolid || bottomRightSolid;
    }

    public static boolean isEntityCollidingHorizontally(Rectangle2D.Float hitBox, float xTempSpeed, int[][] levelData) {
        if (xTempSpeed > 0) {
            return isSolid(hitBox.x + hitBox.width + xTempSpeed, hitBox.y, levelData) ||
                    isSolid(hitBox.x + hitBox.width + xTempSpeed, hitBox.y + hitBox.height, levelData);
        } else {
            return isSolid(hitBox.x + xTempSpeed, hitBox.y, levelData) ||
                    isSolid(hitBox.x + xTempSpeed, hitBox.y + hitBox.height, levelData);
        }
    }
}
