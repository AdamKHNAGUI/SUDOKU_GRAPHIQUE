package Control;

import Model.SudokuModel;
import View.SudokuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

import static Control.CellControl.lastCoords;
import static Model.SudokuModel.*;

import javafx.scene.control.Control;

public class FigureControl implements EventHandler<ActionEvent> {
    private final SudokuView view;
    private final SudokuModel model;
    public FigureControl(SudokuView view, SudokuModel model) {
        this.view = view;
        this.model = model;

        view.btn.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {

            String text = view.text.getText();
            if (text == null || text.trim().isEmpty()) {
                throw new NullPointerException("Veuillez écrire un chiffre à placer");
            }

            int figure;
            try {
                figure = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Veuillez écrire un chiffre valide");
            }
            if (lastCoords == null){
                throw new NullPointerException("Veuillez sélectionner une case");
            }


            if (!existInList3D(lastCoords[0], lastCoords[1])) {
                setCell(lastSelectedCell, figure);
                view.updateBoard(model);

                if (correctBoard(board)) {
                    System.out.println("Bien joué :D");
                }
            } else {
                throw new IllegalArgumentException("Cette case ne peut pas accueillir un nouveau chiffre");
            }
        } catch (IllegalArgumentException i) {
            creerDialogError("Erreur Utilisateur", "Coordonnée Interdite", i.getMessage());

        } catch (NullPointerException n) {
            creerDialogError("Erreur Utilisateur", "Chiffre Inconnu", n.getMessage());
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
