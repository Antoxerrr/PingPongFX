package pingpong.base;

import javafx.scene.Scene;
import pingpong.scenes.game.GameState;

public abstract class AppController {
    private static Scene scene;

    public static void setScene(Scene scene) {
        AppController.scene = scene;
    }

    public static Scene getScene() { return scene; }
    public void initializeGameSession(GameState.GameModes gameMode) {}
}
