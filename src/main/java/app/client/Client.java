package app.client;

import app.App;
import app.Store;
import app.playlist.Playlist;
import app.playlist.Song;
import app.server.FilePayload;
import app.settings.Settings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Client implements Runnable {

    private final Store store = App.getStore();
    private final Settings settings = store.getSettings();

    private final String host;
    private final int port;
    private Socket socket;
    private Runnable release;

    public Client(String host, int port, Runnable release) {
        this.host = host;
        this.port = port;
        this.release = release;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try (Socket socket = this.socket = new Socket(host, port);
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            while (true) {
                String action = (String) inputStream.readObject();
                String fileName = action.split(":")[1];
                Song song = downloadFile(inputStream, fileName);
                store.playPlaylist(new Playlist("Stream", List.of(song)));

                action = (String) inputStream.readObject();
                while (!action.startsWith("CHANGE")) {
                    takeAction(action);
                    action = (String) inputStream.readObject();
                }
                release.run();
            }
        } catch (Exception e) {
        }
    }

    private Song downloadFile(ObjectInputStream inputStream, String fileName) {
        if (!Files.isDirectory(settings.getTempDirectory())) {
            try {
                Files.createDirectory(settings.getTempDirectory());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path path = Path.of(settings.getTempDirectory().toAbsolutePath().toString(), fileName);

        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream stream = Files.newOutputStream(path)) {
            boolean finished = false;
            while (!finished) {
                FilePayload obj = (FilePayload) inputStream.readObject();
                finished = obj.finished();
                stream.write(obj.bytes());
            }
            return new Song(path.toAbsolutePath().toString());
        } catch (Exception e) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        }
    }

    private void takeAction(String action) {
        String[] data = action.split(":");
        float progress = 0.0f;
        try {
            progress = Float.parseFloat(data[1]);
        } catch (Exception ignore) {
        }
        switch (data[0]) {
            case "PLAY" -> store.play();
            case "PAUSE" -> store.pause();
        }
        store.seek(progress);
    }
}
