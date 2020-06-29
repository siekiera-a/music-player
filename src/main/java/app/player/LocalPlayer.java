package app.player;

import app.playlist.Playlist;
import app.playlist.Queue;
import app.playlist.Song;
import app.statistics.Statistics;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class LocalPlayer {

    private MediaPlayer player;
    private final Queue queue;

    private Song currentSong;

    private float volume;
    private boolean loop;
    private boolean isPlayed;

    private Consumer<Duration> onPlaying;
    private Consumer<Duration> onLoaded;

    private Stopwatch stopwatch = new Stopwatch();
    private Statistics statistics;

    public LocalPlayer() {
        this.queue = new Queue();
        volume = 1.0f;
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            statistics = new Statistics();
        }).start();
    }

    /**
     * Stop current playing song and change to the new one
     *
     * @param song new song
     */
    private void changeSong(Song song) {
        stop();
        try {
            java.time.Duration duration = stopwatch.stop();
            long seconds = duration.toSeconds();
            if (seconds > 0) {
                statistics.addTime(currentSong, (int) seconds);
            }
            currentSong = song;
            stopwatch.start();
            File file = new File(song.path());
            Media media = new Media(file.toURI().toString());
            player = new MediaPlayer(media);
            setPlayerProperties();
        } catch (Exception e) {
            currentSong = null;
            player = null;
        }
    }

    /**
     * Apply settings for the changed song
     */
    private void setPlayerProperties() {
        player.setVolume(volume);

        int cycleCount = loop ? Integer.MAX_VALUE : 1;
        player.setCycleCount(cycleCount);

        // check this
        player.setAutoPlay(isPlayed);

        setOnPlaying(onPlaying);
        setOnAudioLoaded(onLoaded);
    }

    /**
     * play current song
     */
    public void play() {
        if (player != null) {
            isPlayed = true;
            player.play();
            stopwatch.start();
        }
    }

    /**
     * set song progress
     *
     * @param progress value in range [0.0, 1.0]
     * @throws IllegalArgumentException if progress is out of the range
     */
    public void seek(float progress) {
        if (progress < 0.0f || progress > 1.0f) {
            throw new IllegalArgumentException("Progress should be between 0.0-1.0 inclusive!");
        }

        if (player == null) {
            return;
        }

        Duration duration = player.getCycleDuration();
        player.seek(duration.multiply(progress));
    }

    /**
     * rewind current song
     */
    public void rewind() {
        seek(0.0f);
    }

    /**
     * shuffle queue
     */
    public void shuffle() {
        queue.shuffle();
    }

    /**
     * set player volume
     *
     * @param newVolume value in range [0.0, 1.0]
     * @throws IllegalArgumentException if value is out of the range
     */
    public void changeVolume(float newVolume) {
        if (newVolume < 0.0f || newVolume > 1.0f) {
            throw new IllegalArgumentException("Volume should be between 0.0-1.0 inclusive!");
        }

        volume = newVolume;
        if (player != null) {
            player.setVolume(volume);
        }
    }

    /**
     * Get player volume
     *
     * @return current volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * toggle loop
     *
     * @return new loop value
     */
    public boolean toggleLoop() {
        loop = !loop;

        if (player != null) {
            int cycleCount = loop ? Integer.MAX_VALUE : 1;
            player.setCycleCount(cycleCount);
        }

        return loop;
    }

    public void next() {
        Song next = queue.next();
        if (next != null && !next.equals(currentSong)) {
            changeSong(next);
        }
    }

    public void previous() {
        Song previous = queue.previous();
        if (previous != null && !previous.equals(currentSong)) {
            changeSong(previous);
        }
    }

    /**
     * pause current song
     */
    public void pause() {
        if (player != null) {
            isPlayed = false;
            player.pause();
            stopwatch.pause();
        }
    }

    /**
     * stop current song and release associeted resources
     */
    public void stop() {
        if (player != null) {
            player.dispose();
        }
    }

    public void changePlaylist(Playlist playlist) {
        if (playlist != null) {
            queue.clear();
            queue.add(playlist.getSongs());
            changeSong(queue.getCurrentSong());
        }
    }

    /**
     * add song to queue
     *
     * @param song
     */
    public void queue(Song song) {
        queue.add(song);
    }

    public void playAsNext(Song song) {
        queue.addFirst(song);
    }

    public void setOnPlaying(Consumer<Duration> onPlaying) {
        if (onPlaying != null) {
            this.onPlaying = onPlaying;
            if (player != null) {
                player.currentTimeProperty().addListener(
                    ((observable, oldValue, newValue) -> onPlaying.accept(newValue)));
            }
        }
    }

    public void setOnAudioLoaded(Consumer<Duration> onLoaded) {
        if (onLoaded != null) {
            this.onLoaded = onLoaded;
            if (player != null) {
                player.setOnReady(() -> onLoaded.accept(player.getCycleDuration()));
            }
        }
    }

    public String getTitle() {
        return currentSong != null ? currentSong.getTitle() : "";
    }

    public List<Song> getQueue() {
        List<Song> songs = queue.getSongs();
        if (songs.size() > 1) {
            return songs.subList(1, songs.size());
        } else {
            return new LinkedList<>();
        }
    }

    public float getProgress() {
        if (player == null) {
            return 0.0f;
        }

        return (float) (player.getCurrentTime().toSeconds() / player.getCycleDuration().toSeconds());
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}
