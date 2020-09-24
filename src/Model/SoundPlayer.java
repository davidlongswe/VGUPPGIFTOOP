package Model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class SoundPlayer {

    public static final String MAIN_MUSIC = "src/Model/resources/sounds/main_music.wav";
    public static final String EPIC_INTRO = "src/Model/resources/sounds/epic_intro.wav";
    public static final String HEART_SUSPENSE = "src/Model/resources/sounds/heart_suspense.wav";
    public static final String SHOOT = "src/Model/resources/sounds/tank_shot.wav";
    public static final String EXPLOSION = "src/Model/resources/sounds/explosion.wav";
    public static final String TANK_HIT = "src/Model/resources/sounds/tank_hit.wav";
    public static final String DEATH = "src/Model/resources/sounds/death.wav";

    File file;
    AudioClip ac;
    Media media;
    MediaPlayer mediaPlayer;
    boolean muted = false;

    public SoundPlayer() {
    }

    public void play(String s){
        switch(s){
            case "MAIN_MUSIC":
                if(!muted){
                    playMedia(MAIN_MUSIC);
                }
                break;
            case "EPIC_INTRO":
                playMedia(EPIC_INTRO);
                break;
            case "HEART_SUSPENSE":
                playMedia(HEART_SUSPENSE);
                break;
            case "SHOOT":
                if(!muted){
                    playAudioClip(SHOOT, 0.1);
                }
                break;
            case "EXPLOSION":
                if(!muted){
                    playAudioClip(EXPLOSION, 1);
                }
                break;
            case "TANK_HIT":
                if(!muted){
                    playAudioClip(TANK_HIT, 0.05);
                }
                break;
            case "DEATH":
                if(!muted){
                    playAudioClip(DEATH, 1);
                }
                break;
        }
    }

    private void playAudioClip(String filename, double volume){
        file = new File(filename);
        ac = new AudioClip(file.toURI().toString());
        ac.setVolume(volume);
        ac.play();
    }

    private void playMedia(String filename){
        file = new File(filename);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }

    public void muteSounds(){
        this.muted = true;
    }

    public void unmuteSounds(){
        this.muted = false;
    }

    public void pauseMusic(){
        mediaPlayer.pause();
    }

    public void stopMusic(){
        mediaPlayer.stop();
    }

    public void playMusic(){
        mediaPlayer.play();
    }

}
