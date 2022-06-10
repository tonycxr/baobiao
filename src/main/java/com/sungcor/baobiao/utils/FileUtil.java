package com.sungcor.baobiao.utils;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {

    public final static String FILE_SUFFIX_SEPARATOR = ".";

    /**
     * 创建文件夹
     *
     * @param filePath 文件夹路径
     * @return 创建文件夹是否成功
     */
    public static boolean createDir(String filePath) {
        File dir = new File(filePath);
        if (dir.exists()) {
            System.out.println("创建目录" + filePath + "失败，目标目录已经存在");
            return false;
        }
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + filePath + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + filePath + "失败！");
            return false;
        }
    }

    /**
     * 文件是否存在
     *
     * @param filePath 文件路径
     * @return 文件是否存在
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return 创建文件是否成功
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void fileMove(String from, String to) throws Exception {
        try {
            File dir = new File(from);
            File[] files = dir.listFiles();
            if (files == null) {
                return;
            }
            File moveDir = new File(to);
            if (!moveDir.exists()) {
                moveDir.mkdirs();
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    fileMove(file.getPath(), to + "\\" + file.getName());
                    file.delete();
                }
                File moveFile = new File(moveDir.getPath() + "\\"
                        + file.getName());
                if (moveFile.exists()) {
                    moveFile.delete();
                }
                file.renameTo(moveFile);
                System.out.println(file + " 移动成功");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 复制文件到指定文件夹下
     *
     * @param fromFilePath 源文件路径<带文件名>
     * @param toPath 目标路径
     * @param fileName 重命名文件
     */
    public static void copyFileToDir(String fromFilePath, String toPath, String fileName) {
        File file = new File(fromFilePath);
        try {
            FileInputStream input = new FileInputStream(file);
            createDir(toPath);
            String dirFile = toPath + "/" + ("".equals(fileName) ? file.getName() : fileName);
            int index;
            byte[] bytes = new byte[1024];
            FileOutputStream downloadFile = new FileOutputStream(dirFile);
            while ((index = input.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
            downloadFile.close();
            input.close();
            System.out.print("复制文件到指定目录下成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("复制文件到指定目录下失败");
        }
    }

    /**
     * 文件重命名 FileUtils.reName("F:\\BEYOND - 海阔天空[weiyun].mp3", "海阔天空.mp3");
     *
     * @param filePath 文件路径(带文件后缀名)
     * @param reName 新名称(必须有后缀名)
     */
    public static void reNameSelf(String filePath, String reName) {
        String oldName = getFileName(filePath);
        String newName = getFileName(reName);
        File file1 = new File(filePath);
        File file2 = new File(filePath.replace(oldName, reName));
        boolean reNameSuccess = file1.renameTo(file2);
        if (reNameSuccess) {
            System.out.println("文件重命名成功");
        } else {
            System.out.println("文件重命名失败");
        }
    }

    public static String[] listFile(File dir) {// 获取文件绝对路径
        String absolutPath = dir.getAbsolutePath();// 声获字符串赋值为路传入文件的路径
        String[] paths = dir.list();// 文件名数组
        String[] files = new String[paths.length];// 声明字符串数组，长度为传入文件的个数
        for (int i = 0; i < paths.length; i++) {// 遍历显示文件绝对路径
            files[i] = absolutPath + "/" + paths[i];
        }
        return files;
    }

    public static void createFile(String path, boolean isFile) {// 创建文件或目录
        createFile(new File(path), isFile);// 调用方法创建新文件或目录
    }

    public static void createFile(File file, boolean isFile) {
        if (!file.exists()) {// 如果文件不存在
            if (!file.getParentFile().exists()) {// 如果文件父目录不存在
                createFile(file.getParentFile(), false);
            } else {// 存在文件父目录
                if (isFile) {// 创建文件
                    try {
                        file.createNewFile();// 创建新文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    file.mkdir();// 创建目录
                }
            }
        }
    }

    /**
     * 获取文件夹下所有文件大小或文件大小
     *
     * @param path 文件路径
     */
    public static void getFilesSize(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
//                    int fileSize = (int) (file1.length() / 1024);
                    double fileSize = file1.length() / 1024 / 1024;
                    double bytes = file.length();
                    double kilobytes = (bytes / 1024);
                    double megabytes = (kilobytes / 1024);
                    double gigabytes = (megabytes / 1024);
                    String outInfo = "文件名:[" + file1.getName() + "]   文件大小:[" + fileSize + "兆]";
                    System.out.println(outInfo);
                }
            }
        } else {
//            int fileSize = (int) (file.length() / 1024);
            long fileSize = file.length() / 1024 / 1024;
            String outInfo = "文件名:[" + file.getName() + "]   文件大小:[" + fileSize + "兆]";
            double bytes = file.length();
            double kilobytes = (bytes / 1024);
            double megabytes = (kilobytes / 1024);
            double gigabytes = (megabytes / 1024);
            System.out.println(outInfo);
        }
    }

    /**
     * 递归删除(推荐)
     */
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDir(file);
                }
            }
        }
        boolean delete = dir.delete();
    }

    /**
     * Read file
     *
     * @param filePath 路径
     */
    public static String readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();
        if (!isFileExist(filePath)) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!isEmpty(fileContent.toString())) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent.toString();
        } catch (IOException e) {
//            throw new RuntimeException("IOException", e);
            return null;
        } finally {
            close(reader);
        }
    }

    /**
     * Write file
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (isEmpty(content)) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            close(fileWriter);
        }
    }

    /**
     * write file, the string will be written to the begin of the file
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    /**
     * Write file
     */
    public static boolean writeFile(String filePath, InputStream is) {
        if (filePath == null) {
            return false;
        }
        return writeFile(filePath, is, false);
    }

    /**
     * 写文件
     *
     * @param filePath 路径
     * @param is 输入流
     * @param append 追加
     */
    public static boolean writeFile(String filePath, InputStream is, boolean append) {

        return writeFile(filePath != null ? new File(filePath) : null, is, append);
    }

    /**
     * Write file
     */
    public static boolean writeFile(File file, InputStream is) {
        return writeFile(file, is, false);
    }


    /**
     * Write file
     *
     * @param file .
     * @param is 输入流     .
     * @param append .
     * @return .
     */
    public static boolean writeFile(File file, InputStream is, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte[] data = new byte[1024];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException", e);
        } finally {
            close(o);
            close(is);
        }
    }

    /**
     * 读取源文件内容
     *
     * @param filename String 文件路径
     * @return byte[] 文件内容
     * @throws IOException .
     */
    public static byte[] readFile(String filename) throws IOException {

        File file = new File(filename);
        if (isEmpty(filename)) {
            throw new NullPointerException("无效的文件路径");
        }
        long len = file.length();
        byte[] bytes = new byte[(int) len];

        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(file));
        int r = bufferedInputStream.read(bytes);
        if (r != len) {
            throw new IOException("读取文件不正确");
        }
        bufferedInputStream.close();
        return bytes;

    }

    /**
     * Move file
     */
    public static void moveFile(String srcFilePath, String destFilePath)
            throws FileNotFoundException {
        if (isEmpty(srcFilePath) || isEmpty(destFilePath)) {
            throw new RuntimeException("Both srcFilePath and destFilePath cannot be null.");
        }
        moveFile(new File(srcFilePath), new File(destFilePath));
    }

    /**
     * Move file
     */
    public static void moveFile(File srcFile, File destFile) throws FileNotFoundException {
        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            deleteFile(srcFile.getAbsolutePath());
        }
    }

    /**
     * Copy file
     */
    public static boolean copyFile(String srcFilePath, String destFilePath)
            throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(srcFilePath);
        return writeFile(destFilePath, inputStream);
    }

    /**
     * rename file
     *
     * @param filePath 源文件路径
     * @param newFileName 新文件名(不需要加路径和后缀)
     */
    public static boolean renameFile(String filePath, String newFileName) {
        return renameFile(new File(filePath), newFileName);
    }

    /**
     * rename file
     *
     * @param file 源文件
     * @param newFileName 新文件名(不需要加路径和后缀)
     */
    public static boolean renameFile(File file, String newFileName) {
        File newFile = null;
        if (file.isDirectory()) {
            newFile = new File(file.getParentFile(), newFileName);
        } else {
            String temp = newFileName.contains(".") ? newFileName
                    : newFileName + file.getName().substring(file.getName().lastIndexOf('.'));
//            temp = newFileName + file.getName().substring(file.getName().lastIndexOf('.'));
            newFile = new File(file.getParentFile(), temp);
        }
        boolean renameSuccess = file.renameTo(newFile);
        System.out.println("重命名" + (renameSuccess ? "成功" : "失败"));
        return renameSuccess;
    }

    /**
     * 获取没有后缀的文件名 Get file name without suffix
     *
     * @param filePath .
     * @return 获取没有后缀的文件名
     */
    public static String getFileNameWithoutSuffix(String filePath) {
        if (isEmpty(filePath)) {
            return filePath;
        }
        int suffix = filePath.lastIndexOf(FILE_SUFFIX_SEPARATOR);
        int fp = filePath.lastIndexOf(File.separator);
        if (fp == -1) {
            return (suffix == -1 ? filePath : filePath.substring(0, suffix));
        }
        if (suffix == -1) {
            return filePath.substring(fp + 1);
        }
        return (fp < suffix ? filePath.substring(fp + 1, suffix) : filePath.substring(fp + 1));
    }

    /**
     * 获取文件名(包含后缀) Get file name
     *
     * @param filePath 路径
     */
    public static String getFileName(String filePath) {
        if (isEmpty(filePath)) {
            return filePath;
        }
        int fp = filePath.lastIndexOf(File.separator);
        return (fp == -1) ? filePath : filePath.substring(fp + 1);
    }

    /**
     * Get folder name
     */
    public static String getFolderName(String filePath) {
        if (isEmpty(filePath)) {
            return filePath;
        }
        int fp = filePath.lastIndexOf(File.separator);
        return (fp == -1) ? "" : filePath.substring(0, fp);
    }

    /**
     * 获取文件后缀
     *
     * @param filePath 文件路径
     * @return 文件后缀(不包含 " . ")
     */
    public static String getFileSuffix(String filePath) {
        if (isEmpty(filePath)) {
            return filePath;
        }
        if (!filePath.contains(FILE_SUFFIX_SEPARATOR)) {
            String[] path = filePath.split(File.separator);
            return path[path.length - 1];
        }
        int suffix = filePath.lastIndexOf(FILE_SUFFIX_SEPARATOR);
        int fp = filePath.lastIndexOf(File.separator);
        if (suffix == -1) {
            return "";
        }
        return (fp >= suffix) ? "" : filePath.substring(suffix + 1);
    }

    /**
     * 创建目录
     *
     * @param filePath 路径
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (isEmpty(folderName)) {
            return false;
        }
        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    /**
     * 判断一个文件是否存在
     *
     * @param filePath 路径
     * @return .
     */
    public static boolean isFileExist(String filePath) {
        if (isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Judge whether a Directory is exist
     */
    public static boolean isFileDirExist(String filePath) {
        if (isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isDirectory());
    }


    /**
     * 判断文件夹是否存在
     *
     * @param directoryPath 路径
     * @return .
     */
    public static boolean isFolderExist(String directoryPath) {
        if (isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * Delete file or folder
     */
    public static boolean deleteFile(String path) {
        if (isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
        }
        return file.delete();
    }

    /**
     * Delete file or folder
     */
    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                return file.delete();
            }
            for (File f : childFile) {
                deleteFile(f);
            }
        }
        return file.delete();
    }

    /**
     * Get file size
     */
    public static long getFileSize(String path) {

        if (isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    private static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            return s.length() == 0;
        }
    }

    /**
     * Get folder size
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes 字节
     * @return 返回长度信息信息
     */
    public static String bytes2mb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal kilobyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
        return (returnValue + "MB");
    }

    /**
     * @return 获取到的文件md5值
     */
    public static String getFileMD5(String filePath) {
        return getFileMD5(new File(filePath));
    }

    /**
     * @return 获取到的文件md5值
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(in);
        }
    }
//==================================================================================================

    /**
     * 递归读取文件路径
     */
    public static void readFileNames(String path, ReadLines readLines) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            readLines.onReadLine(file.getPath());
        } else if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                readFileNames(files[i].getPath(), readLines);
            }
        }
    }

    /**
     * 获取文件的Sha1值
     */
    public static String getFileSha1(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[1024 * 1024 * 10];

            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            String sha1 = new BigInteger(1, digest.digest()).toString(16);
            int length = 40 - sha1.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    sha1 = "0" + sha1;
                }
            }
            return sha1;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 遍历读取文件获取文件内容(主要用于索引文件内的文件是否包含某些关键词)
     *
     * @param path 文件或文件夹路径
     * @param readLines 读取到的行回调
     */
    public static void indexKeyForFile(String path, final ReadLines readLines) {
        readFileNames(path, new ReadLines() {
            @Override
            public void onReadLine(String linStr) {
                readTextFileLines(linStr, new ReadLines() {
                    @Override
                    public void onReadLine(String linStr) {
                        readLines.onReadLine(linStr);
                    }
                });
            }
        });
    }

    /**
     * 逐行读取
     *
     * @param path 路径
     * @param readLines 读取行接口
     * @throws IOException .
     */
    public static void readTextFileLines(String path, ReadLines readLines) {
        try {
            File file = new File(path);
            file.exists();
            //BufferedReader是可以按行读取文件
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (readLines != null) {
                    readLines.onReadLine(str);
                }
            }
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件总行数
     *
     * @param filePath 文件路径
     * @return 文件总行数
     */
    public static int getFlieTotalLines(String filePath) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            LineNumberReader reader = new LineNumberReader(in);
            String s = reader.readLine();
            int lines = 0;
            while (s != null) {
                lines++;
                s = reader.readLine();
            }
            reader.close();
            in.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 方法:按行获取指定内容 使用的时候注意测试,该方法未试验过
     *
     * @param startLine 起始行
     * @return 指定内容内容
     */
    public static String getSpecifyLinesContent(String filePath, int startLine, int endLine) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            StringBuilder sb = new StringBuilder();
            String temp = null;
            int count = 0;
            while ((temp = br.readLine()) != null) {
                count++;
                if (count >= startLine && count <= endLine) {
                    sb.append(temp).append("\n");
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "获取失败";
    }

    /**
     * 方法一:按行获取指定内容(至末尾)
     *
     * @param startLine 起始行
     * @return 指定内容内容
     * @throws Exception .
     */
    public static String getSpecifyContent1(String filePath, int startLine) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        StringBuilder sb = new StringBuilder();
        String temp = null;
        int count = 0;
        while ((temp = br.readLine()) != null) {
            count++;
            if (count >= startLine) {
                sb.append(temp).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * 替换文件内的文本信息<因为是一行一行读的,所以只能替换单词一类的字符串>
     *
     * @param path 文件路径
     * @param olderArg 旧参数
     * @param newArg 新参数
     * @throws IOException .
     */
    public static void replacetext(String path, String olderArg, String newArg) throws IOException {
        // 读
        File file = new File(path);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        String line = null;
        while ((line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            line = line.replaceAll(olderArg, newArg);
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
        }
        // 关闭 输入流A
        bufIn.close();
        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }

    /**
     * 在文件里面的指定行插入数据<如果插入后要换行的话在插入内容的结尾加  /n >
     *
     * @param filePath 文件路径
     * @param insertLineNumber 要插入的行号
     * @param lineToBeInserted 要插入的数据
     * @throws Exception IO操作引发的异常
     */
    public static void insertContentToFile(String filePath, int insertLineNumber,
                                           String lineToBeInserted) throws Exception {
        File inFile = new File(filePath);
        // 临时文件
        File outFile = File.createTempFile("name", ".tmp");
        // 输入
        FileInputStream fis = new FileInputStream(inFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        // 输出
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);
        // 保存一行数据
        String thisLine;
        // 行号从1开始
        int i = 1;
        while ((thisLine = in.readLine()) != null) {
            // 如果行号等于目标行，则输出要插入的数据
            if (i == insertLineNumber) {
                out.println(lineToBeInserted);
            }
            // 输出读取到的数据
            out.println(thisLine);
            // 行号增加
            i++;
        }
        out.flush();
        out.close();
        in.close();
        // 删除原始文件
        inFile.delete();
        // 把临时文件改名为原文件名
        outFile.renameTo(inFile);
    }

    /**
     * Close closeable object 关闭可以关闭的对象
     */
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
//                LogUtils.d("IOUtils",e.toString());
            }
        }
    }

    /**
     * 扫描路径下的文件
     *
     * @param file 文件路径
     */
    public static void scanningFilesName(File file) {
        if (file == null) {
            return;
        }
        File[] fs = file.listFiles();
        if (fs != null) {
            for (File f : fs) {
                if (f.isDirectory()) {    //若是目录，则递归打印该目录下的文件
                    System.out.println("文件夹:" + f.getPath());
                    scanningFilesName(f);
                }
                if (f.isFile())        //若是文件，直接打印
                {
                    System.out.println("文件:" + f.getPath());
                }
            }
        }
    }

    /**
     * windows下文件名中不能含有：\ / : * ? " < > | 英文的这些字符 ，这里使用"."、"'"进行替换。
     *
     * \/:?| 用.替换
     *
     * "<> 用'替换
     */
    public static String getConversionFileName(String dirPath) {
        dirPath = dirPath.replaceAll("[/\\\\:*?|]", ".");
        dirPath = dirPath.replaceAll("[\"<>]", "'");
        return dirPath;
    }

    /**
     * 获取桌面路径
     */
    public static String getDesktopPath() {
        return FileSystemView.getFileSystemView().getHomeDirectory().getPath() + File.separator;
    }

    /**
     * 方法二:按行获取指定内容(至末尾)
     *
     * @param startLine 起始行
     * @return 指定内容内容
     * @throws Exception .
     */
    public String getSpecifyContent2(String filePath, int startLine) throws Exception {
        StringBuilder sb = new StringBuilder();
        LineNumberReader lnr = new LineNumberReader(new FileReader(filePath));
        String buff = lnr.readLine();
        while (buff != null) {
            if (lnr.getLineNumber() >= startLine) {
                sb.append(buff);
                sb.append("\r\n");
            }
            buff = lnr.readLine();
        }
        return sb.toString();
    }


    public interface ReadLines {

        void onReadLine(String linStr);
    }


}

