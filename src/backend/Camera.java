package backend;

import java.awt.*;

public class Camera {

    final private Robot virtualKeyboard;
    final private VirtualControllerManager vcm;

    public Camera(Robot virtualKeyboard, VirtualControllerManager vcm) {
        this.virtualKeyboard = virtualKeyboard;
        this.vcm = vcm;
    }

    public void moveSticks(int leftStickX , int leftStickY , int rightStickX , int rightStickY, int movementLength) throws InterruptedException {
        vcm.moveLeftStick(virtualKeyboard, leftStickX, leftStickY);
        vcm.moveRightStick(virtualKeyboard, rightStickX, rightStickY);
    }

    public void resetSticks() {
        vcm.resetLeftStick(virtualKeyboard);
        vcm.resetRightStick(virtualKeyboard);
    }

    public void focus(Character focusKey) {
        virtualKeyboard.keyPress(focusKey);
        virtualKeyboard.keyRelease(focusKey);
    }
}
