package com.xingguang.sinanya.update;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xingguang.sinanya.update.imal.DownType;
import com.xingguang.sinanya.windows.imal.HttpGet;
import com.xingguang.sinanya.system.MessagesSystem;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entitySystemProperties;
import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * @author SitaNya
 * 日期: 2019-08-20
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class UpdateForDice {
    private static final Logger log = LoggerFactory.getLogger(UpdateForDice.class.getName());
    ExecutorService cachedThreadPool;
    File oldDir;
    File zip;
    File zipDir;

    File newZip;

    File deckDir;
    File deckFile;

    boolean zipExists;
    boolean zipDirExists;

    boolean oldDirExists;

    boolean newZipExists;

    boolean deckDirExists;

    ProgressBarThread pbt;
    JProgressBar downZipProgress;
    JProgressBar downDeckProgress;

    public UpdateForDice(JProgressBar downZipProgress, DownType downType) {
        String dir = entitySystemProperties.getSystemDir();

        switch (downType) {
            case ZIP:
                this.zip = new File(dir + "/SinaNyaNext.zip");
                this.oldDir = new File(dir + "/SinaNya");
                this.zipDir = new File(dir + "/");
                this.zipExists = zip.exists();
                this.zipDirExists = zipDir.exists();
                this.oldDirExists = oldDir.exists() && oldDir.isDirectory();
                this.downZipProgress = downZipProgress;
                break;
            case DECK:
                deckDir = new File(dir + File.separator + "deck");
                this.downDeckProgress = downZipProgress;
                this.deckDirExists = deckDir.exists() && deckDir.isDirectory();

                break;
            default:
                break;
        }

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("download-" + downType.name() + "-%d").build();
        //Common Thread Pool
        cachedThreadPool = new ThreadPoolExecutor(1, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @return md5 value
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void update() throws IOException {
        if (zipExists && oldDirExists) {
            zip.delete();
        }
        try {
            downLoadFromUrl("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/SinaNyaNext.zip", "SinaNyaNext.zip", downZipProgress, zipDir.getPath());
            Rectangle rect = new Rectangle(0, 0, downZipProgress.getWidth(), downZipProgress.getHeight());
            downZipProgress.setValue(downZipProgress.getMaximum());
            downZipProgress.paintImmediately(rect);
            cachedThreadPool.shutdown();
            oldDirExists = oldDir.exists();
            zipExists = zip.exists();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "下载失败，请重新下载");
            return;
        }

        long zipSize = new URL("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/SinaNyaNext.zip").openConnection().getContentLength();


        if (oldDirExists && zipExists && zip.length() == zipSize) {
            unZip(zip, oldDir.getParentFile().getPath());
            try {
                Desktop.getDesktop().open(new File(entitySystemProperties.getSystemDir()));
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            JOptionPane.showMessageDialog(null, "更新完毕,请关闭设置窗口，并运行“重启塔骰后端服务.bat”");
        } else {
            JOptionPane.showMessageDialog(null, "更新失败,请不要关闭酷Q重新更新。");
        }
    }

    public void downLoad(String deck) throws IOException {
        if (!deckDirExists) {
            deckDir.mkdirs();
        }
        File newDeck = new File(deckDir.getPath() + File.separator + deck);
        boolean newDeckExists;
        try {
            downLoadFromUrl("https://deck-1256090486.cos.ap-chengdu.myqcloud.com/" + java.net.URLEncoder.encode(deck, "utf-8"), deck, downDeckProgress, deckDir.getPath());
            Rectangle rect = new Rectangle(0, 0, downDeckProgress.getWidth(), downDeckProgress.getHeight());
            downDeckProgress.setValue(downDeckProgress.getMaximum());
            downDeckProgress.paintImmediately(rect);
            cachedThreadPool.shutdown();
            newDeckExists = newDeck.exists();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "下载失败，请重新下载");
            return;
        }

        long deckSize = new URL("https://deck-1256090486.cos.ap-chengdu.myqcloud.com/" + java.net.URLEncoder.encode(deck, "utf-8")).openConnection().getContentLength();

        if (newDeckExists && newDeck.length() == deckSize) {
            JOptionPane.showMessageDialog(null, "下载成功");
        } else {
            File deleteFile = new File(deckDir.getPath() + File.separator + deck);
            if (deleteFile.exists() && deleteFile.isFile()) {
                deleteFile.delete();
            }
            JOptionPane.showMessageDialog(null, "下载失败失败,请重新下载");
        }
    }

    public boolean isZipDirExists() {
        return zipDirExists;
    }

    public boolean checkNeedUpdate() {
        boolean needUpdate = !MessagesSystem.VERSIONS.equals(HttpGet.sendGet("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/version"));
        boolean notWarn = !HttpGet.sendGet("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/version").contains("Alpha") && !HttpGet.sendGet("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/version").contains("Beta");
        return needUpdate && notWarn;
    }

    public boolean checkNeedWarn() {
        return !MessagesSystem.VERSIONS.equals(HttpGet.sendGet("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/version"));
    }


    public String checkNextUpdate() {
        return HttpGet.sendGet("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/version");
    }

    public boolean serverFileExist() {
        return HttpGet.checkHttpFileExist("https://dice-1256090486.cos.ap-chengdu.myqcloud.com/SinaNyaNext.zip");
    }

    private void downLoadFromUrl(String urlStr, String fileName, JProgressBar jProgressBar, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        conn.setRequestProperty("lfwywxqyh_token", toekn);

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        this.pbt = new ProgressBarThread(conn.getContentLength(), jProgressBar);
        cachedThreadPool.execute(pbt);
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        fos.close();
        inputStream.close();
        pbt.finish();
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            pbt.updateProgress(len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * zip解压
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
