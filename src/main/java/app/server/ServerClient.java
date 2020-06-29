package app.server;

import app.playlist.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerClient {

    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final int bufforSize = 32768; // 2^15
    private final ExecutorService pool;

    public ServerClient(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        pool = Executors.newFixedThreadPool(1);
    }

    /**
     * Send song
     *
     * @param song song to send
     */
    public void send(Song song) {
        pool.submit(() -> sendFile(song.path()));
    }

    /**
     * write file to socket
     *
     * @param path path to file
     * @return true if write successfully, otherwise false
     */
    private boolean sendFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try (FileInputStream input = new FileInputStream(file)) {
                byte[] buffor = new byte[bufforSize];
                while (input.available() > 0) {
                    boolean finished = false;
                    int bytesReaded;
                    if ((bytesReaded = input.read(buffor)) < buffor.length) {
                        finished = true;
                        if (bytesReaded > 0) {
                            byte[] temp = new byte[bytesReaded];
                            System.arraycopy(buffor, 0, temp, 0, bytesReaded);
                            buffor = temp;
                        }
                    }
                    FilePayload payload = new FilePayload(buffor, finished);
                    outputStream.writeObject(payload);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private void task(String action, Optional<Float> progress) {
        String str;
        if (progress.isPresent()) {
            str = String.format("%s:%f", action.toUpperCase(), progress.get());
        } else {
            str = action.toUpperCase();
        }

        try {
            outputStream.writeChars(str);
        } catch (IOException e) {
        }
    }

    public void play(float progress) {
        pool.submit(() -> task("PLAY", Optional.of(progress)));
    }

    public void pause(float progress) {
        pool.submit(() -> task("PAUSE", Optional.of(progress)));
    }

    public void changeSong() {
        pool.submit(() -> task("CHANGE", Optional.empty()));
    }

    public void fileName(String fileName) {
        pool.submit(() -> {
            try {
                outputStream.writeChars("TITLE:" + fileName);
            } catch (Exception e) {
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerClient that = (ServerClient) o;
        return Objects.equals(socket, that.socket) &&
            Objects.equals(outputStream, that.outputStream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, outputStream);
    }

    public void disconnect() {
        try {
            outputStream.close();
            socket.close();
            pool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
