package app;

import app.player.LocalPlayer;
import app.playlist.Playlist;
import app.playlist.Song;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Store {

    private final LocalPlayer player = new LocalPlayer();

    private final Set<Consumer<Duration>> timeChangeListeners;
    private final Set<Consumer<Duration>> audioLoadedListeners;
    private final Set<Consumer<Boolean>> sceneChangeListeners;
    private final Set<Consumer<String>> titleChangeListeners;

    private int volume;
    private boolean isPlayed;

    public Store() {
        volume = (int) (player.getVolume() * 100);
        timeChangeListeners = new HashSet<>();
        sceneChangeListeners = new HashSet<>();
        titleChangeListeners = new HashSet<>();
        audioLoadedListeners = new HashSet<>();

        player.setOnPlaying(this::timeChange);
        player.setOnAudioLoaded(this::audioLoaded);

        player.changePlaylist(new Playlist("xd", List.of(new Song("Bet My Heart.mp3"))));
    }

    public void playPause() {
        isPlayed = !isPlayed;
        if (isPlayed) {
            player.play();
        } else {
            player.pause();
        }
    }

    public void shuffle() {
        player.shuffle();
    }

    public void next() {
        player.next();
    }

    public void previous() {
        player.previous();
    }

    public void changeVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume should be in range [0, 100]");
        }
        this.volume = volume;
        player.changeVolume(1.0f * volume / 100);
    }

    public void repeat() {
        // @TODO
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void subscribeTimeChange(Consumer<Duration> action) {
        timeChangeListeners.add(action);
    }

    private void timeChange(Duration currentTime) {
        timeChangeListeners.forEach(c -> c.accept(currentTime));
    }

    public void subscribeAudioLoaded(Consumer<Duration> action) {
        audioLoadedListeners.add(action);
    }

    private void audioLoaded(Duration duration) {
        audioLoadedListeners.forEach(c -> c.accept(duration));
        titleChange(player.getTitle());
    }

    public void subscribeSceneChange(Consumer<Boolean> action) {
        sceneChangeListeners.add(action);
    }

    public void sceneChange() {
        sceneChangeListeners.forEach(c -> c.accept(isPlayed));
    }

    public void subscribeTitleChange(Consumer<String> action) {
        titleChangeListeners.add(action);
    }

    public void titleChange(String newTitle) {
        titleChangeListeners.forEach(c -> c.accept(newTitle));
    }

    public int getVolume() {
        return volume;
    }


}
