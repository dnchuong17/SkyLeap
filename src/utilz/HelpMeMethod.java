package utilz;

import main.Game;
import java.awt.geom.Rectangle2D;

public class HelpMeMethod {
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
}
