package pingpong.scenes.game.managers;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pingpong.core.KeyBindings;
import pingpong.scenes.game.GameState;
import pingpong.scenes.game.managers.base.RacketControlsManager;
import pingpong.scenes.game.managers.base.ScoreEventListener;

public class BotPlayerControlsManager  extends RacketControlsManager implements ScoreEventListener {

    private double dumping = 0.9;
    private double dumpingMaxLimit = 0.9;
    private double dumpingMinLimit = 0.7;
    private double dumpingStep = 0.0000001;
    private int dumpingDirection = -1;
    private AnimationTimer animation;

    public BotPlayerControlsManager(Scene scene, Rectangle racket, KeyBindings keyBindings,
                                    GameState gameState, Circle ball) {
        super(scene, racket, keyBindings, gameState, ball);
        initializeAnimation();
    }

    private boolean ballIsAbove() {
        return ball.getTranslateY() < racket.getTranslateY();
    }

    private boolean ballIsBelow() {
        return ball.getTranslateY() > racket.getTranslateY();
    }

    private void detectDirection() {
        if (ballIsAbove() && !limitAbove()) {
            stopDown();
            goUp();
        }
        else if (ballIsBelow() && !limitBelow()) {
            stopUp();
            goDown();
        }
    }

    private void decideDumping() {
        dumping += dumpingStep * dumpingDirection;
        if (dumping == dumpingMaxLimit || dumping == dumpingMinLimit) {
            dumpingDirection *= -1;
        }
    }

    private void initializeAnimation() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!gameState.isPaused()) {
                    detectDirection();
                    decideDumping();
                    if (isGoUp && !limitAbove()) {
                        newY -= racketSpeed;
                    } else if (isGoDown && !limitBelow()) {
                        newY += racketSpeed;
                    }
                    racket.setTranslateY(newY * dumping);
                }
            }
        };
    }

    @Override
    public AnimationTimer getAnimation() { return animation; }
}
