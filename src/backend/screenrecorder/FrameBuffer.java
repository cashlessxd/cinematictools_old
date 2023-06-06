package backend.screenrecorder;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FrameBuffer {

    /* Amount of frames to buffer before dumping to disk.
     * Rationale: size of a single uncompressed screenshot is approx. 24MB @ 4K.
     * Thus 120 frames use up around 2.9GB of ram expected to be provided even by the worst machines.
     */
    //TODO TH: calculation or testing of actual image size
    private static final int FRAME_COUNT = 120;
    private final AtomicInteger batchCounter;

    private List<BufferedImage> frames;


    public FrameBuffer() {
        this.batchCounter = new AtomicInteger();
        this.frames = new LinkedList<>();
    }


    public void add( Image frame ) {
        frames.add( createBufferedImage( frame ) );
        if ( frames.size() == FRAME_COUNT ) {
            flush();
        }
    }


    public void flush() {
        int offset = batchCounter.getAndIncrement() * FRAME_COUNT;
        FrameWriter frameWriter = new FrameWriter( frames, offset );
        Thread thread = new Thread( frameWriter );
        thread.start();
        frames = new LinkedList<>();
    }


    private BufferedImage createBufferedImage( Image frame ) {
        BufferedImage bufferedImage = new BufferedImage( frame.getWidth( null ), frame.getHeight( null ), BufferedImage.TYPE_INT_RGB );
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage( frame, 0, 0, null );
        graphics2D.dispose();
        return bufferedImage;
    }

}
