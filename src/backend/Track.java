package backend;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Track {

    final private int leftStickX;
    final private int leftStickY;
    final private int rightStickX;
    final private int rightStickY;
    final private char focusKey;
    final private int coolDownDuration;
    final private int movementLength;
    final private int framesPerSecond;
    final private int amountFrames;
    final private String virtualControllerDirPath;

    public Track(int leftStickX , int leftStickY , int rightStickX , int rightStickY, char focusKey, int framesPerSecond, int coolDownDuration, int movementLength, int videoLength, String virtualControllerDirPath) {
        this.leftStickX = leftStickX;
        this.leftStickY = leftStickY;
        this.rightStickX = rightStickX;
        this.rightStickY = rightStickY;
        this.focusKey = focusKey;
        this.coolDownDuration = coolDownDuration;
        this.framesPerSecond = framesPerSecond;
        this.movementLength = movementLength;
        this.amountFrames = videoLength * framesPerSecond;
        this.virtualControllerDirPath = virtualControllerDirPath;
    }

    public void run() throws AWTException, IOException, InterruptedException {
        Robot virtualKeyboard = new Robot();
        VirtualControllerManager vcm = new VirtualControllerManager(virtualControllerDirPath);
        Camera camera = new Camera(virtualKeyboard, vcm);
        MediaManager mediaManager = new MediaManager(virtualKeyboard);

        vcm.initialize(virtualKeyboard);

        try {
            for (int i = 0; i < amountFrames; i++) {
                camera.move(leftStickX, leftStickY, rightStickX, rightStickY, movementLength);
                camera.focus(focusKey);
                TimeUnit.MILLISECONDS.sleep(coolDownDuration);
                mediaManager.createScreenCapture();
            }

            mediaManager.mergeFramesToVideo(framesPerSecond);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        vcm.kill();
    }
}


