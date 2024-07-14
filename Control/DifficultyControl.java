package Control;

import Model.SudokuModel;
import View.SudokuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import View.SudokuView.*;

import static Model.SudokuModel.initBoard;

public class DifficultyControl implements EventHandler<ActionEvent> {

    private final SudokuView view;
    private SudokuModel model;
    public int difficulty;

    public DifficultyControl(SudokuView view,SudokuModel model){
        this.view=view;
        this.model=model;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        if (view.getSliderValue() != difficulty){
            difficulty = view.getSliderValue();
            System.out.println("Difficulté à ("+difficulty+")");
            initBoard(difficulty);
            view.updateBoard(model);
        }
    }
}
