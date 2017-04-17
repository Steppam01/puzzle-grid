/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzlegridrestruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author nevee_000
 */
public class PuzzleGridRestruct extends Application {
    private Cell selected = null;
    private HashMap<Cell, PTri> hm = new HashMap<Cell, PTri>();
    
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(700, 700);
        Label title = new Label("Stephen's Puzzle Grid");
        title.setFont(new Font("Lucida Console", 24));
        title.relocate(150, 0);
        Label dims = new Label("Dimensions:");
        dims.setFont(new Font("Lucida Console", 16));
        dims.relocate(150, 52);
        TextField input = new TextField();
        input.relocate(260, 50);
        addTextLimiter(input, 3);
        Button reset = new Button();
        reset.setText("Reset");
        reset.relocate(350, 650);
        root.getChildren().addAll(title, input, dims, reset);
        Grid rows = new Grid();
        List<Cell> list = new ArrayList<Cell>();
        input.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                String userDim;
                userDim = input.getText();
                input.clear();
                rows.setRows(Character.getNumericValue(userDim.charAt(0)));
                rows.setCols(Character.getNumericValue(userDim.charAt(2))); 

                int l = 0;
                    for(int i = 0; i < rows.getRows(); i++){
                        for(int j = 0; j < rows.getCols(); j++){
                        Cell cell = new Cell();
                        list.add(cell);
                            PTri coor = new PTri(i, j);
                            hm.put(cell, coor);
                            cell.relocate(150, 150);
                            cell.setTranslateX(50 * (l % rows.getRows()));
                            cell.setTranslateY(50 * (l / rows.getRows()));
                            root.getChildren().add(cell);
                            l++;
               }
                    }
            }
            
        });
        reset.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent ae){
                root.getChildren().clear();
                root.getChildren().addAll(title, dims, input, reset);
                selected = null;
            }
        });
        return root;
    }
    private class Cell extends StackPane{
        private Text text = new Text();
        int startX;
                int startY;
                int endX;
                int endY;
        public Cell(){
            Rectangle border = new Rectangle(50,50);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            getChildren().addAll(border,text);
            addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent) -> {
            System.out.print(hm.get(this).toString());
                if(selected == null){
                    text.setText("S");
                    selected = this;
                   char xCoor = hm.get(this).toString().charAt(0);
                   startX = Character.getNumericValue(xCoor);
                   char yCoor = hm.get(this).toString().charAt(2);
                   startY = Character.getNumericValue(yCoor);
                }
                else{
                   char xCoor = hm.get(this).toString().charAt(0);
                   endX = Character.getNumericValue(xCoor);
                   char yCoor = hm.get(this).toString().charAt(2);
                   endY = Character.getNumericValue(yCoor);
                   long ans = GridController.calculate(endX - startX, endY - startY);
                   text.setText(Long.toString(ans)); 
                }
            });
        }
       
        public boolean hasStartPoint(){
            return text.equals("S");
        }
        public boolean isEmpty(){
            return text.equals(null);
        }
    }
    public static void addTextLimiter(final TextField tf, final int maxLength){
    tf.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        }
    });
}
    @Override
    public void start(Stage primaryStage){
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

