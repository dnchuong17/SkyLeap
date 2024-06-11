package UI;

import gameStates.Gamestate;
import gameStates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.UrmButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private SoundButtons musicButton, sfxButton;

    private UrmButtons menuB, replayB, unpauseB;
    private VolumeButton volumeButton;
    public PauseOverlay(Playing playing) throws IOException {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButtons();
    }

    private void createVolumeButtons() throws IOException {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 *Game.SCALE);
        volumeButton = new VolumeButton(vX, vY,SLIDER_WIDTH, VOLUME_HEIGHT);
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

    private void createSoundButtons() throws IOException {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButtons(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButtons(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

    }

    private void loadBackground() throws IOException {
        backgroundImage = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImage.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImage.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH /2 - bgW /2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();

    }

    public void draw(Graphics g){
        //Background
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);
        //Sound Buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        //Urm Buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        //Volume buttons
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e){
        if (volumeButton.isMousePressed()){
            volumeButton.changeX(e.getX());
        }


    }
    public void mousePressed(MouseEvent e){
       if (isIn(e, musicButton))
           musicButton.setMousePressed(true);
       if (isIn(e, sfxButton))
           sfxButton.setMousePressed(true);
       if (isIn(e, menuB))
           menuB.setMousePressed(true);
       if (isIn(e, replayB))
           replayB.setMousePressed(true);
       if (isIn(e, unpauseB))
           unpauseB.setMousePressed(true);
        if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) throws IOException {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        }
        if (isIn(e, sfxButton)){
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        }
        if (isIn(e, menuB)){
            if (menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
            playing.unpauseGame();
        }

        if (isIn(e, replayB)){
            if(replayB.isMousePressed())
                playing.resetAll();
                playing.unpauseGame();
        }

        if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unpauseGame();
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e){
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        }
        if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        }
        if (isIn(e, replayB))
            replayB.setMouseOver(true);
        if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        }
        if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }


    private boolean isIn(MouseEvent e, PauseButtons b){
        return b.getBounds().contains(e.getX(), e.getY());
    }





}
