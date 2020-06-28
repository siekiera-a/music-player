package app.server;

import app.playlist.Song;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private Thread thread;
    private boolean running;
    private int port;
    private final int maxConnectedDevices = 15;

    private List<ServerClient> clients;

    public Server(int port) {
        clients = new ArrayList<>();
        this.port = port;

        try {
            thread = new Thread(this);
            thread.start();
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        running = true;
        try (ServerSocket server = new ServerSocket(port)) {
            while (running) {
                Socket socket = server.accept();
                if (clients.size() < maxConnectedDevices) {
                    try {
                        ServerClient client = new ServerClient(socket);
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
     *
     * @param song song to send
     */
    public void send(Song song) {
        clients.forEach(client -> client.send(song));
    }

    public void stop() {
        thread.interrupt();
    }
}
