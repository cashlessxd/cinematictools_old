package starter;

import backend.Track;

import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws AWTException, IOException, InterruptedException {

        //keep in mind the default stick deadzone is between 39-61
        int leftStickX = 50;
        int leftStickY = 50;
        int rightStickX = 50;
        int rightStickY = 50;

        char focusKey = 'X';

        int coolDownDuration = 2000; //in milliseconds;
        int movementLength = 100; //in milliseconds;
        int framesPerSecond = 25;
        int videoLength = 1; //in seconds

        boolean testRunEnabled = true; //not 100% accurate
        boolean returnToStartEnabled = true; //not 100% accurate
        boolean refocusEnabled = false;

        String virtualControllerDirPath = "C:\\Program Files\\Virtual Controller";

        Track track = new Track(leftStickX, leftStickY, rightStickX, rightStickY, focusKey, framesPerSecond, coolDownDuration, movementLength, videoLength, testRunEnabled, returnToStartEnabled, refocusEnabled, virtualControllerDirPath);
        track.run();
    }
}