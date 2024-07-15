package View;

import Control.CellControl;

import Control.DifficultyControl;
import Model.SudokuModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import static Model.SudokuModel.*;


public class SudokuView extends Stage {


    public static final String BLACK = "\u001B[30m";     // Noir

    public static final String LIGHT_GRAY = "\u001B[47m"; // Gris clair
    public static final String DARK_GRAY = "\u001B[100m"; // Gris foncé
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[43m";

    public static final String CYAN = "\033[36m";

    private GridPane grid;
    public TextField text;
    public Button btn;
    private Label lastSelectedCell;
    public Slider difficulty;

    Label cell;
    private VBox root;

    public static Timeline timeline;
    private static Label timerLabel;
    private static int secondsElapsed;
    public SudokuView(){
        initWidgets();
    }


    public void initWidgets() {
        timerLabel = new Label("0:00");
        VBox time = new VBox(timerLabel);
        time.setAlignment(Pos.CENTER);
        timerLabel.setStyle("-fx-font-size: 36px;");

        root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setMaxWidth(280);
        root.setMinWidth(280);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setMaxWidth(280);
        grid.setMinWidth(280);
        grid.setStyle("-fx-font-size: 16px;");

        Label label = new Label("Chiffre :");
        text = new TextField();
        btn = new Button("Valider");

        difficulty = new Slider(1, 64, defaultMode);
        difficulty.setMajorTickUnit(8);
        difficulty.setBlockIncrement(1);
        difficulty.setShowTickMarks(true);

        difficulty.setMinWidth(grid.getMinWidth());
        difficulty.setMaxWidth(grid.getMaxWidth());



        hBox.getChildren().addAll(label, text, btn);
        root.getChildren().addAll(time,difficulty,grid, hBox);
    }

    public static void timer() {
        if (timeline != null) {
            timeline.stop();
        }
        secondsElapsed = 0;
        updateTimerLabel();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++;
            updateTimerLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    private static void updateTimerLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timerLabel.setText(String.format("%d:%02d", minutes, seconds));
        System.out.println(minutes + ":" + seconds);
    }
    public int getSliderValue(){
        return (int) difficulty.getValue();
    }

    public void updateBoard(SudokuModel model) {
        grid.getChildren().clear();
        for (int i = 0; i < SudokuModel.SUBGRID_SIZE; i++) {
            for (int j = 0; j < SudokuModel.SUBGRID_SIZE; j++) {
                for (int k = 0; k < SudokuModel.SIZE; k++) {
                    int row = (i * SudokuModel.SUBGRID_SIZE) + (k / SudokuModel.SUBGRID_SIZE);
                    int col = (j * SudokuModel.SUBGRID_SIZE) + (k % SudokuModel.SUBGRID_SIZE);

                    if (model.getValue(i, j, k) != 0) {
                        cell = new Label(String.valueOf(model.getValue(i, j, k)));
                    } else {
                        cell = new Label(" ");
                    }
                    String backgroundColor;
                    if (!correctBoard(board)) {
                        backgroundColor = ((row / SudokuModel.SUBGRID_SIZE + col / SudokuModel.SUBGRID_SIZE) % 2 == 0) ? "-fx-background-color: lightgray;" : "-fx-background-color: darkgray;";
                    } else {
                        backgroundColor = "-fx-background-color: green;";
                    }
                    cell.setStyle(

                            "-fx-border-color: black; -fx-alignment: center; -fx-min-width: 30px; -fx-min-height: 30px;" + backgroundColor
                    );

                    addCellListeners(cell, row, col);

                    grid.add(cell, col, row);
                }
            }
        }
    }


    private void addCellListeners(Label cell, int row, int col) {
        CellControl clickCell = new CellControl(this, row, col);
        cell.setOnMouseClicked(clickCell);
    }
    public void addSliderListener(SudokuModel model){
        DifficultyControl mode = new DifficultyControl(this,model);

        difficulty.valueProperty().addListener((observable, oldValue, newValue) -> {
            mode.handle(new ActionEvent());
        });
    }



    public void handleCellClick(Label cell, boolean error) {

        if (lastSelectedCell != null) {
            lastSelectedCell.setStyle(lastSelectedCell.getStyle().replace("-fx-background-color: yellow;", ""));
            lastSelectedCell.setStyle(lastSelectedCell.getStyle().replace("-fx-background-color: red;", ""));

        }

        if(!error) {
            cell.setStyle(cell.getStyle() + "-fx-background-color: yellow;");
        } else {
            cell.setStyle(cell.getStyle() + "-fx-background-color: red;");
        }
        lastSelectedCell = cell;
    }

    public VBox getRoot() {
        return root;
    }

    public void setText(int figure){
        text.setText(Integer.toString(figure));
    }

}
