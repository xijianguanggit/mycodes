package nio;

/**
 * Created by wangdecheng on 2017/12/28.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Use for reader line from file by use Nio
 *
 * @author Victor
 */
public class NioReader {
    private static final String lineSeparator = "\n";//换行关键字
    private final int size = 1024 * 8;//块的大小
    private static int n;//该文件可分成块数量
    private FileInputStream fin;//读取文件的流
    private FileChannel fcin;//管道
    private static int count = 0;//记录该块读取到第几行
    private static int piece = 0;//记录读取到哪一块
    private static String[] strs;//该块转换后的数组，一行数据对应一个元素

    public NioReader(String path)
            throws IOException {
        fin = new FileInputStream(path);
        fcin = fin.getChannel();
        long length = fcin.size();
        n = (int)length / size;
        strs = getStrsBySize();
    }

    public String getNextLine()
            throws IOException {
        String s = null;
        int lengh = strs.length - 1;
        if (count < lengh) {
            s = strs[count];
            count++;
        } else if (count == lengh) {
            if (piece > n) {
                s = strs[count];
                count++;
            } else {
                strs = getStrsBySize();
                s = getNextLine();
            }
        }
        return s;
    }

    /**
     * 获取下一块的String数组
     */
    private String[] getStrsBySize()throws IOException {
        count = 0;
        if (piece <= n) {
            long opint = piece * size;
            MappedByteBuffer buffer;
            byte[] content = new byte[size];

            String str=null;
            if (piece == n) {
                long length = fcin.size();
                int finalsize = (int)(length - opint);
                buffer = fcin.map(FileChannel.MapMode.READ_ONLY, opint,
                        finalsize);
                buffer.get(content, 0, finalsize);
                str = new String(content,0,finalsize);
            } else {
                buffer = fcin.map(FileChannel.MapMode.READ_ONLY, opint,
                        size);
                buffer.get(content, 0, size);
            }

            if(str==null) {
                if (piece == 0) {
                    str = new String(content);
                    str.trim();
                } else {
                    String newStr = new String(content);
                    newStr.trim();
                    str = strs[strs.length - 1] + newStr;
                }
            }
            strs = str.split(lineSeparator);
            piece++;
        }
        return strs;
    }

    public void close()
            throws IOException {
        if (fin != null) {
            fin.close();
        }
        if (fcin != null) {
            fcin.close();
        }
    }
}

