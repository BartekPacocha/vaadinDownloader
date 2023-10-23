package com.example.cdaVaadin.services;


import com.example.cdaVaadin.dtos.DownloadFileInfoDto;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TestService {

    private CdaDownloaderService cdaDownloaderService;

    private static final List<DownloadFileInfoDto> LIST = new ArrayList<>();

    public void testMethod(Set<Integer> episodesToDownload) {
        episodesToDownload
                .forEach(System.out::println);
    }

    public static List<DownloadFileInfoDto> getDataFromList() {
        return LIST;
    }

    public void updateGrid() {
        LIST.add(createData());
    }

    private DownloadFileInfoDto createData() {
        return DownloadFileInfoDto.builder()
                .fileNumber(RandomStringUtils.randomAlphabetic(10))
                .fileName(RandomStringUtils.randomAlphabetic(10))
                .fileSize(RandomUtils.nextLong(1000, 2000))
                .totalBytesRead(RandomUtils.nextLong(1000, 2000))
                .downloadSpeed(RandomUtils.nextDouble(1, 5))
                .downloadProgress(RandomUtils.nextDouble(1, 100))
                .build();
    }

}
