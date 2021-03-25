package pingpong.scenes.game.utils;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

public class BallCollisions {
    public static boolean collidesY(Bounds ballBounds, Rectangle2D screenBounds) {
        return ballBounds.getMinY() <= screenBounds.getMinY() ||
                ballBounds.getMaxY() >= screenBounds.getMaxY();
    }

    public static BallXCollisionDTO collidesX(Bounds ballBounds, Rectangle2D screenBounds) {
        BallXCollisionDTO result;
        if (ballBounds.getMinX() >= screenBounds.getMaxX()) {
            result = new BallXCollisionDTO(true, PlayerIndex.FIRST_PLAYER);
        } else if (ballBounds.getMaxX() <= screenBounds.getMinX()) {
            result = new BallXCollisionDTO(true, PlayerIndex.SECOND_PLAYER);
        } else {
            result = new BallXCollisionDTO(false);
        }
        return result;
    }

    public static boolean collidesPaddle(Bounds ballBounds, Rectangle[] rackets) {
        for (Rectangle racket : rackets) {
            if (ballBounds.intersects(racket.localToParent(racket.getBoundsInLocal()))) {
                return true;
            }
        }
        return false;
    }
}
