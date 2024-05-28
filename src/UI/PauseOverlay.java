package UI;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static utilz.Constants.UI.PauseButtons.*;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private SoundButtons musicButton, sfxButton;
    public PauseOverlay() throws IOException {
        loadBackground();
        createSoundButtons();
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

    }

    public void draw(Graphics g){
        //Background
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);
        //Sound Buttons
        musicButton.draw(g);
        sfxButton.draw(g);
    }
    public void mousePressed(MouseEvent e){
       if (isIn(e, musicButton))
           musicButton.setMousePressed(true);
       if (isIn(e, sfxButton))
           sfxButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e){
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        }
        if (isIn(e, sfxButton)){
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        }

        musicButton.resetBools();
        sfxButton.resetBools();
    }

    public void mouseMoved(MouseEvent e){
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        }
        if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
    }


    private boolean isIn(MouseEvent e, PauseButtons b){
        return b.getBounds().contains(e.getX(), e.getY());
    }





}
