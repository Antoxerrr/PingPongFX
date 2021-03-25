package pingpong.scenes.game.managers;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import pingpong.core.Assets;
import pingpong.scenes.game.GameConfig;
import pingpong.scenes.game.GameState;
import pingpong.scenes.game.managers.base.Manager;
import pingpong.scenes.game.managers.base.ScoreEventListener;
import pingpong.scenes.game.utils.BallCollisions;

public class BallManager extends Manager implements ScoreEventListener {

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    private final GameState gameState;
    private final Circle ball;
    private double dx, dy;
    private int newX = 0, newY = 0;
    private final Rectangle[] rackets;
    private double speedMultiplier = 1;
    private double multiplierStep = 0.0009;
    private AnimationTimer animation;

    public BallManager(Circle ball, Rectangle[] rackets, GameState gameState) {
        this.ball = ball;
        this.rackets = rackets;
        this.gameState = gameState;
        dx = GameConfig.BALL_SPEED;
        dy = -GameConfig.BALL_SPEED;
        initializeAnimation();
    }

    private void nullifyBallParameters() {
        newX = 0;
        newY = 0;
        speedMultiplier = 1;
    }

    @Override
    public void onScored() {
        nullifyBallParameters();
        ball.setTranslateX(0);
        ball.setTranslateY(0);
    }

    private void initializeAnimation() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!gameState.isPaused()) {
                    Bounds ballBounds = ball.localToParent(ball.getBoundsInLocal());
                    if (BallCollisions.collidesY(ballBounds, screenBounds)) {
                        dy *= -1;
                        speedMultiplier += multiplierStep;
                        Assets.playHitSound();
                    }
                    else if (BallCollisions.collidesPaddle(ballBounds, rackets)) {
                        dx *= -1;
                        speedMultiplier += multiplierStep;
                        Assets.playHitSound();
                    }
                    newX += dx * speedMultiplier;
                    newY += dy * speedMultiplier;
                    ball.setTranslateX(newX);
                    ball.setTranslateY(newY);
                }
            }
        };
    }

    @Override
    public AnimationTimer getAnimation() { return animation; }
}
