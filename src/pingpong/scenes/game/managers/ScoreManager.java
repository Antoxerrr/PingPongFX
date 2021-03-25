package pingpong.scenes.game.managers;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import pingpong.scenes.game.GameState;
import pingpong.scenes.game.events.ScoredEventObserver;
import pingpong.scenes.game.managers.base.Manager;
import pingpong.scenes.game.utils.BallCollisions;
import pingpong.scenes.game.utils.BallXCollisionDTO;
import pingpong.scenes.game.utils.PlayerIndex;

import java.util.HashMap;

public class ScoreManager extends Manager {

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    boolean scoreBlocked = false;
    private final GameState gameState;
    private final Circle ball;
    private final Rectangle[] rackets;
    private final ScoredEventObserver scoredEventObserver;
    private final HashMap<PlayerIndex, Integer> score = new HashMap<>();
    private final HashMap<PlayerIndex, Label> scoreLabels = new HashMap<>();
    private AnimationTimer animation;

    public ScoreManager(GameState gameState, Circle ball, Label firstPlayerScore,
                        Label secondPlayerScore, Rectangle[] rackets) {
        this.gameState = gameState;
        this.ball = ball;
        this.rackets = rackets;
        this.score.put(PlayerIndex.FIRST_PLAYER, 0);
        this.score.put(PlayerIndex.SECOND_PLAYER, 0);
        this.scoreLabels.put(PlayerIndex.FIRST_PLAYER, firstPlayerScore);
        this.scoreLabels.put(PlayerIndex.SECOND_PLAYER, secondPlayerScore);
        this.scoredEventObserver = new ScoredEventObserver();
        initializeAnimation();
    }

    private void updateScore(PlayerIndex playerIndex) {
        score.put(playerIndex, score.getOrDefault(playerIndex, 0) + 1);
        Label scoreLabel = scoreLabels.get(playerIndex);
        scoreLabel.setText(Integer.toString(score.get(playerIndex)));
        scoredEventObserver.notifyScored();
    }

    private void blockScore() { scoreBlocked = true; }
    private void unBlockScore() { scoreBlocked = false; }
    public ScoredEventObserver getObserver() { return scoredEventObserver; }

    private boolean scoreUnblockAllowed(Bounds ballBounds) {
        return (BallCollisions.collidesY(ballBounds, screenBounds) ||
                BallCollisions.collidesPaddle(ballBounds, rackets)) &&
                scoreBlocked;
    }

    private void initializeAnimation() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!gameState.isPaused()) {
                    Bounds ballBounds = ball.localToParent(ball.getBoundsInLocal());
                    BallXCollisionDTO collisionResult =
                            BallCollisions.collidesX(ballBounds, screenBounds);
                    if (collisionResult.collides && !scoreBlocked) {
                        blockScore();
                        updateScore(collisionResult.playerIndex);
                    }
                    if (scoreUnblockAllowed(ballBounds)) {
                        unBlockScore();
                    }
                }
            }
        };
    }

    @Override
    public AnimationTimer getAnimation() { return animation; }
}
