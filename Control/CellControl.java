package Control;

import View.SudokuView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import static Control.SudokuControl.convertCoordonnate;
import static Model.SudokuModel.existInList3D;
import static Model.SudokuModel.lastSelectedCell;

public class CellControl implements EventHandler<MouseEvent> {
    private final SudokuView view;
    public final int row;
    public final int col;

    public static int[] lastCoords;
    public CellControl(SudokuView view, int row, int col) {
        this.view = view;
        this.row = row;
        this.col = col;
    }

    @Override
    public void handle(MouseEvent event) {
        boolean ok =existInList3D(row,col);
        view.handleCellClick((Label) event.getSource(),ok);
        lastSelectedCell = convertCoordonnate(row,col);
        lastCoords = new int[]{row,col};
    }

}