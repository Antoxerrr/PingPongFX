package pingpong.base.dto;

import javafx.scene.Parent;
import pingpong.base.AppController;

public class SceneDataDTO {
    private final Parent root;
    private final AppController controller;

    public SceneDataDTO(Parent root, AppController controller) {
        this.root = root;
        this.controller = controller;
    }

    public Parent getRoot() { return root; }
    public AppController getController() { return controller; }
}
