package utilz;

import main.Game;

public class Constants {

    public static class UI {
        //menu button size
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
//pause button size
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT *Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;

            public static final int VOLUME_WIDTH= (int) (VOLUME_WIDTH_DEFAULT *Game.SCALE);
            public static final int VOLUME_HEIGHT= (int) (VOLUME_HEIGHT_DEFAULT *Game.SCALE);
            public static final int SLIDER_WIDTH= (int) (SLIDER_WIDTH_DEFAULT *Game.SCALE);


        }
    }

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
        public static final int FAILING = 4;


        public static int GetSpriteAmounts(int player_action ) {
            switch (player_action) {
                    case RUNNING: return 4;
                    case IDLE: return 3;
                    case JUMPING: return 4;
                    case FAILING: return 2;
                    case LOOKINGUP: return 2;
                    default: return 1;
            }
        }
    }
}
