package com.example.cdaVaadin.services;


import com.example.cdaVaadin.dtos.DownloadFileInfoDto;
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

    private static Boolean CREATE_FAKE_DATA = false;

    public void testMethod(Set<Integer> episodesToDownload) {
        episodesToDownload
                .forEach(System.out::println);
    }

    public static List<DownloadFileInfoDto> getDataFromList() {
        return LIST;
    }

    public void updateGrid() {
        if (CREATE_FAKE_DATA) {
            LIST.add(createData());
        }
    }

    public static void startUpdateFakeData() {
        CREATE_FAKE_DATA = true;
    }

    public static void stopUpdateFakeData() {
        CREATE_FAKE_DATA = false;
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
