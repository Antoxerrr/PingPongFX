package pingpong.scenes.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import pingpong.Main;
import pingpong.Router;
import pingpong.base.AppController;
import pingpong.base.dto.SceneDataDTO;
import pingpong.scenes.game.GameState;

public class MainMenuController extends AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox MainMenuLayout;

    @FXML
    private Button playButton;

    @FXML
    private Button playWithBotButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button quitButton;

    @FXML
    void initialize() {
        quitButton.setOnAction(e -> System.exit(0));
        settingsButton.setOnAction(actionEvent -> {
            SceneDataDTO settingsSceneData = Router.getSettingsScene();
            Main.setScene(MainMenuLayout.getScene(), settingsSceneData.getRoot());
        });
        playButton.setOnAction(actionEvent -> {
            SceneDataDTO gameSceneData = Router.getGameScene();
            gameSceneData.getController().initializeGameSession(GameState.GameModes.LOCAL);
            Main.setScene(MainMenuLayout.getScene(), gameSceneData.getRoot());
        });
        playWithBotButton.setOnAction(actionEvent -> {
            SceneDataDTO gameSceneData = Router.getGameScene();
            gameSceneData.getController().initializeGameSession(GameState.GameModes.LOCAL_WITH_BOT);
            Main.setScene(MainMenuLayout.getScene(), gameSceneData.getRoot());
        });
    }
}
