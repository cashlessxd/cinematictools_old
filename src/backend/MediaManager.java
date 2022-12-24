package backend;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MediaManager {

    private ArrayList<BufferedImage> frames;

    public MediaManager() {
        frames = new ArrayList<>();
    }

    public void createScreenCapture(Robot robot, int frameNumber) {
        BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        frames.add(image);
    }

    public void mergeFramesToVideo(int framesPerSecond) throws IOException {
        File video = new File("result/clip.mp4");
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(video, framesPerSecond);

        for (BufferedImage frame : frames) {
            encoder.encodeImage(frame);
        }

        encoder.finish();
    }
}