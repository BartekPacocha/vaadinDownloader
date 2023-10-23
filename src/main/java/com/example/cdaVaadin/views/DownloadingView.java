package com.example.cdaVaadin.views;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;

@Route("downloading-view")
public class DownloadingView extends VerticalLayout {

    private ProgressBar progressBar = new ProgressBar(0.0, 100.0);

    public DownloadingView() {
        VerticalLayout containerLayout = new VerticalLayout();
        containerLayout.setSizeFull();
        containerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        containerLayout.setAlignItems(Alignment.CENTER);

        progressBar.setWidth("400px");
        containerLayout.add(progressBar);

        simulateTask();

        add(containerLayout);
    }

    private void simulateTask() {
        for (double progress = 0.0; progress <= 100.0; progress += 10.0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateProgressBar(progress);

            if (progress == 100) {
                Notification.show("Task completed!");
            }
        }
    }

    private void updateProgressBar(double progress) {
        progressBar.setValue(progress);
    }
}

