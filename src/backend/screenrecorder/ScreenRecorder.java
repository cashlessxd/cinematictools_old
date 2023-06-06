package backend.screenrecorder;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

public class ScreenRecorder {

    Rectangle screenRegion;
    Robot robot;
    double screenHight;
    double screenWidth;
    FrameBuffer frameBuffer;


    public ScreenRecorder() throws AWTException {
        this.frameBuffer = new FrameBuffer();

        GraphicsDevice defaultScreen = getDefaultScreen();
        this.robot = new Robot( defaultScreen );
        this.screenHight = defaultScreen.getDisplayMode().getHeight();
        this.screenWidth = defaultScreen.getDisplayMode().getWidth();

        this.screenRegion = new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() );
    }


    public GraphicsDevice getDefaultScreen() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }


    public void captureAndAddFrame() {

        frameBuffer.add();
    }

}
