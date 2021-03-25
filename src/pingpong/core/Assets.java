package pingpong.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Assets {

    private static MediaPlayer ballHitSound;

    public static void loadAssets() {
        loadBallHitSound();
    }

    private static void loadBallHitSound() {
        String ballHitSoundPath = "src/pingpong/assets/ballHitSound.wav";
        Media sound = new Media(new File(ballHitSoundPath).toURI().toString());
        ballHitSound = new MediaPlayer(sound);
        ballHitSound.setVolume(0.15);
    }

    public static void playHitSound() {
        ballHitSound.seek(Duration.ZERO);
        ballHitSound.play();
    }
}
