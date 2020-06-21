package app.playlist;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public class Queue extends Playlist {

    private final Deque<Song> history = new LinkedList<>();

    public Queue() {
        super("Queue");
    }

    /**
     * Shuffle queue
     */
    public void shuffle() {
        Collections.shuffle((List<?>) playlist);
    }

    /**
     * @return song which is played
     */
    public Song getCurrentSong() {
        return playlist.peek();
    }

    /**
     * If queue has next and add current played to history and play next song from queue
     * or play current
     *
     * @return song which will be played next
     */
    public Song next() {
        if (playlist.size() > 1) {
            history.offerLast(playlist.poll());
        }
        return getCurrentSong();
    }

    /**
     * Play previous played song based on history or play current if history is empty
     *
     * @return song which was played before
     */
    public Song previous() {
        if (history.size() > 0) {
            return history.removeLast();
        }
        return getCurrentSong();
    }

}
