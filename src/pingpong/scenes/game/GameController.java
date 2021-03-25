package pingpong.scenes.game;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pingpong.Main;
import pingpong.Router;
import pingpong.base.AppController;
import pingpong.core.KeyBindings;
import pingpong.scenes.game.events.ScoredEventObserver;
import pingpong.scenes.game.managers.*;
import pingpong.scenes.game.managers.base.RacketControlsManager;

public class GameController extends AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle playerOneRacket;

    @FXML
    private Rectangle playerTwoRacket;

    @FXML
    private Label firstPlayerScore;

    @FXML
    private Label secondPlayerScore;

    @FXML
    private Circle ball;

    @FXML
    private Button returnToGameButton;

    @FXML
    private Button returnToMenuButton;

    private GameState gameState;

    // Менеджеры сцены
    private BallManager ballManager;
    private RacketControlsManager firstPlayerManager;
    private RacketControlsManager secondPlayerManager;
    private ScoreManager scoreManager;
    private GameStateManager gameStateManager;

    private void initializeManagers() {
        Rectangle[] rackets = new Rectangle[]{playerOneRacket,  playerTwoRacket};
        KeyBindings firstPlayerControls = new KeyBindings(KeyCode.W, KeyCode.S);
        KeyBindings secondPlayerControls = new KeyBindings(KeyCode.UP, KeyCode.DOWN);
        Scene scene = getScene();
        GameState.GameModes gameMode = gameState.getGameMode();

        ballManager = new BallManager(ball, rackets, gameState);
        firstPlayerManager = new LocalPlayerControlsManager(scene, playerOneRacket, firstPlayerControls, gameState, ball);
        if (gameMode == GameState.GameModes.LOCAL) {
            secondPlayerManager = new LocalPlayerControlsManager(
                    scene, playerTwoRacket, secondPlayerControls, gameState, ball
            );
        } else if (gameMode == GameState.GameModes.LOCAL_WITH_BOT) {
            secondPlayerManager = new BotPlayerControlsManager(
                    scene, playerTwoRacket, secondPlayerControls, gameState, ball
            );
        }
        scoreManager = new ScoreManager(gameState, ball, firstPlayerScore, secondPlayerScore, rackets);
        gameStateManager = new GameStateManager(gameState, scene, returnToGameButton, returnToMenuButton);
    }

    private void subscribeOnScoredEvent() {
        ScoredEventObserver observer = scoreManager.getObserver();
        observer.subscribe(ballManager);
        observer.subscribe(firstPlayerManager);
        observer.subscribe(secondPlayerManager);
    }

    private void startAnimations() {
        ballManager.getAnimation().start();
        firstPlayerManager.getAnimation().start();
        secondPlayerManager.getAnimation().start();
        scoreManager.getAnimation().start();
        gameStateManager.getAnimation().start();
    }

    private void stopAnimations() {
        ballManager.getAnimation().stop();
        firstPlayerManager.getAnimation().stop();
        secondPlayerManager.getAnimation().stop();
        scoreManager.getAnimation().stop();
        gameStateManager.getAnimation().stop();
    }

    @Override
    public void initializeGameSession(GameState.GameModes gameMode) {
        gameState = new GameState(gameMode);
        initializeManagers();
        subscribeOnScoredEvent();
        startAnimations();
        returnToMenuButton.setOnAction(actionEvent -> {
            stopAnimations();
            Main.setScene(getScene(), Router.getMainMenuScene().getRoot());
        });
    }

    @FXML
    void initialize() {
        returnToGameButton.setFocusTraversable(false);
        returnToMenuButton.setFocusTraversable(false);
        returnToMenuButton.setVisible(false);
        returnToGameButton.setVisible(false);
    }
}