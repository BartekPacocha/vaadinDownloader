package com.example.cdaVaadin.services;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerTestService {

    private TestService testService;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
//        testService.updateGrid();
//        System.out.println("scheduled");
    }

    @Scheduled(fixedRate = 1000)
    public void downloadEpisodesFromList() {
        testService.downloadEpisodeFromList();
    }

}
