
import Control.FigureControl;
import Control.KeyCodeControl;
import Control.SudokuControl;
import Model.SudokuModel;
import Model.SudokuModel.*;
import View.SudokuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Control.SudokuControl;

import static Model.SudokuModel.*;


public class Appli extends Application {
    public static void start(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        SudokuModel model = new SudokuModel();
        SudokuView view = new SudokuView();
        SudokuControl control = new SudokuControl(model, view);
        FigureControl controller = new FigureControl(view, model);
        KeyCodeControl key = new KeyCodeControl(view,model);

        view.addSliderListener(model);

        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(310);

        primaryStage.setTitle("Sudoku");
        primaryStage.getIcons().add(new Image("icon.jpg"));
        primaryStage.setScene(new Scene(view.getRoot(), 400, 400));
        primaryStage.show();

        control.start();


    }
    public static void main(String[] args) {
        launch(args);
    }

}