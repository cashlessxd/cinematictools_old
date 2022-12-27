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
    final private int videoLength;
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
        this.videoLength = videoLength;
        this.virtualControllerDirPath = virtualControllerDirPath;
    }

    public void run() throws AWTException, IOException, InterruptedException {
        Robot virtualKeyboard = new Robot();
        VirtualControllerManager vcm = new VirtualControllerManager(virtualControllerDirPath);
        Camera camera = new Camera(virtualKeyboard, vcm);
        MediaManager mediaManager = new MediaManager(virtualKeyboard);

        vcm.initialize(virtualKeyboard);

        doTestRun(camera);

        createVideo(camera, mediaManager);

        vcm.kill();
    }

    public void doTestRun(Camera camera) throws InterruptedException {
        camera.moveSticks(leftStickX, leftStickY, rightStickX, rightStickY, movementLength);
        TimeUnit.SECONDS.sleep(videoLength);
        camera.resetSticks();
        camera.moveSticks(invertStickPos(leftStickX), invertStickPos(leftStickY), invertStickPos(rightStickX), invertStickPos(rightStickY), movementLength);
        TimeUnit.SECONDS.sleep(videoLength);
        camera.resetSticks();
    }

    public void createVideo(Camera camera, MediaManager mediaManager) throws InterruptedException, IOException {
        int amountFrames = videoLength * framesPerSecond;
        for (int i = 0; i < amountFrames; i++) {
            camera.moveSticks(leftStickX, leftStickY, rightStickX, rightStickY, movementLength);
            TimeUnit.MILLISECONDS.sleep(movementLength);
            camera.resetSticks();
            camera.focus(focusKey);
            TimeUnit.MILLISECONDS.sleep(coolDownDuration);
            mediaManager.createScreenCapture();
        }

        mediaManager.mergeFramesToVideo(framesPerSecond);
    }

    public int invertStickPos(int pos) {
        int offset = pos - 50;
        return 50 - offset;
    }
}


