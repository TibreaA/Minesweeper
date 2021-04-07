package sample;

import game.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;

public class Controller {
    public HBox box;
    @FXML
    private Button newGame;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField mines;
    @FXML
    private TextField time;
    @FXML
    private GridPane gridPane;

    private static Board board = new Board();
    private int nrCol;
    private int nrRow;

    private int nrFlag;

    private final double lSquare = 30;

    private ImageView[][] myImage;

    private Timeline timeline;
    private int timp = 0;

    public void initialize(){
        timeline = new Timeline(new KeyFrame(Duration.millis(1000) , (ActionEvent e)->{
            timp++;
            int sec = timp % 60;
            int min = timp / 60;
            String secunde , minute;

            secunde = (sec > 9)? sec + "" : "0" + sec;
            minute = (min > 9)? min + "" : "0" + min;

            time.setText(minute + " : " + secunde);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        board.newGame();

        ImageView image = new ImageView("emo.png");
        image.setFitWidth(35);
        image.setFitHeight(35);
        newGame.setGraphic(image);

        time.setPrefWidth(60);

        nrFlag = board.getN_MINES();

        mines.setText(nrFlag + "");
        mines.setEditable(false);
        mines.setPrefWidth(60);
        time.setText("00:00");
        timp = 0;
        time.setEditable(false);

        nrCol = board.getN_COLS();
        nrRow = board.getN_ROWS();

        myImage = new ImageView[nrRow][nrCol];
        for(int i = 0 ; i < nrRow ; i++){
            for(int j = 0 ; j < nrCol ; j++){
                myImage[i][j] = new ImageView("fullSquare.png");
                myImage[i][j].setFitHeight(lSquare);
                myImage[i][j].setFitWidth(lSquare);
                gridPane.add(myImage[i][j] , i , j);
            }
        }

        //gridPane.setAlignment(Pos.CENTER);
    }

    @FXML
    public void handleClickGridPane(MouseEvent e) throws NullPointerException{

        if(board.isInGame()) {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                timeline.play();

                int x = (int) (e.getX() / 30);
                int y = (int) (e.getY() / 30);

                if (x < nrRow && y < nrCol && x >= 0 && y >= 0) {
                    int p = board.uncover(x, y);
                    update(x, y);
                    switch (p) {
                        case 0:
                            lose(x, y);
                            break;
                        case 2:
                            win(x, y);
                            break;
                    }
                }
            } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                int x = (int) (e.getX() / 30);
                int y = (int) (e.getY() / 30);

                if (x < nrCol && y < nrRow) {
                    setFlag(x, y);
                }
            }
        }
    }

    public void lose(int x , int y){
        timeline.stop();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("You lose");
        alert.setHeaderText("You lose!\n" + "Keep trying!");
        alert.show();

    }


    public void win(int x , int y){
        timeline.stop();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("You win");
        alert.setHeaderText("You win!\n" + "Congratulations!\n" + "Try a higher level!");
        alert.show();

    }

    public void newGame (){
        board.newGame();
        timeline.stop();

        nrFlag = board.getN_MINES();

        mines.setText(nrFlag + "");
        mines.setEditable(false);
        mines.setPrefWidth(40);
        time.setText("00:00");
        timp = 0;
        time.setEditable(false);

        nrCol = board.getN_COLS();
        nrRow = board.getN_ROWS();

        myImage = new ImageView[nrRow][nrCol];
        for(int i = 0 ; i < nrRow ; i++){
            for(int j = 0 ; j < nrCol ; j++){
                myImage[i][j] = new ImageView("fullSquare.png");
                myImage[i][j].setFitHeight(lSquare);
                myImage[i][j].setFitWidth(lSquare);
                gridPane.add(myImage[i][j] , i , j);
            }
        }

       // gridPane.setAlignment(Pos.CENTER);
    }

    public void setFlag(int x , int y) {
        if (myImage[x][y].getImage().getUrl().contains("flag.png")) {
            System.out.println(1);
            myImage[x][y].setImage(new Image("fullSquare.png"));
            nrFlag++;
            mines.setText(nrFlag + "");
        } else if(myImage[x][y].getImage().getUrl().contains("fullSquare.png")) {

            System.out.println(2);
            myImage[x][y].setImage(new Image("flag.png"));
            nrFlag--;
            mines.setText(nrFlag + "");
        }
    }
    public void update(int x , int y){

        int k[] = board.getField();

        for(int i = 0 ; i < nrRow ; i++){
            for(int j = 0 ; j < nrCol ; j++){
                int g = k[i * nrCol + j];
                if(g == 0){
                    myImage[i][j].setImage(new Image("emptySquare.png"));
                }else if(g >= 10){
                    if(!myImage[i][j].getImage().getUrl().contains("flag.png"))
                        myImage[i][j].setImage(new Image("fullSquare.png"));
                }else if(g == 9){
                    if(x == i && y == j){
                        myImage[i][j].setImage(new Image("mineRed.jpg"));
                    }else{
                        myImage[i][j].setImage(new Image("mine.png"));
                    }
                }else{
                    myImage[i][j].setImage(new Image(g + ".png"));
                }
                myImage[i][j].setFitHeight(lSquare);
                myImage[i][j].setFitWidth(lSquare);

            }
        }
        System.out.println("TTT");
    }

    public void setEasy() throws IOException {
        board.setN_COLS(8);
        board.setN_ROWS(8);
        board.setN_MINES(10);

        newStage(380 , 330);

    }

    public void setMedium() throws IOException {
        board.setN_COLS(13);
        board.setN_ROWS(15);
        board.setN_MINES(40);

        newStage(590 , 530);
    }

    public void setHard() throws IOException {
        board.setN_COLS(16);
        board.setN_ROWS(30);
        board.setN_MINES(99);

        newStage(740, 1000);
        newGame();
    }

    public void newStage(int x , int y) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Platform UTCN");
        stage.setScene(new Scene(root, x, y));
        stage.setMinHeight(x);
        stage.setMinWidth(y);
        stage.setMaxHeight(x);
        stage.setMaxWidth(y);
        stage.show();
    }

    public void pageWeb() throws URISyntaxException, IOException {
        URI uri = new URI("https://en.wikipedia.org/wiki/Minesweeper_(video_game)");

        Desktop.getDesktop().browse(uri);
    }

}
