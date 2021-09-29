package com.yanqi;

import java.io.*;

/**
 * @author YQQ
 * @program: People's Daily
 * @description: 支线程用于写入文件
 * @date 2021-09-29 22:41:20
 */
public class IOThread implements Runnable {

    private File file;
    private InputStream is;
    private FileOutputStream fileOutputStream = null;

    public IOThread() {
    }

    public IOThread(File file, InputStream is) {
        this.file = file;
        this.is = is;
    }

    @Override
    public void run() {
        try{
            fileOutputStream = new FileOutputStream(file,true);
            writeToFile(is, fileOutputStream);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void writeToFile(InputStream is, FileOutputStream fileOutputStream) throws IOException {
        byte[] bytes = new byte[1024 * 2048];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            fileOutputStream.write(bytes,0,len);
        }
        is.close();
        fileOutputStream.close();
    }
}
