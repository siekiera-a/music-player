package app.statistics;

import app.playlist.Song;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Statistics {

    private final LinkedList<Info> listenedSongs;
    private Path path = Paths.get(System.getProperty("user.home") + "\\Music");

    public Statistics() {
        listenedSongs = new LinkedList<>();
    }

    /**
     * Update listening statistics
     *
     * @param song which was played
     * @param time which the song was played
     */
    public void addTime(Song song, int time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Info info = new Info(song, time, formatter.format(date));

        listenedSongs.add(info);

        save(path);
    }

    /**
     * Saving statistics to file
     *
     * @param directory for saving statistics
     */
    public void save(Path directory) {
        File file = new File(String.valueOf(directory),  "statistics.txt");

        try (FileWriter outputFile = new FileWriter(file.getAbsoluteFile())) {
            for (Info i : listenedSongs) {
                outputFile.write(i.song().path() + "\t" + i.time() + "\t" + i.date() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}