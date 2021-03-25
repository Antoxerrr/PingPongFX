package pingpong.scenes.game.utils;


public class BallXCollisionDTO {
    public boolean collides;
    public PlayerIndex playerIndex = null;

    public BallXCollisionDTO(boolean collides, PlayerIndex playerIndex) {
        this.collides = collides;
        this.playerIndex = playerIndex;
    }
    public BallXCollisionDTO(boolean collides) {
        this.collides = collides;
    }
}
