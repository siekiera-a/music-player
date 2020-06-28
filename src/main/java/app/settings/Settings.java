package app.settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    private Path saveDirectory;
    private Path musicDirectory;
    private final Path path = Paths.get(System.getProperty("user.home") + "\\Music");

    public Settings() {
        this.saveDirectory = path;
        this.musicDirectory = path;
        if (!Files.exists(Paths.get(path + "\\settings.txt"))) {
            save();
        }
    }

    /**
     * Changing path for searching music
     *
     * @param path for searching music
     */
    public void setMusicLocation(Path path) {
        if (isDirectory(path)) {
            musicDirectory = path;
            save();
        }
    }

    public Path getSaveDirectory() {
        return saveDirectory;
    }

    /**
     * Changing path for saving playloist
     *
     * @param path for saving playlist
     */
    public void setSaveDirectory(Path path) {
        if (isDirectory(path)) {
            saveDirectory = path;
            save();
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
     * Saving settings to file
     */
    public void save() {
        File file = new File(String.valueOf(path), "settings.txt");

        try (FileWriter outputFile = new FileWriter(file.getAbsoluteFile())) {
            outputFile.write((saveDirectory) + "\n");
            outputFile.write((musicDirectory) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
