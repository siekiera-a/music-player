package app.player;


import java.time.Duration;

public class Stoper {

    private long lastTick;
    private long calculatedTime;

    /**
     * start/resume timer
     */
    public void start() {
        lastTick = System.currentTimeMillis();
    }

    /**
     * pause timer
     */
    public void pause() {
        if (lastTick > 0) {
            long currentTime = System.currentTimeMillis();
            calculatedTime += currentTime - lastTick;
            lastTick = currentTime;
        }
    }

    /**
     * stop timer and reset stopper
     *
     * @return calculated time
     */
    public Duration stop() {
        pause();
        Duration duration = Duration.ofMillis(calculatedTime);
        lastTick = 0;
        calculatedTime = 0;
        return duration;
    }

}
