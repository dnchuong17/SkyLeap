package utilz;

import main.Game;
import java.awt.geom.Rectangle2D;

public class HelpMeMethod {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        return !isSolid(x, y, levelData) &&
                !isSolid(x + width, y, levelData) &&
                !isSolid(x, y + height, levelData) &&
                !isSolid(x + width, y + height, levelData);
    }

    private static boolean isSolid(float x, float y, int[][] levelData) {
        if (x < 0 || y < 0 || x >= Game.GAME_WIDTH || y >= Game.GAME_HEIGHT) {
            return true;  // Return true if the coordinates are out of the game boundaries, treating out-of-bounds as solid.
        }
        int xIndex = (int) (x / Game.TILE_SIZE);  // column index of the tile based on x-coordinate.
        int yIndex = (int) (y / Game.TILE_SIZE);  // row index of the tile based on y-coordinate.
        int tileValue = levelData[yIndex][xIndex];  // Retrieve the tile type from the level data using the calculated indices.
        return tileValue >= 48 || tileValue != 11;  // Determine if the tile is solid based on its value.
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
        int currentTile = (int) (hitBox.y / Game.TILE_SIZE);  // current tile row based on the entity's y-coordinate.
        if (airSpeed > 0) {
            // If falling:
            int tileYPos = currentTile * Game.TILE_SIZE;  // Calculate the starting y-coordinate of the current tile.
            int yOffset = (int) (Game.TILE_SIZE - hitBox.height);  // Calculate the offset to position the entity just above the ground.
            return tileYPos + yOffset - 1;  // Return the computed position, adjusted by -1 to ensure it does not overlap into the ground.
        } else {
            // If jumping:
            return currentTile * Game.TILE_SIZE;  // Return the starting y-coordinate of the tile directly under the roof.
        }
    }

    public static boolean isEntityOnGround(Rectangle2D.Float hitBox, int[][] levelData) {
        // Check the tiles at the bottom-left and bottom-right corners below the hitbox to determine if they are solid.
        return isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData) ||
                isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData);
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] levelData) {
        if(!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData)) {
            if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData)) {
                return false;
            }
        }
        return true;
    }
}

// old code
/*public class HelpMeMethod {
    public static <levelData> boolean canMoveHere(float x, float y, float width, float height ,int[][] levelData) {
        if(!isSolid(x, y, levelData)) {
            if(!isSolid(x + width, y, levelData)) {
                if(!isSolid(x, y + height, levelData)) {
                    if(!isSolid(x + width, y + height, levelData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean isSolid(float x, float y, int[][] levelData) {
        if (x < 0 || x >= Game.GAME_WIDTH) {         // check if x is out of bounds
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {        // check if y is out of bounds
            return true;
        }
        float xIndex = x / Game.TILE_SIZE;          // get the x index
        float yIndex = y / Game.TILE_SIZE;          // get the y index
        int value = levelData[(int)yIndex][(int)xIndex]; // get the value of the tile

        if(value >= 48 || value != 11) {                            // check if the tile is empty
            return true;
        } else {
            return false;
        }
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xTempSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILE_SIZE);
        if (xTempSpeed > 0) {
            // moving right
            int tileXPos = currentTile * Game.TILE_SIZE;
            int xOffSet = (int)(Game.TILE_SIZE - hitBox.width);
            return tileXPos + xOffSet -1;
        } else {
            // moving left
            return currentTile * Game.TILE_SIZE;
        }
    }
    public static float GetEntityYPosUnderRoofOrAboveGround(Rectangle2D.Float hitBox, float airSpeed){
        int currentTile = (int) (hitBox.y / Game.TILE_SIZE);
        if (airSpeed > 0) {
            // falling - check if the player is on the ground
            int tileYPos = currentTile * Game.TILE_SIZE;
            int yOffSet = (int)(Game.TILE_SIZE - hitBox.height);
            return tileYPos + yOffSet -1;
        } else {
            // jumping - check if the player is under the roof
            return currentTile * Game.TILE_SIZE;
        }
    }
    public static boolean IsEntityOnGround(Rectangle2D.Float hitBox, int[][] levelData){
        // check if the pixel below bottom left corner and bottom right of the hitbox is solid
        if(!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData) && !isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData)){
            return false;
        }else {
            return true;
        }
    }
}*/
