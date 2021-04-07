package game;

import game.Board;

import java.util.Scanner;

public class MainTest {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board();
        board.newGame();
        board.showTable();

        int x=-1,y=-1;
        while(x != 0){
            System.out.println("Give x : ");
            x= scanner.nextInt();
            System.out.println("Give y : ");
            y=scanner.nextInt();
            x = board.uncover(x,y);
            if(x==2){
                System.out.println("Game Won");
                x = 0;
            }
        }

    }

}
