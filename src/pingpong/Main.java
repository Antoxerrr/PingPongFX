package pingpong;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pingpong.core.Assets;
import pingpong.scenes.game.GameController;
import pingpong.scenes.mainmenu.MainMenuController;
import pingpong.scenes.settings.SettingsController;

public class Main extends Application {

    static public void setScene(Scene scene, Parent root) {
        scene.setRoot(root);
    }

    private void applySceneToControllers(Scene scene) {
        GameController.setScene(scene);
        SettingsController.setScene(scene);
        MainMenuController.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ping Pong FX");
        Parent root = Router.getMainMenuScene().getRoot();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        applySceneToControllers(scene);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        Assets.loadAssets();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
