package com.ecnu.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Txt文件工具类
 */
public class TxtUtil {
    private static Logger logger = LoggerFactory.getLogger(TxtUtil.class);
    private final static String TXT = "txt";

    public static List<String[]> readTxt(MultipartFile file) throws IOException {
        if (checkTxtFile(file)) {
            InputStream inputStream = file.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String[]> rowList = new ArrayList<String[]>(); //存储txt中的原始数据。

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                //把每一行的数据放在cells中
                String[] cells = line.split("[;| \t]");
                rowList.add(cells);
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();
            return rowList;
        } else {
            logger.error("文件读取错误");
            throw new IOException(file.getName() + "文件读取错误");
        }
    }

    /**
     * 检查文件
     */
    private static boolean checkTxtFile(MultipartFile  file) throws IOException {
        if (file == null) {
            logger.error("文件不存在");
            throw new FileNotFoundException("文件不存在！");
        }

        // 获取文件名
        String filename = file.getOriginalFilename();
        logger.info("file name : {}", filename);
        // 判断是否为txt文件
        if (!filename.endsWith(TXT)) {
            logger.error(filename + "不是txt文件");
            throw new IOException(filename + "不是txt文件");
        }
        return true;
    }
}
