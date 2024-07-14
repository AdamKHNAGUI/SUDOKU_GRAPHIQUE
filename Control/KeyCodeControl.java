package Control;

import Model.SudokuModel;
import View.SudokuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static Control.CellControl.lastCoords;

import static Control.SudokuControl.convertCoordonnate;
import static Model.SudokuModel.setCell;

public class KeyCodeControl implements EventHandler<ActionEvent>{
    private final SudokuView view;
    private final SudokuModel model;
    public KeyCodeControl(SudokuView view, SudokuModel model) {
        this.view = view;
        this.model = model;


        view.getRoot().addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyEvent);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // Code to execute when an action event is triggered
    }

    private void newState(int[] pos,int fig){
        view.setText(fig);
        setCell(pos, fig);
        view.updateBoard(model);
    }

    private void handleKeyEvent(KeyEvent event) {
        KeyCode code = event.getCode();
        try {
            if (lastCoords != null) {
                int[] pos = convertCoordonnate(lastCoords[0],lastCoords[1]);
                switch (code) {
                    case NUMPAD1:
                        newState(pos,1);
                        System.out.println("Numpad 1 pressed");
                        break;
                    case NUMPAD2:
                        newState(pos,2);
                        System.out.println("Numpad 2 pressed");
                        break;
                    case NUMPAD3:
                        newState(pos,3);
                        System.out.println("Numpad 3 pressed");
                        break;
                    case NUMPAD4:
                        newState(pos,4);
                        System.out.println("Numpad 4 pressed");
                        break;
                    case NUMPAD5:
                        newState(pos,5);
                        System.out.println("Numpad 5 pressed");
                        break;
                    case NUMPAD6:
                        newState(pos,6);
                        System.out.println("Numpad 6 pressed");
                        break;
                    case NUMPAD7:
                        newState(pos,7);
                        System.out.println("Numpad 7 pressed");
                        break;
                    case NUMPAD8:
                        newState(pos,8);
                        System.out.println("Numpad 8 pressed");
                        break;
                    case NUMPAD9:
                        newState(pos,9);
                        System.out.println("Numpad 9 pressed");
                        break;
                    default:
                        break;
                }
                event.consume();
            } else {
                throw new IllegalArgumentException("Veuillez sélectionner une case");

            }
        } catch (IllegalArgumentException i) {
            creerDialogError("Erreur Utilisateur", "Case non renseigné", i.getMessage());
        }
    }


    public void creerDialogError(String titre, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
