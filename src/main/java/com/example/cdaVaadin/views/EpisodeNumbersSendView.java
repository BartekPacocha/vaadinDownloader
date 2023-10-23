package com.example.cdaVaadin.views;

import com.example.cdaVaadin.services.TestService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


@Route("episode-add")
public class EpisodeNumbersSendView extends VerticalLayout {

    private final TextArea textInput = new TextArea("Enter Text");
    private final Button sendButton = new Button("Send");
    private final Button addNumberButton = new Button("Add number");
    private final Button clearButton = new Button("Clear episode numbers");
    private final NumberField numberInput = new NumberField("Enter Number");

    private final Set<Integer> episodesNumbers = new HashSet<>();

    @Autowired
    private TestService testService;

    public EpisodeNumbersSendView() {
        textInput.setHeight("400px");
        textInput.setWidth("800px");

        sendButton.addClickListener(e -> sendTextToBackend());
        clearButton.addClickListener(e -> clearTextInput());
        addNumberButton.addClickListener(e -> addNumberToTextArea());

        VerticalLayout containerTextAreaLayout = new VerticalLayout();
        containerTextAreaLayout.setFlexGrow(1, textInput);
        containerTextAreaLayout.setAlignItems(Alignment.CENTER);

        containerTextAreaLayout.add(textInput);

        HorizontalLayout numberInputLayout = new HorizontalLayout(numberInput, addNumberButton);
        numberInputLayout.setAlignItems(Alignment.BASELINE);

        add(containerTextAreaLayout, numberInputLayout, sendButton, clearButton);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }

    private void addNumberToTextArea() {
        if (numberInput.getValue() != null) {
            int number = numberInput.getValue().intValue();

            if (episodesNumbers.add(number)) {
                String currentText = textInput.getValue();
                String newText = currentText + number + "\n";
                textInput.setValue(newText);
            } else {
                Notification notification = Notification.show("Number already exists", 1000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            numberInput.clear();
        }
    }

    private void clearTextInput() {
        textInput.clear();
    }

    private void sendTextToBackend() {
        String text = textInput.getValue();

        testService.testMethod(episodesNumbers);

        navigateToNewView();
    }

    private void navigateToNewView() {
        getUI().ifPresent(ui -> ui.navigate("downloading-view"));
    }

}
