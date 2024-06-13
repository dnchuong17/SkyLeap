package UI;

import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utilz.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

public class AudioOptions {

    private VolumeButton volumeButton;
    private SoundButtons musicButton, sfxButton;
    private Game game;
    public AudioOptions(Game game) throws IOException {
        this.game = game;
        createSoundButtons();
        createVolumeButtons();
    }

    private void createVolumeButtons() throws IOException {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 *Game.SCALE);
        volumeButton = new VolumeButton(vX, vY,SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createSoundButtons() throws IOException {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButtons(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButtons(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

    }

    public void update(){
        volumeButton.update();
        sfxButton.update();
        musicButton.update();
    }

    public void draw(Graphics g){
        //sound buttons
        volumeButton.draw(g);
        sfxButton.draw(g);

        //volume buttons
        musicButton.draw(g);
    }

    public void mouseDragged(MouseEvent e){
        if (volumeButton.isMousePressed()){
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();
            if (valueBefore != valueAfter)
                game.getAudioPlayer().setVolume(valueAfter);
        }


    }
    public void mousePressed(MouseEvent e){
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e){
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleSongMute();
            }
        }
        else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                game.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e){
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        }
        if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }


    private boolean isIn(MouseEvent e, PauseButtons b){
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
