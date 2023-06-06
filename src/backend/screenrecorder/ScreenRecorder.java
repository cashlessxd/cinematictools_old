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
    double screenHeight;
    double screenWidth;
    FrameBuffer frameBuffer;


    public ScreenRecorder() throws AWTException {
        this.frameBuffer = new FrameBuffer();

        GraphicsDevice defaultScreen = getDefaultScreen();
        this.robot = new Robot( defaultScreen );
        this.screenHeight = getScreenHeight( defaultScreen );
        this.screenWidth = getScreenWidth( defaultScreen );

        this.screenRegion = new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() );
    }


    private GraphicsDevice getDefaultScreen() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    private double getScreenHeight(GraphicsDevice screen) {
        return screen.getDisplayMode().getHeight();
    }

    private double getScreenWidth(GraphicsDevice screen) {
        return screen.getDisplayMode().getWidth();
    }


    public void captureAndAddFrame() {
    }

}
