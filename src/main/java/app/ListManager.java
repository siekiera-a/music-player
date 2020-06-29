package app;

import app.playlist.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ListManager {
    ObservableList<String> playlistData = FXCollections.observableArrayList();
    ObservableList<String> searchedData = FXCollections.observableArrayList();

    public ListView<String> playlistView;
    public TextArea playlistSearch;

    private final Store store = App.getStore();

    String filePah = Paths.get(System.getProperty("user.home") + "\\Music\\App") + "\\statistics.txt";

    ListManager(ListView<String> playlistView, TextArea playlistSearch) {
        this.playlistView = playlistView;
        this.playlistSearch = playlistSearch;

        playlistView.setCellFactory(x -> new CustomCell());
        setSearchListener();
    }

    public void setList(ObservableList<String> newPlaylist) {
        playlistData = newPlaylist;
        playlistView.setItems(playlistData);
    }

    protected void setSearchListener() {
        playlistSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                playlistView.setItems(playlistData);
            } else {
                ObservableList<String> items = FXCollections.observableArrayList();
                for (String x : playlistData) {
                    if (x.contains(newValue)) {
                        items.add(x);
                    }
                }
                searchedData = items;
                playlistView.setItems(searchedData);
            }
        });
    }

    /**
     * Set current day listened songs in option 'Recently Played' in statistics
     */
    public void currentDaySongs() {
        playlistData.clear();
        if (Files.exists(Paths.get(filePah))) {
            Map<Song, Integer> sortedByCount = sortByValue(getFewDaysListened(0));
            showStatistics(sortedByCount);
        }
    }

    /**
     * Set last week listened songs in option 'Last Week Played' in statistics
     */
    public void lastWeekSongs() {
        playlistData.clear();
        if (Files.exists(Paths.get(filePah))) {
            Map<Song, Integer> sortedByCount = sortByValue(getFewDaysListened(-6));
            showStatistics(sortedByCount);
        }
    }

    /**
     * Set the least played songs in option 'Last Played Songs' in statistics
     */
    public void leastPlayed() {
        playlistData.clear();
        if (Files.exists(Paths.get(filePah))) {
            Map<Song, Integer> sortedByCount = reverseOrder(getCountedSongs());
            showStatistics(sortedByCount);
        }
    }

    /**
     * Set the most played songs in option 'Generally Played Songs' in statistics
     */
    public void mostPlayed() {
        playlistData.clear();
        if (Files.exists(Paths.get(filePah))) {
            Map<Song, Integer> sortedByCount = sortByValue(getCountedSongs());
            showStatistics(sortedByCount);
        }
    }

    /**
     * Set list of statistics on screen
     *
     * @param sortedByCount sorted map
     */
    private void showStatistics(Map<Song, Integer> sortedByCount) {
        sortedByCount.forEach((key, value) -> playlistData.add(key.getTitle()));
        playlistView.setItems(playlistData);
    }

    /**
     * Get played songs from file and count total time of listening for each song in the last few days
     *
     * @param number of last days to statictics
     * @return hashmap, which contains songs and total time of listening
     */
    public Map<Song, Integer> getFewDaysListened(int number) {
        Map<Song, Integer> listenedSongs = new HashMap<>();
        try {
            var lines = Files.readAllLines(Path.of(filePah));
            var parser = new SimpleDateFormat("dd/MM/yyyy");

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, number);
            Date calDate = calendar.getTime();
            String format = parser.format(calDate);
            Date currentDay = null;
            try {
                currentDay = parser.parse(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date finalCurrentDay = currentDay;

            lines.forEach(line -> {
                String[] data = line.split("\t");
                String path = data[0];
                int time = Integer.parseInt(data[1]);
                Date date = null;
                try {
                    date = parser.parse(data[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Song song = new Song(path);

                if (date != null && finalCurrentDay != null && (date.after(finalCurrentDay) || date.equals(finalCurrentDay))) {
                    if (!listenedSongs.containsKey(song)) {
                        listenedSongs.put(song, time);
                    } else {
                        for (var e : listenedSongs.entrySet()) {
                            if (e.getKey().equals(song)) {
                                Integer value = e.getValue();
                                value += time;
                                listenedSongs.put(song, value);
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listenedSongs;
    }

    /**
     * Get played songs from file and count the number of occurrences of the songs
     *
     * @return hashmap, which contains songs and number of repetitions
     */
    public Map<Song, Integer> getCountedSongs() {
        Map<Song, Integer> mostListened = new HashMap<>();
        try {
            var lines = Files.readAllLines(Path.of(filePah));

            lines.forEach(line -> {
                String[] data = line.split("\t");
                String path = data[0];

                Song song = new Song(path);

                if (!mostListened.containsKey(song)) {
                    mostListened.put(song, 1);
                } else {
                    for (var e : mostListened.entrySet()) {
                        if (e.getKey().equals(song)) {
                            Integer value = e.getValue();
                            value++;
                            mostListened.put(song, value);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return mostListened;
    }

    /**
     * Sort map form largest to smallest
     *
     * @param map which will be sorted
     * @return sorted map
     */
    public Map<Song, Integer> sortByValue(Map<Song, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted((Map.Entry.<Song, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Sort map from smallest to largest
     *
     * @param map which will be sorted
     * @return sorted map
     */
    public Map<Song, Integer> reverseOrder(Map<Song, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted((Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}