package app.server;

import app.playlist.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

class ServerClient {

    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final int bufforSize = 32768;

    public ServerClient(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * Send song
     *
     * @param song song to send
     */
    public void send(Song song) {
        new Thread(() -> {
            if (sendFile(song.path())) {
                System.out.println("successfull!");
            } else {
                System.out.println("failed!");
            }
        }).start();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
