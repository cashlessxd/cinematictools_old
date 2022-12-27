package backend;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VirtualControllerManager {
    final private String virtualControllerDirPath;

    public VirtualControllerManager(String virtualControllerDirPath) {
        this.virtualControllerDirPath = "\"" + virtualControllerDirPath + "\\VirtualController.exe\"";
    }

    public void initialize(Robot robot) throws IOException, InterruptedException {
        //this is a placeholder for a better solution in the future
        //TODO: change initialization method to Virtual Track commands (/run, /load, etc.) when /load bug is fixed

        //start Virtual Track application
        String command = virtualControllerDirPath;
        Runtime.getRuntime().exec(command);

        //wait until application opened
        TimeUnit.MILLISECONDS.sleep(100);

        //open profile selection
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_O);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_O);

        //select custom cinematictools profile
        String profileName = "cinematictools";
        char[] profileNameChars = profileName.toUpperCase().toCharArray();
        for (char profileNameChar : profileNameChars) {
            robot.keyPress(profileNameChar);
            robot.keyRelease(profileNameChar);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        //start Virtual Track
        robot.keyPress(KeyEvent.VK_F5);
        robot.keyRelease(KeyEvent.VK_F5);

        //wait until Virtual Track has started
        TimeUnit.MILLISECONDS.sleep(1000);

        //minimize window
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }

    public void kill() throws IOException {
        String command = "taskkill /F /IM VirtualController.exe";
        Runtime.getRuntime().exec(command);
    }

    public void moveLeftStick(Robot robot, int xPos, int yPos) throws InterruptedException {
        int xOffset = xPos - 50;
        int yOffset = yPos - 50;

        if (xOffset < 0) {
            //move left
            for (int i = 0; i < xOffset * -1; i++) {
                robot.keyPress(KeyEvent.VK_1);
                robot.keyRelease(KeyEvent.VK_1);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        } else {
            //move right
            for (int i = 0; i < xOffset; i++) {
                robot.keyPress(KeyEvent.VK_2);
                robot.keyRelease(KeyEvent.VK_2);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        }

        if (yOffset < 0) {
            //move down
            for (int i = 0; i < yOffset * -1; i++) {
                robot.keyPress(KeyEvent.VK_4);
                robot.keyRelease(KeyEvent.VK_4);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        } else {
            //move up
            for (int i = 0; i < yOffset; i++) {
                robot.keyPress(KeyEvent.VK_3);
                robot.keyRelease(KeyEvent.VK_3);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        }
    }

    public void moveRightStick(Robot robot, int xPos, int yPos) throws InterruptedException {
        int xOffset = xPos - 50;
        int yOffset = yPos - 50;

        if (xOffset < 0) {
            //move left
            for (int i = 0; i < xOffset * -1; i++) {
                robot.keyPress(KeyEvent.VK_NUMPAD1);
                robot.keyRelease(KeyEvent.VK_NUMPAD1);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        } else {
            //move right
            for (int i = 0; i < xOffset; i++) {
                robot.keyPress(KeyEvent.VK_NUMPAD2);
                robot.keyRelease(KeyEvent.VK_NUMPAD2);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        }

        if (yOffset < 0) {
            //move down
            for (int i = 0; i < yOffset * -1; i++) {
                robot.keyPress(KeyEvent.VK_NUMPAD4);
                robot.keyRelease(KeyEvent.VK_NUMPAD4);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        } else {
            //move up
            for (int i = 0; i < yOffset; i++) {
                robot.keyPress(KeyEvent.VK_NUMPAD3);
                robot.keyRelease(KeyEvent.VK_NUMPAD3);
                TimeUnit.MILLISECONDS.sleep(1);
            }
        }
    }

    public void resetLeftStick(Robot robot) {
        robot.keyPress(KeyEvent.VK_0);
        robot.keyRelease(KeyEvent.VK_0);
    }

    public void resetRightStick(Robot robot) {
        robot.keyPress(KeyEvent.VK_NUMPAD0);
        robot.keyRelease(KeyEvent.VK_NUMPAD0);
    }
}
