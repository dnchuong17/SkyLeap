package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {
    public static int MENU = 0;

    //in game
    public static int JUMP = 0;

    private Clip[] songs, effects;
    private int currentSongID;
    private float volume = 0.6f;
    private boolean songMute, effectMute;


    public AudioPlayer(){
        loadSongs();
        loadEffects();
        playSong(MENU);
    }

    private void loadSongs(){
        String[] names = {"/Audio/MenuSound.wav"};
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++){
            songs[i] = getClip(names[i]);
        }
    }

   private void loadEffects(){
        String[] effectNames = {"/Audio/JumpSound.wav"};
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++){
                effects[i] = getClip(effectNames[i]);
        }

        updateEffectVolume();
    }

    private Clip getClip(String name){
        URL url = this.getClass().getResource(name);
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
        return null;
    }


    public void playEffect(int effect){
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }


   public void setVolume(float volume){
        this.volume = volume;
        updateSongVolume();
        updateEffectVolume();
    }


    public void stopSong(){
        if (songs[currentSongID].isActive())
        songs[currentSongID].stop();
    }
    public void stopEffect(){
        for (Clip clip: effects){
            if (clip.isActive())
                clip.stop();
        }
    }



    public void playSong(int song){
        stopSong();

        currentSongID = song;
        updateSongVolume();
        songs[currentSongID].setMicrosecondPosition(0);
        songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);

    }
    public void toggleSongMute(){
        this.songMute = !songMute;
        for (Clip clip: songs){
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }

    }
    public void toggleEffectMute(){
        this.effectMute = !effectMute;
        for (Clip clip: effects){
            BooleanControl booleanControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        if (!effectMute)
            playEffect(JUMP);

    }

    private void updateSongVolume(){
        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }
    private void updateEffectVolume(){
        for (Clip clip: effects) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}
