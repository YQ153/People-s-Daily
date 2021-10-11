package com.yanqi.rewrite.io;

import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * @author YQQ
 * @program: People's Daily
 * @description:
 * @date 2021-09-30 19:26:01
 */
public class FileInAndOut {
    protected static FileOutputStream fileOutputStream;
    protected static InputStream fileInputStream;

    public static int fileWrite(@NotNull String fileName, @NotNull InputStream inputStream){
        fileInputStream = inputStream;
        try {
            fileOutputStream = new FileOutputStream(creatFileByFileName(fileName));
        } catch (FileNotFoundException e) {
            return -1;
        }
        int len = 0;
        byte[] bytes = new byte[1024 * 2048];
        return 0;

    }

    private static File creatFileByFileName(String fileName){
        return new File(fileName);
    }
}
