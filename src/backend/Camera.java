package backend;

import java.awt.*;

public class Camera {

    public void move(Robot robot, Character movementKey, int keyPressedDuration, int frameNumber) {
        robot.keyPress(movementKey);
        robot.delay(keyPressedDuration);
        robot.keyRelease(movementKey);
        System.out.println("moved camera in frame: " + (frameNumber));
    }

    public void focus(Robot robot, Character focusKey, int frameNumber) {
        robot.keyPress(focusKey);
        robot.keyRelease(focusKey);
        System.out.println("focused camera in frame: " + (frameNumber));
    }
}
