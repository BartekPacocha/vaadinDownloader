package com.example.cdaVaadin.services;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

}
