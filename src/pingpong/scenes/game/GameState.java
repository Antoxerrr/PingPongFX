package pingpong.scenes.game;

public class GameState {
    private boolean paused = false;
    private final GameModes gameMode;

    public enum GameModes { LOCAL, LOCAL_WITH_BOT, ONLINE }

    public GameState(GameModes mode) {
        this.gameMode = mode;
    }

    public boolean isPaused() { return paused; }
    public void pause() { paused = true; }
    public void unpause() { paused = false; }
    public GameModes getGameMode() { return gameMode; }
}
