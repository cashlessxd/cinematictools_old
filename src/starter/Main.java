package starter;

import backend.Controller;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws AWTException {
        Character movementKey = 'W';
        Character focusKey = 'X';
        int keyPressedDuration = 20; //in milliseconds
        int cooldownDuration = 3000; //in milliseconds
        int framesPerSecond = 25;
        int videoLength = 5; //in seconds

        Controller controller = new Controller(movementKey, focusKey, keyPressedDuration, cooldownDuration, framesPerSecond, videoLength);
        controller.run();
    }
}