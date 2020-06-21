package app.playlist;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public record Song(String path) {

    private static final List<String> extensions = List.of("wav", "mp3");

    /**
     * Verify correct data
     * Throw exception if path is incorrect or extension is not supported
     *
     * @param path filepath of song
     */
    public Song {
        Path p = Paths.get(path);

        if (Files.exists(p) && Files.isRegularFile(p)) {

            String file = p.getFileName().toString();
            int index = file.lastIndexOf(".");
            String ext = file.substring(index + 1);

            if (extensions.contains(ext.toLowerCase())) {
                this.path = path;
            } else {
                throw new IllegalArgumentException("Extension not supported");
            }

        } else {
            throw new IllegalArgumentException("Wrong path");
        }

    }

    /**
     * @return song title based on path
     */
    public String getTitle() {
        Path p = Paths.get(path);
        String fileName = p.getFileName().toString();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(0, index);
    }

    /**
     * @return file extension based on path
     */
    public String getExtension() {
        Path p = Paths.get(path);
        String fileName = p.getFileName().toString();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

}

