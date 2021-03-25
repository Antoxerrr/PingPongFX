package pingpong.scenes.game.managers.base;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import pingpong.core.KeyBindings;
import pingpong.scenes.game.GameConfig;
import pingpong.scenes.game.GameState;

public abstract class RacketControlsManager extends Manager implements ScoreEventListener {

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    protected boolean isGoUp, isGoDown;
    protected double newY = 0;
    protected final double racketSpeed = GameConfig.RACKET_SPEED;

    protected final KeyBindings keyBindings;
    protected final Rectangle racket;
    protected final GameState gameState;
    protected final Scene scene;
    protected final Circle ball;

    public RacketControlsManager(Scene scene, Rectangle racket,
                                      KeyBindings keyBindings,
                                      GameState gameState, Circle ball) {
        this.scene = scene;
        this.racket = racket;
        this.keyBindings = keyBindings;
        this.gameState = gameState;
        this.ball = ball;
    }

    protected boolean limitAbove() {
        Bounds bounds = racket.localToParent(racket.getBoundsInLocal());
        return bounds.getMinY() <= screenBounds.getMinY();
    }
    protected boolean limitBelow() {
        Bounds bounds = racket.localToParent(racket.getBoundsInLocal());
        return bounds.getMaxY() >= screenBounds.getMaxY();
    }

    public void goUp() { isGoUp = true; }

    public void goDown() {
        isGoDown = true;
    }

    public void stopUp() {
        isGoUp = false;
    }

    public void stopDown() {
        isGoDown = false;
    }

    @Override
    public void onScored() {
        newY = 0;
        racket.setTranslateY(0);
    }
}
