package game;

import java.util.Random;

public class Board {
   // private final int CELL_SIZE = 15;
    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private int allCells;
    private int uncovered;

    public Board() {
        this.inGame = true;
    }

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private int N_MINES = 10;
    private int N_ROWS = 8;
    private int N_COLS = 8;

    //private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    //private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;


    public int[] getField() {
        return field;
    }

    public boolean isInGame() {
        return inGame;
    }

    public int getN_MINES() {
        return N_MINES;
    }

    public void setN_MINES(int n_MINES) {
        N_MINES = n_MINES;
    }

    public void setN_ROWS(int n_ROWS) {
        N_ROWS = n_ROWS;
    }

    public void setN_COLS(int n_COLS) {
        N_COLS = n_COLS;
    }

    public int getN_ROWS() {
        return N_ROWS;
    }

    public int getN_COLS() {
        return N_COLS;
    }

    public int getMinesLeft() {
        return minesLeft;
    }

    public boolean newGame(){

        int cell;

        Random random = new Random();

        inGame = true;
        minesLeft = N_MINES;

        allCells = N_ROWS * N_COLS;
        uncovered = allCells;

        field = new int[allCells];

        for(int i = 0 ; i < allCells ; i++) {
            field[i] = COVER_FOR_CELL;
        }

        int i = 0;
        while(i<N_MINES){
            int position = (int) ( allCells * random.nextDouble());
            if((position < allCells) && field[position] != COVERED_MINE_CELL){
                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;
                if(current_col > 0){
                    cell = position -1;
                    if(cell >= 0)
                        if(field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;

                    cell = position - N_COLS - 1;

                    if(cell >= 0)
                        if(field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;

                    cell = position + N_COLS - 1;
                    if(cell < allCells)
                        if(field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                }
                cell = position - N_COLS;
                if(cell >= 0){
                    if(field[cell] != COVERED_MINE_CELL)
                        field[cell] += 1;
                }
                cell = position + N_COLS;
                if(cell < allCells){
                    if(field[cell] != COVERED_MINE_CELL)
                        field[cell] += 1;
                }

                if(current_col < (N_COLS -1)){
                    cell = position + 1;
                    if(cell < allCells){
                        if(field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                    }
                    cell = position + N_COLS + 1;
                    if(cell < allCells){
                        if(field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                    }
                    cell = position - N_COLS + 1 ;
                    if(cell >= 0)
                        if(field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                }
            }
        }
        return false;
    }

    public void showTable(){
        for(int i = 0 ; i < allCells ;i ++){
            if(i % N_COLS == 0 )
                System.out.println();
            System.out.print(field[i] + " ");
        }

    }

    public int uncover(int x,int y) throws NullPointerException{
        System.out.println("AA");
        if(!inGame){
           // newGame();
            return 0 ;
        }
        if((x < N_ROWS) && (y <N_COLS) && x >= 0 && y >= 0){
                if(field[x * N_COLS + y] > MINE_CELL){

                    if(field [x*N_COLS + y] <= COVERED_MINE_CELL && field [x*N_COLS + y] > MINE_CELL){
                        field [x*N_COLS + y] -= COVER_FOR_CELL;
                        uncovered --;

                        if(field [x*N_COLS + y] == MINE_CELL){
                            inGame = false;
                            return 0;//Lose
                        }

                        if(field[x*N_COLS + y] == EMPTY_CELL){
                            findEmptyCells(x,y);
                        }

                    }
                }
        }
       // showTable();
        if(checkIfWin()){
            return 2;// WIN
        }else {
            return 1;// Jocul continua
        }
/**<<<<<<< HEAD:src/Game/Board.java


=======
>>>>>>> 645ee085500dfc3590c7237d7a162090399c47bf:AndreiProiectOOP/src/game/Board.java*/
    }

    public void uncoverAll(){
        for(int i = 0 ;i < allCells ; i ++){
            if(field[i] > MINE_CELL && field[i] <= COVERED_MINE_CELL)
                field[i] -= COVER_FOR_CELL;
        }
    }
    public void mark(int x,int y){
        if(!inGame){
            // newGame();
            return;
        }

        if((x < N_ROWS) && (y <N_COLS)){
            if(field[x*N_COLS + y] <= COVERED_MINE_CELL) {
                if(minesLeft > 0){
                    if(field[x*N_COLS + y] == 0)
                    field[x*N_COLS + y] += MARK_FOR_CELL;
                    minesLeft -- ;
                }
            }else {
                field[x*N_COLS + y] -= MARK_FOR_CELL;
                minesLeft ++;
            }

        }
        //showTable();

    }

    public void findEmptyCells(int x,int y){

        if(x >= 0 && y >= 0) {

            int cell;
            int position = x * N_COLS + y;
            if (y > 0) {
                cell = position - N_COLS - 1;
                if (cell >= 0 && cell < allCells) {
                    if (field[cell] > MINE_CELL) {
                        field[cell] -= COVER_FOR_CELL;
                        uncovered--;
                        if (field[cell] == EMPTY_CELL) {
                            findEmptyCells(x - 1, y - 1);
                        }
                    }
                }
                cell = position - 1;
                if (cell >= 0 && cell < allCells) {
                    if (field[cell] > MINE_CELL) {
                        field[cell] -= COVER_FOR_CELL;
                        uncovered--;
                        if (field[cell] == EMPTY_CELL) {
                            findEmptyCells(x, y - 1);
                        }
                    }
                }
                cell = position + N_COLS - 1;
                if (cell >= 0 && cell < allCells) {
                    if (field[cell] > MINE_CELL) {
                        field[cell] -= COVER_FOR_CELL;
                        uncovered--;
                        if (field[cell] == EMPTY_CELL) {
                            findEmptyCells(x + 1, y - 1);
                        }
                    }
                }
            }

            cell = position - N_COLS;
            if (cell >= 0 && cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    uncovered--;
                    if (field[cell] == EMPTY_CELL) {
                        findEmptyCells(x - 1, y);
                    }
                }
            }

            cell = position + N_COLS;
            if (cell >= 0 && cell < allCells) {
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    uncovered--;
                    if (field[cell] == EMPTY_CELL) {
                        findEmptyCells(x + 1, y);
                    }
                }
            }
            if (y < (N_COLS - 1)) {
                cell = position - N_COLS + 1;
                if (cell >= 0 && cell < allCells) {
                    if (field[cell] > MINE_CELL) {
                        field[cell] -= COVER_FOR_CELL;
                        uncovered--;
                        if (field[cell] == EMPTY_CELL) {
                            findEmptyCells(x - 1, y + 1);
                        }
                    }
                }

                cell = position + N_COLS + 1;
                if (cell >= 0 && cell < allCells) {
                    if (field[cell] > MINE_CELL) {
                        field[cell] -= COVER_FOR_CELL;
                        uncovered--;
                        if (field[cell] == EMPTY_CELL) {
                            findEmptyCells(x + 1, y + 1);
                        }
                    }
                }

                cell = position + 1;
                if (cell >= 0 && cell < allCells) {

                    if (field[cell] > MINE_CELL) {

                        field[cell] -= COVER_FOR_CELL;
                        uncovered--;
                        if (field[cell] == EMPTY_CELL) {
                            findEmptyCells(x, y + 1);
                        }
                    }
                }
            }
        }
    }

    private boolean checkIfWin(){
        if(uncovered == N_MINES )
            return true;
        else return false;
    }



}
