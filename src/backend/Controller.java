package backend;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Controller {

    private Character movementKey = 'W';
    private Character focusKey = 'X';

    private int keyPressedDuration = 20; //in ms (20ms recommended for FH5)
    private int cooldownDuration = 3000; //in ms
    private int framesPerSecond = 25;
    private int amountFrames = 50;

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


