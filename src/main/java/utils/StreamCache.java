package utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by wangdecheng on 30/08/2018.
 */

public class StreamCache implements  Runnable{

    private Map<String,byte[]> tempCache = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService schedulers = Executors.newScheduledThreadPool(1);
    private static final Logger logger = Logger.getLogger(StreamCache.class);

    private String fileDirectory = "/user/cache/";


    int size = 16;
    double factor = 0.75;
    int capacity = (int) (size * factor);
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(size);

    public StreamCache(){
        init();
    }
    public void init() {
        //每5分钟执行一次，超过capacity的流写入磁盘
        schedulers.schedule(this, 5, TimeUnit.MINUTES);
    }

    //放入缓存
    public void addCache(String key,InputStream inputStream){
        try {
            blockingQueue.put(key);
        } catch (InterruptedException e) {
            logger.error(" error put "+ key + " to queue",e) ;
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        tempCache.put(key,input2byte(inputStream));

    }

    //获取文件流
    public byte[] getInputStream(String key){
        if(tempCache.containsKey(key)){
            return tempCache.get(key);
        }
        return loadFromFile(key);
    }

    //刷新全部缓存到磁盘
    public void flush(){
        while (!blockingQueue.isEmpty()){
            String key = blockingQueue.poll();
            saveToFile(key);
        }
    }

    @Override
    public void run() {
        //缓存写入磁盘
        while (blockingQueue.size() > capacity){
            String key = blockingQueue.poll();
            saveToFile(key);
        }
    }

    private byte[] loadFromFile(String key) {
        File file = new File(fileDirectory+key);
        InputStream inputStream = null;
        try {
             inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return input2byte(inputStream);
    }

    private void saveToFile(String key) {

        byte[] inputStream = tempCache.get(key);

        //写入磁盘
        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(fileDirectory + key);
            outputStream.write(inputStream);

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tempCache.remove(key);
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    private final byte[] input2byte(InputStream inStream){
        if(inStream == null){
            return null;
        }
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 1024)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
        }catch (IOException e){
            e.printStackTrace();

        }

        return null;
    }
}
