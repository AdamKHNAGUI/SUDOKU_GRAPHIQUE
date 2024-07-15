package Control;

import Model.SudokuModel;
import View.SudokuView;

import java.util.Scanner;

import static Control.CellControl.lastCoords;
import static Model.SudokuModel.*;


public class SudokuControl {
    private SudokuModel model;
    private SudokuView view;

    public SudokuControl(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
        view.updateBoard(model);
    }



    public void start() {
        initBoard(defaultMode);
        view.updateBoard(model);


    }

//    public static void actionPlayer() {
//        Scanner scanner = new Scanner(System.in);
//
//        try {
//            System.out.println("Écrivez la coordonnée (ex: A1):");
//            String input = scanner.next().toUpperCase();
//            char cell = input.charAt(0);
//
//
//            int num = Character.getNumericValue(input.charAt(1));
//            if (existInList((cell),num)){
//                throw new IllegalArgumentException("Coordonnée Interdite - Cette Case ne peut pas acceuillir un nouveau chiffre");
//            }
//
//            if (cell < 'A' || cell > 'I' || num < 0 || num > 9) {
//                throw new IllegalArgumentException("Coordonnées invalide.");
//            } else {
//                lastSelectedCell = new int[]{cell- 'A', num,1};
//                displayBoard();
//
//            }
//
//
//            System.out.println("Choisissez le chiffre (1-9) :");
//            int value = scanner.nextInt();
//
//
//            if (value < 1 || value > 9) {
//                throw new IllegalArgumentException("Valeur invalide.");
//            }
//
//            System.out.println("Case : " + cell + num + " → " + value);
//            coords = convertCoordonnate(cell, num);
//            setCell(coords, value);
//        } catch (Exception e) {
//            System.err.println("Erreur : " + e.getMessage());
//        }
//    }

    public static int[] convertCoordonnate(int row, int col) {
        int ROW = row / SUBGRID_SIZE;
        int COL = col / SUBGRID_SIZE;
        int INDEX =( (row % 3) * 3) + (col % 3);
        coords = new int[]{ROW,COL,  INDEX};
//        System.out.println("Coordonate → "+ROW+COL+INDEX);
        System.out.println("COOR → "+ROW+"|"+COL+"|"+INDEX);
        return new int[]{ROW,COL,  INDEX};
    }



}
