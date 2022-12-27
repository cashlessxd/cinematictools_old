package backend;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MediaManager {

    final private Robot virtualKeyboard;

    final private ArrayList<BufferedImage> frames;

    public MediaManager(Robot virtualKeyboard) {
        this.virtualKeyboard = virtualKeyboard;
        this.frames = new ArrayList<>();
    }

    public void createScreenCapture() {
        BufferedImage image = virtualKeyboard.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        frames.add(image);
    }

    public void mergeFramesToVideo(int framesPerSecond) throws IOException {
        Files.createDirectories(Paths.get("result"));
        File video = new File("result/clip.mp4");
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(video, framesPerSecond);

        for (BufferedImage frame : frames) {
            encoder.encodeImage(frame);
        }

        encoder.finish();
    }
}