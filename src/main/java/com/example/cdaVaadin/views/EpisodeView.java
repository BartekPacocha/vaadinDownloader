package com.example.cdaVaadin.views;

import com.example.cdaVaadin.dtos.EpisodeDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("episodes")
public class EpisodeView extends VerticalLayout {

    private static Grid<EpisodeDto> grid;

    private final ListDataProvider<EpisodeDto> dataProvider = new ListDataProvider<>(getEpisodeList());

    public EpisodeView() {
        this.setupGrid();
    }

    public List<EpisodeDto> getEpisodeList() {
        EpisodeDto episodeDto = EpisodeDto.builder()
                .episodeNumber("1")
                .downloaded(true)
                .watched(true)
                .build();

        EpisodeDto episodeDto2 = EpisodeDto.builder()
                .episodeNumber("2")
                .downloaded(false)
                .watched(false)
                .build();

        return List.of(episodeDto, episodeDto2);
    }

    private void setupGrid() {
        grid = new Grid<>(EpisodeDto.class, false);
        grid.setAllRowsVisible(true);
        grid.setDataProvider(dataProvider);

        TextField episodeNumberFilter = new TextField("Filter Episode number");
        TextField downloadedFilter = new TextField("Filter Downloaded");
        TextField watchedFilter = new TextField("Filter Watched");

        Button showUnwatchedEpisodes = new Button("Show unwatched episodes");

        // Add listener to text fields for filtering
        episodeNumberFilter.addValueChangeListener(e -> filterByEpisodeNumber(e.getValue()));
        downloadedFilter.addValueChangeListener(e -> filterByDownloaded(e.getValue()));
        watchedFilter.addValueChangeListener(e -> filterByWatched(e.getValue()));


        grid.addColumn(EpisodeDto::getEpisodeNumber).setHeader("Episode number");

        grid.addComponentColumn(item -> createEditableCheckbox(item, "Downloaded"))
                .setHeader("Downloaded");

        // Add an editable checkbox column for "Watched"
        grid.addComponentColumn(item -> createEditableCheckbox(item, "Watched"))
                .setHeader("Watched");

        grid.addComponentColumn(this::createButton).setHeader("Download");


        HorizontalLayout filterLayout = new HorizontalLayout(episodeNumberFilter, downloadedFilter, watchedFilter, showUnwatchedEpisodes);
        filterLayout.setWidthFull();
        filterLayout.setAlignItems(Alignment.END);

        // Create a VerticalLayout and add filterLayout above the grid
        VerticalLayout verticalLayout = new VerticalLayout(filterLayout, grid);
        verticalLayout.setSizeFull();
        verticalLayout.setAlignItems(Alignment.STRETCH);

        add(verticalLayout);
    }

    private Checkbox createEditableCheckbox(EpisodeDto episodeDto, String property) {
        Checkbox checkbox = new Checkbox();
        checkbox.setValue(property.equals("downloaded") ? episodeDto.isDownloaded() : episodeDto.isWatched());
        checkbox.addValueChangeListener(event -> {
            if (property.equals("downloaded")) {
                episodeDto.setDownloaded(event.getValue());
            } else {
                episodeDto.setWatched(event.getValue());
            }
            // You might want to save the changes to your backend here
        });
        return checkbox;
    }

    private Button createButton(EpisodeDto item) {
        Button button = new Button("Download episode");
        button.setIcon(new Icon(VaadinIcon.DOWNLOAD));
        button.addClickListener(event -> {
            // Handle button click logic here
            button.addClassName("button-clicked");
            Notification.show("Button clicked for Episode: " + item.getEpisodeNumber());
        });
        return button;
    }

    private void filterByEpisodeNumber(String filter) {
        dataProvider.setFilter(episodeDto ->
                episodeDto.getEpisodeNumber().toLowerCase().contains(filter.toLowerCase()));
    }

    private void filterByDownloaded(String filter) {
        dataProvider.setFilter(episodeDto ->
                String.valueOf(episodeDto.isDownloaded()).toLowerCase().contains(filter.toLowerCase()));
    }

    private void filterByWatched(String filter) {
        dataProvider.setFilter(episodeDto ->
                String.valueOf(episodeDto.isWatched()).toLowerCase().contains(filter.toLowerCase()));
    }

}
