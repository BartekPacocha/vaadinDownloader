package com.example.cdaVaadin.services;

import com.example.cdaVaadin.listeners.ProgressListener;
import org.springframework.stereotype.Service;

@Service
public class FileDownloadService {

    public void downloadFile(String fileName, ProgressListener progressListener) {
        // Simulate file download
        for (int progress = 0; progress <= 100; progress += 20) {
            try {
                Thread.sleep(500); // Simulate downloading (1 second delay)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Notify progress to the listener
            progressListener.onProgressUpdate(fileName, progress);
        }
    }
}

