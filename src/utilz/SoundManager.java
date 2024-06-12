package utilz;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SoundManager {
    private Map<String, List<Clip>> soundMap;

    public SoundManager() {
        soundMap = new HashMap<>();
    }

    public void loadSound(String name, String path) {
        try {
            URL soundURL = getClass().getResource(path);
            Objects.requireNonNull(soundURL, "Sound file not found: " + path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundMap.computeIfAbsent(name, k -> new ArrayList<>()).add(clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSound(String name) {
        List<Clip> clips = soundMap.get(name);
        if (clips != null && !clips.isEmpty()) {
            Clip clip = clips.get(0);
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
            clips.remove(0);
            soundMap.get(name).add(clip); // Recycle the clip to allow concurrent playing
        }
    }

    public void stopSound(String name) {
        List<Clip> clips = soundMap.get(name);
        if (clips != null) {
            for (Clip clip : clips) {
                clip.stop();
            }
        }
    }

    public void close() {
        for (List<Clip> clips : soundMap.values()) {
            for (Clip clip : clips) {
                clip.close();
            }
        }
    }

    public void setVolume(String name, float volume) {
        List<Clip> clips = soundMap.get(name);
        if (clips != null) {
            for (Clip clip : clips) {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(volume);
            }
        }
    }

    // Load and play background music (OST)
    private Clip backgroundClip;

    public void loadBackgroundMusic(String path) {
        try {
            URL soundURL = getClass().getResource(path);
            Objects.requireNonNull(soundURL, "Background music file not found: " + path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the background music
            backgroundClip.start();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    public void setBackgroundMusicVolume(float volume) {
        if (backgroundClip != null) {
            FloatControl volumeControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        }
    }
}



