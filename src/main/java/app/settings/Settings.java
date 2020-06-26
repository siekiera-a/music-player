package app.settings;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    private String password;
    private Path saveDirectory;
    private Path playlistLocation;

    public Settings() {
        Path path = Paths.get(System.getProperty("user.home") + "\\Music");
        this.saveDirectory = path;
        this.playlistLocation = path;
    }

    /**
     * Changing path for searching music
     *
     * @param path for searching music
     */
    public void setPlaylistLocations(Path path) {
        if (isDirectory(path)) {
            playlistLocation = path;
        }
    }

    /**
     * Changing path for saving playloist
     *
     * @param path for saving playlist
     */
    public void setSaveDirectory(Path path) {
        if (isDirectory(path)) {
            saveDirectory = path;
        }
    }

    /**
     * @param path for save
     * @return true if path is a directory otherwise return false
     */
    public static boolean isDirectory(Path path) {
        if (path == null || !Files.exists(path)) {
            return false;
        } else return Files.isDirectory(path);
    }

    /**
     * Setting password
     *
     * @param password for private room
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return path for autostart in Windows
     */
    public static String getAutoStart() {
        return System.getProperty("java.io.tmpdir").replace("Local\\Temp\\", "Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
    }

    public static String getRunningDir() {
        String runningdir = Paths.get(".").toAbsolutePath().normalize().toString();
        runningdir += "\\target";       //dopisac cala lokalizacje artefaktow
         System.out.println(runningdir);

        return runningdir;
    }

    public void autostart() {
        Path autostartPath = Paths.get(getAutoStart());
        Path currentPath = Paths.get(getRunningDir());
        try {
            Path path = Path.of(Settings.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            System.out.println(path.toAbsolutePath().toString());
//            Files.createSymbolicLink(path, )
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        try {
//            Files.move(currentPath.resolve("ania.txt"), autostartPath.resolve("ania.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
