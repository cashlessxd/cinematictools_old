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
    final private boolean testRunEnabled;
    final private boolean returnToStartEnabled;
    final private boolean refocusEnabled;
    final private String virtualControllerDirPath;

    public Track(int leftStickX, int leftStickY, int rightStickX, int rightStickY, char focusKey, int framesPerSecond, int coolDownDuration, int movementLength, int videoLength, boolean testRunEnabled, boolean returnToStartEnabled, boolean refocusEnabled, String virtualControllerDirPath) {
        this.leftStickX = leftStickX;
        this.leftStickY = leftStickY;
        this.rightStickX = rightStickX;
        this.rightStickY = rightStickY;
        this.focusKey = focusKey;
        this.coolDownDuration = coolDownDuration;
        this.framesPerSecond = framesPerSecond;
        this.movementLength = movementLength;
        this.videoLength = videoLength;
        this.testRunEnabled = testRunEnabled;
        this.returnToStartEnabled = returnToStartEnabled;
        this.refocusEnabled = refocusEnabled;
        this.virtualControllerDirPath = virtualControllerDirPath;
    }

    public void run() throws AWTException, IOException, InterruptedException {
        Robot virtualKeyboard = new Robot();
        VirtualControllerManager vcm = new VirtualControllerManager(virtualControllerDirPath);
        Camera camera = new Camera(virtualKeyboard, vcm);
        MediaManager mediaManager = new MediaManager(virtualKeyboard);

        vcm.initialize(virtualKeyboard);
        createVideo(camera, mediaManager);
        vcm.kill();
    }

    public void createVideo(Camera camera, MediaManager mediaManager) throws InterruptedException, IOException {
        int amountFrames = videoLength * framesPerSecond;

        if (testRunEnabled) {
            doTestRun(camera, amountFrames);
        }

        for (int i = 0; i < amountFrames; i++) {

            camera.moveSticks(leftStickX, leftStickY, rightStickX, rightStickY);
            TimeUnit.MILLISECONDS.sleep(movementLength);
            camera.resetSticks();

            if (refocusEnabled) {
                camera.focus(focusKey);
            }

            TimeUnit.MILLISECONDS.sleep(coolDownDuration);

            mediaManager.createScreenCapture();
        }

        if (returnToStartEnabled) {
            returnToStart(camera, amountFrames);
        }

        mediaManager.mergeFramesToVideo(framesPerSecond);
    }

    public void doTestRun(Camera camera, int amountFrames) throws InterruptedException {
        for (int i = 0; i < amountFrames; i++) {
            camera.moveSticks(leftStickX, leftStickY, rightStickX, rightStickY);
            TimeUnit.MILLISECONDS.sleep(movementLength);
            camera.resetSticks();
        }

        for (int i = 0; i < amountFrames; i++) {
            camera.moveSticks(invertStickPos(leftStickX), invertStickPos(leftStickY), invertStickPos(rightStickX), invertStickPos(rightStickY));
            TimeUnit.MILLISECONDS.sleep(movementLength);
            camera.resetSticks();
        }
    }

    public void returnToStart(Camera camera, int amountFrames) throws InterruptedException {
        for (int i = 0; i < amountFrames; i++) {
            camera.moveSticks(invertStickPos(leftStickX), invertStickPos(leftStickY), invertStickPos(rightStickX), invertStickPos(rightStickY));
            TimeUnit.MILLISECONDS.sleep(movementLength);
            camera.resetSticks();
        }
    }

    public int invertStickPos(int pos) {
        int offset = pos - 50;
        return 50 - offset;
    }
}


