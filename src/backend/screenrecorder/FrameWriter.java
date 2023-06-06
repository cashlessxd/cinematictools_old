package backend.screenrecorder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;

public class FrameWriter implements Runnable {

    private static final String DIRECTORY_NAME = "framedump";
    private static final String FILE_NAME = "frame";
    private static final String IMAGE_FORMAT = "png";
    private final Path dumpDirectory;
    private final int offset;
    private final List<BufferedImage> frames;


    public FrameWriter( List<BufferedImage> frames, int offset ) {
        this.frames = frames;
        this.dumpDirectory = getWorkingDirectory();
        this.offset = offset;
    }


    @Override
    public void run() {
        AtomicInteger index = new AtomicInteger();
        frames.forEach( frame -> {
            writeFrame( frame, index.getAndIncrement() );
        } );
    }


    private void writeFrame( BufferedImage frame, int index ) {
        try {
            ImageIO.write( frame, IMAGE_FORMAT, new File( getFileURI( offset + index, dumpDirectory ) ) );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }


    private URI getFileURI( int index, Path dumpDirectory ) {
        return dumpDirectory.resolve( FILE_NAME + index + "." + IMAGE_FORMAT ).toUri();
    }


    private Path getWorkingDirectory() {
        return Paths.get( System.getProperty( "user.dir" ) + System.getProperty( "file.separator" ) + DIRECTORY_NAME );
    }


}
