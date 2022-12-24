package backend;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Controller {

    private final Character movementKey;
    private final Character focusKey;
    private final int keyPressedDuration; //in milliseconds
    private final int cooldownDuration; //in milliseconds
    private final int framesPerSecond;
    private final int amountFrames;

    public Controller(Character movementKey, Character focusKey, int keyPressedDuration, int cooldownDuration, int framesPerSecond, int videoLength) {
        this.movementKey = movementKey;
        this.focusKey = focusKey;
        this.keyPressedDuration = keyPressedDuration;
        this. cooldownDuration = cooldownDuration;
        this.framesPerSecond = framesPerSecond;
        this.amountFrames = videoLength * framesPerSecond;
    }

    public void run() throws AWTException {
        Camera camera = new Camera();
        MediaManager mediaManager = new MediaManager();
        Robot robot = new Robot();

        try {
            for (int i = 0; i < amountFrames; i++) {
                //move camera
                camera.move(robot, movementKey, keyPressedDuration, i + 1);

                //focus camera
                camera.focus(robot, focusKey, i + 1);

                //cooldowm
                TimeUnit.MILLISECONDS.sleep(cooldownDuration);

                //take screen capture
                mediaManager.createScreenCapture(robot, i + 1);
            }

            mediaManager.mergeFramesToVideo(framesPerSecond);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}


