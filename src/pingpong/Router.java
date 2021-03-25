package pingpong;

import javafx.fxml.FXMLLoader;
import pingpong.base.dto.SceneDataDTO;


import java.io.IOException;

public class Router {

    private static SceneDataDTO createScene(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Router.class.getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-228);
        }
        return new SceneDataDTO(loader.getRoot(), loader.getController());
    }

    public static SceneDataDTO getMainMenuScene() {
        final String MainMenuFXMLPath = "templates/MainMenu.fxml";
        return createScene(MainMenuFXMLPath);
    }
    public static SceneDataDTO getSettingsScene() {
        final String SettingsFXMLPath = "templates/settings.fxml";
        return createScene(SettingsFXMLPath);
    }
    public static SceneDataDTO getGameScene() {
        final String GameFXMLPath = "templates/game.fxml";
        return createScene(GameFXMLPath);
    }
}
