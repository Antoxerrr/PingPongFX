package pingpong.scenes.game.events;

import pingpong.scenes.game.managers.base.ScoreEventListener;

import java.util.ArrayList;

public class ScoredEventObserver {
    private final ArrayList<ScoreEventListener> listeners = new ArrayList<>();

    public void subscribe(ScoreEventListener listener) {
        listeners.add(listener);
    }
    public void unsubscribe(ScoreEventListener listener) {
        listeners.remove(listener);
    }
    public void notifyScored() {
        for (ScoreEventListener listener : listeners) {
            listener.onScored();
        }
    }
}
