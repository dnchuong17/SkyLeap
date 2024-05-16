package utilz;

import entities.Player;
import entities.Player.*;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1; // look up
        public static final int RIGHT = 2;
        public static final int DOWN = 3; // look down
        public static final int JUMP = 4;
    }


    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int LOOKINGUP = 2;
        public static final int JUMPING = 3;
        public static final int CHARGING = 3;
        public static final int FAILING = 4;




        public static int GetSpriteAmounts(int player_action ) {
            switch (player_action) {
                    case RUNNING: return 4;
                    case IDLE: return 3;
//                    case JUMPING: return 4;
                    case CHARGING: return 0;
                    case FAILING: return 2;
                    case LOOKINGUP: return 2;
                    default: return 1;
            }
        }
    }
}
