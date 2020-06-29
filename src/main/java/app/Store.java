package app;

import app.client.Client;
import app.player.LocalPlayer;
import app.playlist.Playlist;
import app.playlist.Song;
import app.server.Server;
import app.settings.Settings;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Store {

    private final LocalPlayer player = new LocalPlayer();

    private final Set<Consumer<Duration>> timeChangeListeners;
    private final Set<Consumer<Duration>> audioLoadedListeners;
    private final Set<Consumer<Boolean>> sceneChangeListeners;
    private final Set<Consumer<String>> titleChangeListeners;
    private final Set<Consumer<Playlist>> queueChangeListeners;
    private final List<Playlist> playlists;
    private final Settings settings;

    private int volume;
    private boolean isPlayed;
    private int currentTime;

    private Server server;
    private final int port = 21370;
    private Client client;

    public Store() {
        volume = (int) (player.getVolume() * 100);
        timeChangeListeners = new HashSet<>();
        sceneChangeListeners = new HashSet<>();
        titleChangeListeners = new HashSet<>();
        audioLoadedListeners = new HashSet<>();
        queueChangeListeners = new HashSet<>();
        settings = new Settings();
        playlists = new ArrayList<>();

        player.setOnPlaying(this::timeChange);
        player.setOnAudioLoaded(this::audioLoaded);

        loadPlaylists();

        player.changePlaylist(new Playlist("xd", List.of(
            new Song("Bet My Heart.mp3"),
            new Song("Visions.mp3"),
            new Song("This Love.mp3")
        )));
    }

    private void loadPlaylists() {
        try (Stream<Path> files = Files.walk(settings.getPlaylistDirectory(), 1)) {
            files.filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(".txt"))
                .forEach(path -> {
                    String file = path.getFileName().toString();
                    String playlistName = file.substring(0, file.lastIndexOf("."));
                    try {
                        List<String> lines = Files.readAllLines(path);
                        List<Song> songs = new ArrayList<>();
                        lines.forEach(line -> {
                            try {
                                Song song = new Song(line);
                                songs.add(song);
                            } catch (IllegalArgumentException ignored) {
                            }
                        });
                        Playlist playlist = new Playlist(playlistName, songs);
                        playlists.add(playlist);
                    } catch (IOException e) {
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * play if not playing, pause if playing
     */
    public void playPause() {
        isPlayed = !isPlayed;
        if (server != null) {
            server.setIsPlayed(isPlayed);
        }
        if (isPlayed) {
            player.play();
            if (isStreaming()) {
                server.play(player.getProgress());
            }
        } else {
            player.pause();
            if (isStreaming()) {
                server.pause(player.getProgress());
            }
        }
    }

    public void play() {
        isPlayed = true;
        player.play();
        Platform.runLater(() -> sceneChange());
    }

    public void pause() {
        isPlayed = false;
        player.pause();
        Platform.runLater(() -> sceneChange());
    }

    public void seek(float progress) {
        player.seek(progress);
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
        player.toggleLoop();
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
        queueChange(new Playlist("Queue", player.getQueue()));

        if (isStreaming()) {
            Song song = player.getCurrentSong();
            server.changeSong(song);
            server.fileName(song.getTitle() + "." + song.getExtension());
            server.send();
        }
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
    private void titleChange(String newTitle) {
        titleChangeListeners.forEach(c -> c.accept(newTitle));
    }

    /**
     * subscribe to title change
     *
     * @param action method that will be performed after queue update
     */
    public void subscribeQueueChange(Consumer<Playlist> action) {
        queueChangeListeners.add(action);
    }

    private void queueChange(Playlist playlist) {
        queueChangeListeners.forEach(c -> c.accept(playlist));
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

    /**
     * Create playlist
     *
     * @param name of playlist
     */
    public void createPlaylist(String name, String path) {
        playlists.add(new Playlist(name, Collections.singleton(new Song(path))));
        playlists.forEach(p -> p.save(settings.getPlaylistDirectory()));
    }

    /**
     * @return list of playlists
     */
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * @param name playlist's name
     * @return playlist or null if playlist do not exist
     */
    public Playlist getPlaylist(String name) {
        Optional<Playlist> playlist = playlists
            .stream()
            .filter(p -> p.getName().equals(name))
            .findFirst();

        return playlist.orElse(null);
    }

    /**
     * @return settings
     */
    public Settings getSettings() {
        return settings;
    }

    public void startStream() {
        if (server == null) {
            server = new Server(port, player.getCurrentSong(), player::getProgress);
        }
    }

    public void disconnect() {
        if (server != null) {
            server.stop();
            server = null;
        }
        if (client != null) {
            client.disconnect();
            client = null;
        }
    }

    private boolean isStreaming() {
        return server != null;
    }

    public void playPlaylist(Playlist playlist) {
        player.changePlaylist(playlist);
    }

    public void release() {
        player.stop();
        disconnect();
    }

    public void connect(String address) {
        client = new Client(address, port, player::stop);
    }

}
