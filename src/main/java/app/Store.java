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
    private int currentTime;

    public Store() {
        volume = (int) (player.getVolume() * 100);
        timeChangeListeners = new HashSet<>();
        sceneChangeListeners = new HashSet<>();
        titleChangeListeners = new HashSet<>();
        audioLoadedListeners = new HashSet<>();

        player.setOnPlaying(this::timeChange);
        player.setOnAudioLoaded(this::audioLoaded);

        player.changePlaylist(new Playlist("xd", List.of(
            new Song("Bet My Heart.mp3"),
            new Song("Visions.mp3"),
            new Song("This Love.mp3")
        )));
    }

    /**
     * play if not playing, pause if playing
     */
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

    /**
     * play next song
     */
    public void next() {
        player.next();
    }

    /**
     * rewind song if listening longer than 5 seconds, otherwise play previous song
     */
    public void previous() {
        if (currentTime < 5) {
            player.previous();
        } else {
            player.rewind();
        }
    }

    /**
     * change player volume
     *
     * @param volume new volume in range [0, 100]
     * @throws IllegalArgumentException if new volume is out of range
     */
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

    /**
     * @return true, if playing now, otherwise false
     */
    public boolean isPlayed() {
        return isPlayed;
    }

    /**
     * @param action method that will be performed after change of song time
     */
    public void subscribeTimeChange(Consumer<Duration> action) {
        timeChangeListeners.add(action);
    }

    /**
     * notify subscribers about change in song time
     *
     * @param currentTime current time of the song
     */
    private void timeChange(Duration currentTime) {
        this.currentTime = (int) currentTime.toSeconds();
        timeChangeListeners.forEach(c -> c.accept(currentTime));
    }

    /**
     * subscribe to audio load
     *
     * @param action method that will be performed after audio load
     */
    public void subscribeAudioLoaded(Consumer<Duration> action) {
        audioLoadedListeners.add(action);
    }

    /**
     * notify subscribers about audio loaded and change title
     *
     * @param duration duration of the song
     */
    private void audioLoaded(Duration duration) {
        currentTime = 0;
        audioLoadedListeners.forEach(c -> c.accept(duration));
        titleChange(player.getTitle());
    }

    /**
     * subscribe to scene change
     *
     * @param action method that will be performed after changing scene
     */
    public void subscribeSceneChange(Consumer<Boolean> action) {
        sceneChangeListeners.add(action);
    }

    /**
     * notify subscribers about scene change (window minimalization/maximalization)
     */
    public void sceneChange() {
        sceneChangeListeners.forEach(c -> c.accept(isPlayed));
    }

    /**
     * subscribe to title change
     *
     * @param action method that will be performed after changing the title
     */
    public void subscribeTitleChange(Consumer<String> action) {
        titleChangeListeners.add(action);
    }

    /**
     * notify subscribers about title change
     *
     * @param newTitle new title
     */
    public void titleChange(String newTitle) {
        titleChangeListeners.forEach(c -> c.accept(newTitle));
    }

    /**
     * Get player volume
     *
     * @return current volume
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Add song to the front of the queue
     *
     * @param song song to add
     */
    public void playAsNext(Song song) {
        player.playAsNext(song);
    }

    /**
     * Queue the song
     *
     * @param song requested song
     */
    public void queue(Song song) {
        player.queue(song);
    }

    /**
     * Get player queue
     *
     * @return queue
     */
    public List<Song> getQueue() {
        return player.getQueue();
    }

}
