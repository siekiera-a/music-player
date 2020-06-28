package app.playlist;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public class Queue extends Playlist {

    private final Deque<Song> history = new LinkedList<>();
//    private boolean prev;

    public Queue() {
        super("Queue");
    }

    /**
     * Shuffle queue
     */
    public void shuffle() {
        Song currentSong = playlist.remove();
        Collections.shuffle((List<?>) playlist);
        addFirst(currentSong);
    }

    /**
     * @return song which is played or null if queue is empty
     */
    public Song getCurrentSong() {
        return playlist.peek();
    }

    /**
     * Ad current played song to history and play next song from queue
     *
     * @return song which will be played next or null if queue is empty
     */
    public Song next() {
        if (playlist.size() > 0) {
            history.offerLast(playlist.poll());
        }
//        prev = false;
        return getCurrentSong();
    }

    /**
     * Add current played song to the end of history.
     * Play previous song based on history.
     *
     * @return song which was played before
     */
    public Song previous() {
        if (history.size() > 0) {
            return history.removeLast();
        }
        return getCurrentSong();
    }


//    public Song previous() {
//        if (history.size() > 0) {
//            Song prevSong = history.peekLast();
//            if (!prev) {
//                history.offerLast(getCurrentSong());
//                prev = true;
//            } else {
//                history.offerLast(history.peekLast());
//            }
//
//            return prevSong;
//        }
//        return getCurrentSong();
//    }

}
