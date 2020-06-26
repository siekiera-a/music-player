package app.playlist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Playlist {
    private String name;
    protected Queue<Song> playlist = new LinkedList<>();

    /**
     * Create a new playlist based on song's collection
     *
     * @param name  name of playlist
     * @param songs song's collection
     */
    public Playlist(String name, Collection<Song> songs) {
        this.name = name;
        if (songs != null)
            playlist.addAll(songs);
    }

    /**
     * Create a new empty playlist
     *
     * @param name name of playlist
     */
    public Playlist(String name) {
        this.name = name;
    }

    /**
     * Add song to playlist
     *
     * @param song song which is add to playlist
     */
    public void add(Song song) {
        playlist.offer(song);
    }

    /**
     * Add songs collection to playlist
     *
     * @param songs song's collection
     */
    public void add(Collection<Song> songs) {
        if (songs != null) {
            playlist.addAll(songs);
        }
    }

    /**
     * Remove song from playlist
     *
     * @param song song which is being removed
     */
    public void remove(Song song) {
        playlist.remove(song);
    }

    /**
     * Remove songs collection from playlist
     *
     * @param songs collection of songs to removed
     */
    public void remove(Collection<Song> songs) {
        if (songs != null) {
            playlist.removeAll(songs);
        }
    }

    /**
     * Remove playlist
     */
    public void remove() {
        String directory = System.getProperty("user.home") + "\\Music";
        File file = new File(directory, this.name + ".txt");

        file.delete();
    }

    /**
     * Rename playlist
     *
     * @param name new playlist's name
     */
    public void changeName(String name) {
        String directory = System.getProperty("user.home") + "\\Music";
        File oldFile = new File(directory, this.name + ".txt");
        File newFile = new File(directory, name + ".txt");

        boolean change = oldFile.renameTo(newFile);
        if (change) {
            this.name = name;
        }
    }

    /**
     * @param i position on playlist
     * @return true if playlist contains position, otherwise false
     */
    public boolean isInPlaylist(int i) {
        return i >= 0 && i < playlist.size();
    }

    /**
     * Changing two songs position
     *
     * @param i position of first song to swap
     * @param j position of second song to swap
     */
    public void swapElement(int i, int j) {
        if (isInPlaylist(i) && isInPlaylist(j)) {
            Collections.swap((List<?>) playlist, i, j);
        }
    }

    /**
     * Save playlist in file
     */
    public void save() {
        String directory = System.getProperty("user.home") + "\\Music";
        File file = new File(directory, this.name + ".txt");

        try (FileWriter outputFile = new FileWriter(file.getAbsoluteFile())) {
            for (Song p : playlist) {
                outputFile.write(p.path() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clear() {
        playlist.clear();
    }

    public List<Song> getSongs() {
        return List.copyOf(playlist);
    }

}
