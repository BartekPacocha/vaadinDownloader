package com.example.cdaVaadin.views;

import com.example.cdaVaadin.dtos.DownloadFileInfoDto;
import com.example.cdaVaadin.services.TestService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.example.cdaVaadin.services.TestService.*;

@Route("test")
public class TestView extends VerticalLayout {

    private final Button buttonStopUpdate = new Button("Stop update");
    private final Button buttonStartUpdate = new Button("Start update");
    private final Button buttonPlusInterval = new Button("+");
    private final Button buttonMinusInterval = new Button("-");

    private final Button buttonStartCreateFakeData = new Button("Start fake data");
    private final Button buttonStopCreateFakeData = new Button("Stop fake data");

    private final TextArea textArea = new TextArea("Episode number");

    Span span = new Span("5000");

    private static Grid<DownloadFileInfoDto> grid;

    private final ListDataProvider<DownloadFileInfoDto> dataProvider = new ListDataProvider<>(getDataFromList());


    private ScheduledExecutorService executor;

    @Autowired
    private TestService testService;

    public TestView() {
        this.setupGrid();
        this.setupDownloadSection();
        this.setupButtons();
        this.setupCreateFakeDataButtons();

        buttonStopUpdate.addClickListener(e -> stopUpdate());

        add(buttonStopUpdate);

        UI.getCurrent().setPollInterval(2000);
        Registration registration = UI.getCurrent().addPollListener(e -> refreshGridData());
    }

    private void setupGrid() {
        grid = new Grid<>(DownloadFileInfoDto.class, false);
        grid.setAllRowsVisible(true);
        grid.setDataProvider(dataProvider);
        grid.addColumn(DownloadFileInfoDto::getFileNumber).setHeader("fileNumber");
        grid.addColumn(DownloadFileInfoDto::getFileName).setHeader("fileName");
        grid.addColumn(DownloadFileInfoDto::getFileSize).setHeader("fileSize");
        grid.addColumn(DownloadFileInfoDto::getTotalBytesRead).setHeader("totalBytesRead");
        grid.addColumn(DownloadFileInfoDto::getDownloadSpeed).setHeader("downloadSpeed");
        grid.addColumn(DownloadFileInfoDto::getDownloadProgress).setHeader("downloadProgress");

        add(grid);
    }

    private void setupDownloadSection() {
        Button downloadEpisodeButton = new Button("Download episode");

        downloadEpisodeButton.addClickListener(e -> startDownloadingEpisode());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(textArea, downloadEpisodeButton);
        add(horizontalLayout);
    }

    private void startDownloadingEpisode() {
        Integer episodeNumber = Integer.valueOf(textArea.getValue());

        testService.testMethod(episodeNumber);
    }

    private void setupButtons() {
        buttonStartUpdate.addClickListener(e -> startUpdate());
        buttonPlusInterval.addClickListener(e -> plusInterval());
        buttonMinusInterval.addClickListener(e -> minusInterval());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(buttonStartUpdate, buttonPlusInterval, buttonMinusInterval, span);

        add(horizontalLayout);
    }

    private void setupCreateFakeDataButtons() {
        buttonStartCreateFakeData.addClickListener(e -> startCreateFakeData());
        buttonStopCreateFakeData.addClickListener(e -> stopCreateFakeData());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(buttonStartCreateFakeData, buttonStopCreateFakeData);

        add(horizontalLayout);
    }

    private void startCreateFakeData() {
        startUpdateFakeData();
    }

    private void stopCreateFakeData() {
        stopUpdateFakeData();
    }

    private void refreshGridData() {
        List<DownloadFileInfoDto> dataFromList = getDataFromList();
        List<DownloadFileInfoDto> copyOfData = new ArrayList<>(dataFromList);

        UI currentUI = UI.getCurrent();
        if (currentUI != null) {
            UI.getCurrent().access(() -> {
                dataProvider.getItems().clear();
                dataProvider.getItems().addAll(copyOfData);
                dataProvider.refreshAll();
            });
        }
    }

    private void stopUpdate() {
        UI.getCurrent().setPollInterval(-1);
        span.setText(String.valueOf(getPollInterval()));
    }

    private void startUpdate() {
        UI.getCurrent().setPollInterval(5000);
        span.setText(String.valueOf(getPollInterval()));
    }

    private void plusInterval() {
        int pollInterval = getPollInterval();

        UI.getCurrent().setPollInterval(pollInterval + 1000);
        span.setText(String.valueOf(getPollInterval()));
    }

    private void minusInterval() {
        int pollInterval = getPollInterval();

        UI.getCurrent().setPollInterval(pollInterval - 1000);
        span.setText(String.valueOf(getPollInterval()));
    }

    private int getPollInterval() {
        return UI.getCurrent().getPollInterval();
    }
}
