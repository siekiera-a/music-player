package app.server;

import app.playlist.Song;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Server implements Runnable {

    private boolean running;
    private int port;
    private final int maxConnectedDevices = 15;
    private boolean isPlayed;

    private final Supplier<Float> getProgress;

    private Song song;
    private List<ServerClient> clients;
    private ServerSocket server;

    public Server(int port, Song song, Supplier<Float> getProgress) {
        this.song = song;
        this.getProgress = getProgress;
        clients = new ArrayList<>();
        this.port = port;

        new Thread(this).start();
    }

    @Override
    public void run() {
        running = true;
        try (ServerSocket server = this.server = new ServerSocket(port)) {
            while (running) {
                Socket socket = server.accept();

                clients.removeAll(
                    clients.stream()
                        .filter(ServerClient::isDisconnected)
                        .collect(Collectors.toList()));

                if (clients.size() < maxConnectedDevices) {
                    try {
                        ServerClient client = new ServerClient(socket);
                        client.fileName(song.getTitle() + "." + song.getExtension());
                        client.send(song);
                        if (isPlayed) {
                            client.play(getProgress.get());
                        } else {
                            client.pause(getProgress.get());
                        }
                        clients.add(client);
                    } catch (IOException ignore) {
                        socket.close();
                    }
                } else {
                    socket.close();
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * send song to all connected clients
     */
    public void send() {
        clients.forEach(client -> client.send(song));
    }

    public void play(float progress) {
        clients.forEach(client -> client.play(progress));
    }

    public void pause(float progress) {
        clients.forEach(client -> client.pause(progress));
    }

    public void changeSong(Song song) {
        this.song = song;
        clients.forEach(ServerClient::changeSong);
    }

    public void fileName(String fileName) {
        clients.forEach(c -> c.fileName(fileName));
    }

    public void stop() {
        try {
            server.close();
        } catch (IOException e) {
        }
        clients.forEach(ServerClient::disconnect);
    }

    public void setIsPlayed(boolean value) {
        isPlayed = value;
    }

}
