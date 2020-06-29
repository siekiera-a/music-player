package app.statistics;

import app.App;
import app.playlist.Song;
import app.settings.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Statistics {

    private final LinkedList<Info> listenedSongs;
    private final Settings settings = App.getStore().getSettings();

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Info info = new Info(song, time, formatter.format(date));
        listenedSongs.add(info);
        save();
    }

    /**
     * Saving statistics to file
     */
    public void save() {
        String str = listenedSongs.stream()
                .map(i -> String.format("%s\t%d\t%s", i.song().path(), i.time(), i.date()))
                .collect(Collectors.joining("\n"));

        try {
            Files.writeString(settings.getStatisticsPath(), str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}