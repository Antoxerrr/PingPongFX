package pingpong.scenes.game.managers;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pingpong.scenes.game.GameState;
import pingpong.scenes.game.managers.base.Manager;

public class GameStateManager extends Manager {

    private final GameState gameState;
    private final Scene scene;
    private final Button returnToGameButton;
    private final Button returnToMenuButton;
    private AnimationTimer animation;

    public GameStateManager(GameState gameState, Scene scene,
                            Button returnToGameButton, Button returnToMenuButton) {
        this.gameState = gameState;
        this.scene = scene;
        this.returnToGameButton = returnToGameButton;
        this.returnToMenuButton = returnToMenuButton;
        initializePauseEvent();
        initializeMenuEvents();
        initializeAnimation();
    }

    private void pause() { gameState.pause(); }
    private void unpause() {
        gameState.unpause();
    }

    private void setMenuVisible(boolean v) {
        returnToGameButton.setVisible(v);
        returnToMenuButton.setVisible(v);
    }

    private void initializeMenuEvents() {
        returnToGameButton.setOnAction(actionEvent -> {
            setMenuVisible(false);
            unpause();
        });
    }

    private void initializePauseEvent() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.ESCAPE) {
                if (!gameState.isPaused()) {
                    pause();
                    setMenuVisible(true);
                } else {
                    unpause();
                    setMenuVisible(false);
                }
            }
        });
    }

    private void initializeAnimation() {
        animation = new AnimationTimer() {
            @Override
            public void handle(long l) {}
        };
    }

    @Override
    public AnimationTimer getAnimation() { return animation; }
}
