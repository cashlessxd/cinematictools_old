package backend.screenrecorder;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

public class ScreenRecorder {

    private final Rectangle screenRegion;
    private Robot robot;
    private double screenHeight;
    private double screenWidth;
    private FrameBuffer frameBuffer;


    public ScreenRecorder() throws AWTException {
        this.frameBuffer = new FrameBuffer();
        GraphicsDevice defaultScreen = getDefaultScreen();
        this.robot = new Robot( defaultScreen );
        setScreenDimensions( defaultScreen );
        this.screenRegion = new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() );
    }


    private void setScreenDimensions( GraphicsDevice screen ) {
        screenHeight = screen.getDisplayMode().getHeight();
        screenWidth = screen.getDisplayMode().getWidth();
    }


    private GraphicsDevice getDefaultScreen() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }


    public void captureAndAddFrame() {
        Image frame = robot.createMultiResolutionScreenCapture( screenRegion )
                .getResolutionVariant( screenWidth, screenHeight );
        frameBuffer.add( frame );
    }

}
