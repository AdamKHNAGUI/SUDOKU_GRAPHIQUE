package Model;

import java.util.*;

import static Control.SudokuControl.convertCoordonnate;
import static View.SudokuView.*;

public class SudokuModel {


    public SudokuModel(){

    }

    public static final int defaultMode = 10;
    public static final int SUBGRID_SIZE = 3; // Taille de la sous-grille (3x3)
    public static final int SIZE = SUBGRID_SIZE * SUBGRID_SIZE; // Taille de la grille complète (9x9)
    public static final int[][][] board = new int[SUBGRID_SIZE][SUBGRID_SIZE][SIZE];
    public static List<int[]> initCoords;
    public static int[] coords = new int[]{0,0,0};

    private static final Random random = new Random();

    public static int[] lastSelectedCell = new int[]{0,0,0};

    public static void initBoard(int difficulty) {
        initCoords = new ArrayList<>();
        int[][] grid = new int[SIZE][SIZE];
        fillGrid(grid);
        removeCells(grid, difficulty);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i / SUBGRID_SIZE][j / SUBGRID_SIZE][(i % SUBGRID_SIZE) * SUBGRID_SIZE + (j % SUBGRID_SIZE)] = grid[i][j];
            }
        }
        initList();

    }

    private static boolean fillGrid(int[][] grid) {
        List<Integer> numbers = Arrays.asList(1,2, 3, 4, 5, 6, 7, 8, 9);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (grid[i][j] == 0) {
                    Collections.shuffle(numbers);
                    for (int num : numbers) {

                        if (isValidPlacement(grid, i, j, num)) {
                            grid[i][j] = num;

                            if (fillGrid(grid)) {

                                return true;
                            } else {

                                grid[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        int startRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int startCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void removeCells(int[][] grid, int emptyCells) {
        Random rand = new Random();
        int cellsToEmpty = emptyCells;

        while (cellsToEmpty > 0) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);

            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                cellsToEmpty--;
            }
        }
    }

    public static void displayBoard() {
        System.out.println("%  A  B  C  D  E  F  G  H  I");

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                String color = ((i / SUBGRID_SIZE + j / SUBGRID_SIZE) % 2 == 0) ? DARK_GRAY : LIGHT_GRAY;
                String font = BLACK;
                int value = board[i / SUBGRID_SIZE][j / SUBGRID_SIZE][(i % SUBGRID_SIZE) * SUBGRID_SIZE + (j % SUBGRID_SIZE)];
                if (j == lastSelectedCell[0] && i == lastSelectedCell[1] && lastSelectedCell[2] == 1) {
                    color = YELLOW;
                }
                if (value != 0) {

                    boolean inInitCoords = false;
                    for (int[] item : initCoords) {
                        if (item[0] == i / SUBGRID_SIZE && item[1] == j / SUBGRID_SIZE && item[2] == (i % SUBGRID_SIZE) * SUBGRID_SIZE + (j % SUBGRID_SIZE)) {
                            inInitCoords = true;
                            break;
                        }
                    }
                    font = !inInitCoords ? CYAN:BLACK;
                    System.out.print(color + " " + font + value + " " + color);
                } else {
                    System.out.print(color + "   ");
                }
                System.out.print(RESET);
            }
            System.out.println();
        }
    }

    public static void initList(){
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    if (board[i][j][k] != 0){
                        int[] newItem = new int[]{i, j, k};
                        initCoords.add(newItem);

                    }
                }
            }
        }
    }

    public static boolean existInList(char row, int col) {
        int[] tempTab = convertCoordonnate(row,col);
        for (int[] item : initCoords) {
            if (item[0] == tempTab[0] && item[1] == tempTab[1] && item[2] == tempTab[2]) {
//                System.out.println(row + "|" + col);
                return true;
            }
        }
        return false;
    }

    public static boolean existInList3D(int row, int col) {
//        int ROW = row / SUBGRID_SIZE;
//        int COL = col / SUBGRID_SIZE;
//        int INDEX =( (row % 3) * 3) + (col % 3);

        int[] tempTab = convertCoordonnate(row,col);


        for (int[] item : initCoords) {
            if (item[0] == tempTab[0] && item[1] == tempTab[1] && item[2] == tempTab[2]) {
//                System.out.println(row + "|" + col);
                return true;
            }
        }
        return false;
    }

    public static void setCell(int[]tab,int value){
        board[tab[0]][tab[1]][tab[2]] = value;
    }

    public int getValue(int i, int j, int k) {
        return board[i][j][k];
    }
    public static boolean correctBoard(int[][][] board){
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                Set<Integer> seenNumbers = new HashSet<>();
                for (int k = 0; k < SIZE; k++) {
                    int num = board[i][j][k];
                    if (num != 0) {
                        if (seenNumbers.contains(num)) {
                            return false;
                        }
                        seenNumbers.add(num);
                    } else {
                        return false;
                    }
                }
            }
        }
        System.out.println("Win");
        return true;
    }




}