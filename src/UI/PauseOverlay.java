package UI;

import gameStates.Gamestate;
import gameStates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.UI.UrmButtons.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private AudioOptions audioOptions;

    private UrmButtons menuB, replayB, unpauseB;

    public PauseOverlay(Playing playing) throws IOException {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButtons();

    }

    private void createUrmButtons() throws IOException {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (465 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuB = new UrmButtons(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButtons(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButtons(unpauseX, bY, URM_SIZE, URM_SIZE, 0);

    }

    private void loadBackground() throws IOException {
        backgroundImage = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImage.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImage.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();

    }

    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        //Urm Buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB))
            menuB.setMousePressed(true);
        if (isIn(e, replayB))
            replayB.setMousePressed(true);
        if (isIn(e, unpauseB))
            unpauseB.setMousePressed(true);
        else audioOptions.mousePressed(e);

    }

    public void mouseReleased(MouseEvent e) throws IOException {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
            playing.unpauseGame();
        }

        if (isIn(e, replayB)) {
            if (replayB.isMousePressed())
                playing.resetAll();
                playing.unpauseGame();
        }

        if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unpauseGame();
        } else audioOptions.mouseReleased(e);
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        }
        if (isIn(e, replayB))
            replayB.setMouseOver(true);
        if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else audioOptions.mouseMoved(e);
    }


    private boolean isIn(MouseEvent e, PauseButtons b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
