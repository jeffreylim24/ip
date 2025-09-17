package mumbo.app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mumbo.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Mumbo mumbo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/UserPixelArt.png"));
    private Image mumboImage = new Image(this.getClass().getResourceAsStream("/images/MumboPic.png"));

    /**
     * Initializes the main window and binds the scroll pane to the dialog container height.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Show welcome message when GUI starts
        String welcomeMessage = new Ui().getWelcomeMessage();
        dialogContainer.getChildren().add(
            DialogBox.getDukeDialog(welcomeMessage, mumboImage)
        );
    }

    /** Injects the Mumbo instance */
    public void setMumbo(Mumbo m) {
        mumbo = m;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Mumbo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mumbo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, mumboImage)
        );
        userInput.clear();

        // Check if this was a final goodbye message and exit if needed
        if (mumbo.shouldExit()) {
            // Delay the exit slightly so the user can see the goodbye message
            new Thread(() -> {
                try {
                    Thread.sleep(1500); // Wait 1.5 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(() -> Platform.exit());
            }).start();
        }
    }
}
