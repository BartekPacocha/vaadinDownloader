package com.example.cdaVaadin.views;

import com.example.cdaVaadin.listeners.ProgressListener;
import com.example.cdaVaadin.services.FileDownloadService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("file-download")
public class FileDownloadView extends VerticalLayout implements ProgressListener {

    private Div fileNameLabel = new Div();
    private Div progressLabel = new Div();// Initialize with a default value
    private FileDownloadService fileDownloadService;

    public FileDownloadView(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
        add(fileNameLabel, progressLabel); // Add labels to the view

        // Initiate file download
        String fileName = "example.txt"; // Replace with the actual file name
        fileDownloadService.downloadFile(fileName, this);
    }

    @Override
    public void onProgressUpdate(String fileName, int progress) {
        UI.getCurrent().access(() -> {
            fileNameLabel.setText("File Name: " + fileName);
            progressLabel.setText("Progress: " + progress + "%");
        });

        if (progress == 100) {
            // Download completed
            progressLabel.setText("Progress: 100% (Download Complete)");
        }
    }
}


