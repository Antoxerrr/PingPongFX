package pingpong.scenes.game.managers;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pingpong.core.KeyBindings;
import pingpong.scenes.game.GameState;
import pingpong.scenes.game.managers.base.RacketControlsManager;
import pingpong.scenes.game.managers.base.ScoreEventListener;

public class LocalPlayerControlsManager extends RacketControlsManager implements ScoreEventListener {

    private AnimationTimer animation;

    public LocalPlayerControlsManager(Scene scene, Rectangle racket,
                                      KeyBindings keyBindings,
                                      GameState gameState, Circle ball) {
        super(scene, racket, keyBindings, gameState, ball);
        initKeysEvents();
        initializeAnimation();
    }

    private void initKeysEvents() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == keyBindings.UP_KEY) { goUp(); }
            else if (keyCode == keyBindings.DOWN_KEY) { goDown(); }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == keyBindings.UP_KEY) { stopUp(); }
            else if (keyCode == keyBindings.DOWN_KEY) { stopDown(); }
        });
    }

    private void initializeAnimation() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!gameState.isPaused()) {
                    if (isGoUp && !limitAbove()) {
                        newY -= racketSpeed;
                    } else if (isGoDown && !limitBelow()) {
                        newY += racketSpeed;
                    }
                    racket.setTranslateY(newY);
                }
            }
        };
    }

    @Override
    public AnimationTimer getAnimation() { return animation; }
}
