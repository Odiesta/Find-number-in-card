package com.odiestas;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    @FXML
    private Label numberToSearch;
    @FXML
    private FlowPane cardsArea;
    @FXML
    private Label cardsClicked;
    private int counter = 0;
    private String theNumber;

    public void initialize() {
        starterCard();
    }

    public void starterCard() {
        List<Integer> numbers = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 63; i++) {
            numbers.add(rand.nextInt(100));
        }

        for (int i : numbers) {
            cardsArea.getChildren().add(createCard(i));
        }

        numberToSearch.setText("Find number: " +
                numbers.get(new Random().nextInt(numbers.size() - 1)));

        theNumber = numberToSearch.getText().substring(13);

    }

    public StackPane createCard(int number) {

        StackPane stack = new StackPane();
        Rectangle rectangle = new Rectangle(30.0, 25.0);
        rectangle.setFill(Color.GRAY);
        rectangle.setArcHeight(10.0);
        rectangle.setArcWidth(10.0);

        Text text = new Text("" + number);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        text.setFill(Color.WHITE);

        stack.getChildren().addAll(rectangle, text);

        return stack;
    }

    @FXML
    public void startGame() {
        for (int i = 0; i < cardsArea.getChildren().size(); i++) {
            StackPane stack = (StackPane) cardsArea.getChildren().get(i);
            stack.getChildren().add(new Rectangle(30.0, 25.0, Color.BLACK));

            stack.setOnMouseClicked(event -> {

                FadeTransition trans = new FadeTransition(Duration.seconds(1), stack.getChildren().get(2));
                trans.setFromValue(1.0);
                trans.setToValue(0);
                trans.play();

                counter++;

                Text text1 = (Text) stack.getChildren().get(1);
                if (text1.getText().equals(theNumber)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("You have found the number " + theNumber);
                    alert.showAndWait();
                    Platform.exit();
                }
                cardsClicked.setText("Counter: " + counter);
            });
        }

        FXCollections.shuffle(cardsArea.getChildren());
    }
}
