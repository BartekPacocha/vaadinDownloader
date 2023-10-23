package com.example.cdaVaadin.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CdaDownloaderService {

    private void download() {
//        System.out.println("Start");
//
//        List<Integer> missingEpisodesToRetry = loadMissingEpisodes();
//
//        while (!missingEpisodesToRetry.isEmpty()) {
//
//            System.out.println("Start Firefox");
//            FirefoxOptions options = new FirefoxOptions();
//            options.setHeadless(true);
//
//            WebDriver driver = new FirefoxDriver(options);
//
//            int counter = 1;
//
//            for (int i = 1; i <= 999; i++) {
//
//                if (!missingEpisodesToRetry.isEmpty()) {
//                    if (!missingEpisodesToRetry.contains(i)) {
//                        continue;
//                    } else {
//                        int size = missingEpisodesToRetry.size();
//                        System.out.println(counter + "/" + size);
//                        counter++;
//                    }
//                }
//
//                String wbijamUrl = getWbijamUrl(i);
//                System.out.println("Get " + i);
//
//                try {
//                    Document wbijamDoc = Jsoup.connect(wbijamUrl).get();
//                    Elements table = wbijamDoc.getElementsByTag("table");
//                    Element element = table.get(0).getElementsByTag("tr").get(2).getElementsByTag("td").get(4);
//                    String rel = element.select(".odtwarzacz_link").attr("rel");
//
//                    String odtwarzacz = getOdtwarzacz(rel);
//
//                    Document doc2 = Jsoup.connect(odtwarzacz).get();
//
//                    String player = "";
//
//                    for (Element a : doc2.select("a")) {
//                        String href = a.attr("href");
//                        boolean cda = href.contains("cda");
//                        if (cda) {
//                            player = href;
//                            break;
//                        }
//                    }
//
//                    driver.get(player);
//                    Document doc4 = Jsoup.parse(driver.getPageSource());
//
//                    Elements elementsByClass = doc4.getElementsByClass("pb-video-player-content");
//                    if (elementsByClass.isEmpty()) {
//                        System.out.println("Missing episode: " + i);
//                        appendLineToFile("missing.txt", String.valueOf(i));
//                        continue;
//                    }
//                    String src = elementsByClass.get(0).getElementsByClass("pb-video-player").get(0).attr("src");
//
//                    appendLineToFile("lista.txt", i + " - " + src);
//                    System.out.println("Download link to episode: " + i);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            driver.close();
//            killFirefoxAndGeckoDriver();
//            System.out.println("Stop Firefox");
//
//            missingEpisodesToRetry = loadMissingEpisodes();
//        }
//
//        // download mp4
//        downloadMp4List();
    }

    private static void downloadMp4List() {
        List<String> list = loadMp4Links();

        downloadFiles(list);
    }

    private static void downloadFiles(List<String> fileURLs) {
        for (int i = 0; i < fileURLs.size(); i++) {
            String line = fileURLs.get(i);
            String fileURL = getUrlFromLine(line);
            String savePath = getSavePathFromLine(line);

            try {
                downloadFile(fileURL, savePath, i + 1, fileURLs.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getSavePathFromLine(String line) {
        int i = line.indexOf("-") - 1;
        String substring = line.substring(0, i);

        return "D:\\Filmy\\One Piece\\onePiece\\" + substring + ".mp4";
    }

    private static String getUrlFromLine(String line) {
        int i = line.indexOf("-") + 1;

        return line.substring(i);
    }

    private static void downloadFile(String fileURL, String savePath, int fileNumber, int totalFiles) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try (InputStream inputStream = httpURLConnection.getInputStream();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;
            long fileSize = httpURLConnection.getContentLength(); // Get the total file size

            long startTime = System.currentTimeMillis();

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                double speed = (double) totalBytesRead / (double) elapsedTime * 1000.0;

                System.out.printf("File %d/%d - Downloaded: %d bytes (%.2f KB/s) - %.2f%%%n",
                        fileNumber, totalFiles, totalBytesRead, speed / 1024.0,
                        (double) totalBytesRead / fileSize * 100.0);
            }

            System.out.println("File " + fileNumber + "/" + totalFiles + " downloaded successfully to: " + savePath);
        }
    }

    private static String getOdtwarzacz(String rel) {
        return String.format("https://op.wbijam.pl/odtwarzacz-%s.html", rel);
    }

    private static String getWbijamUrl(int i) {
        if (i < 100) {
            return String.format("https://op.wbijam.pl/pierwsza_seria-0%s.html", i);
        }
        return String.format("https://op.wbijam.pl/pierwsza_seria-%s.html", i);
    }

    public static void appendLineToFile(String filePath, String lineToAppend) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(lineToAppend);
            writer.newLine();  // Add a new line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> loadMissingEpisodes() {
        return loadFile("missing.txt").stream()
                .map(Integer::valueOf)
                .toList();
    }

    public static List<String> loadMp4Links() {
        return loadFile("lista.txt");
    }

    private static List<String> loadFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                clearFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static void clearFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void killFirefoxAndGeckoDriver() {
        try {
            // Kill Firefox
            ProcessBuilder firefoxProcessBuilder = new ProcessBuilder("taskkill", "/F", "/IM", "firefox.exe");
            Process firefoxProcess = firefoxProcessBuilder.start();
            int firefoxExitCode = firefoxProcess.waitFor();

            if (firefoxExitCode == 0) {
                System.out.println("Firefox has been terminated.");
            } else {
                System.out.println("Failed to terminate Firefox.");
            }

            // Kill GeckoDriver
            ProcessBuilder geckoDriverProcessBuilder = new ProcessBuilder("taskkill", "/F", "/IM", "geckodriver.exe");
            Process geckoDriverProcess = geckoDriverProcessBuilder.start();
            int geckoDriverExitCode = geckoDriverProcess.waitFor();

            if (geckoDriverExitCode == 0) {
                System.out.println("GeckoDriver has been terminated.");
            } else {
                System.out.println("Failed to terminate GeckoDriver.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
