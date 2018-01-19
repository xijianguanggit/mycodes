package nio;

/**
 * Created by wangdecheng on 2017/12/28.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioWriter {
    private static final String lineSeparator = "\r\n";
    private FileOutputStream fout;
    private FileChannel fcout;
    private ByteBuffer buffer;

    public NioWriter(String path) throws IOException {
        fout = new FileOutputStream(path);
        fcout = fout.getChannel();
    }

    public void putln(String s) {
        if (fcout == null) {
            throw new IllegalArgumentException(
                    "attempt to use a closed Writer");
        }
        buffer= ByteBuffer.allocate(1024*8);
        buffer.clear();
        s = s.trim() + lineSeparator;
        byte[] bytes = s.getBytes();
        buffer.put(bytes);
        buffer.flip();
        try {
            fcout.write(buffer);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() throws IOException {
        if (fout != null) {
            fout.close();
        }
        if (fcout != null) {
            fcout.close();
        }
    }
}

