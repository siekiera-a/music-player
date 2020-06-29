package app.settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Settings {
    private Path playlistDirectory;
    private Path musicDirectory;
    private Path settingsDirectory;

    public Settings() {
        Path path = Paths.get(System.getProperty("user.home"), "Music");
        settingsDirectory = Path.of(path.toString(), "App");

        if (!Files.isDirectory(settingsDirectory)) {
            try {
                Files.createDirectory(settingsDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path settingsFile = getSettingsPath();
        if (Files.exists(settingsFile)) {
            try {
                List<String> lines = Files.readAllLines(settingsFile);
                playlistDirectory = Path.of(lines.get(0));
                musicDirectory = Path.of(lines.get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            playlistDirectory = path;
            musicDirectory = path;
            save();
        }
    }

    /**
     * Changing path for searching music
     *
     * @param filePath for searching music
     */
    public void setMusicLocation(Path filePath) {
        if (Files.isDirectory(filePath)) {
            musicDirectory = filePath;
            save();
        }
    }

    /**
     * @return musicDirectory
     */
    public Path getMusicDirectory() {
        return musicDirectory;
    }

    /**
     * @return playlistDirectory
     */
    public Path getPlaylistDirectory() {
        return playlistDirectory;
    }

    /**
     * Changing path for saving playloist
     *
     * @param filePath for saving playlist
     */
    public void setPlaylistDirectory(Path filePath) {
        if (Files.isDirectory(filePath)) {
            playlistDirectory = filePath;
            save();
        }
    }

    /**
     * Saving settings to file
     */
    public void save() {
        try {
            Files.writeString(getSettingsPath(), String.format("%s\n%s\n", playlistDirectory, musicDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return path of settings file
     */
    public Path getSettingsPath() {
        return Path.of(settingsDirectory.toAbsolutePath().toString(), "settings.txt");
    }

    /**
     * @return path of statistics file
     */
    public Path getStatisticsPath() {
        return Path.of(settingsDirectory.toAbsolutePath().toString(), "statistics.txt");
    }
}
