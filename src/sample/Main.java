package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static java.awt.Color.black;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane borderPane=new BorderPane();
        borderPane.setMinHeight(400);
        borderPane.setMinWidth(700);

        VBox vbox=new VBox();
        vbox.setMinWidth(USE_COMPUTED_SIZE);
        vbox.setMinHeight(100);
        vbox.setStyle("-fx-background-color:whitesmoke; -fx-border-color:red; -fx-border-width:0 0 1 0");
        vbox.setAlignment(Pos.CENTER);
        Label gameName=new Label("GHOST BUSTER");
        gameName.setAlignment(Pos.CENTER);
        gameName.setFont(Font.font("Verdana", FontWeight.BOLD, 40));

        Text t = new Text ("Shovito Barua Soumma(1605066)");
        t.setId("fancytext");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        //t.setX(80);
        t.setY(8);
        Text t2 = new Text ("Artificial Intelligence Lab");
        t2.setId("fancytext");
        t2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        //t.setX(80);
        t.setY(10);
        //t.setTextAlignment(TextAlignment.);
        vbox.getChildren().addAll(gameName,t,t2);
//        vbox.getChildren().add(t);

        VBox vbox2=new VBox();
        vbox2.setMinHeight(USE_COMPUTED_SIZE);
        vbox2.setMinWidth(USE_COMPUTED_SIZE);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(20);

        TextField rowField=new TextField();
        rowField.setPromptText("Enter Row");
        rowField.setMaxWidth(200);


        TextField columnField=new TextField();
        columnField.setPromptText("Enter Column");
        columnField.setMaxWidth(200);

        Button button=new Button("Submit");
        Label status=new Label();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String rowString = rowField.getText();
                String columnString = columnField.getText();
                int totalRow = Integer.parseInt(rowString);
                int totalCol = Integer.parseInt(columnString);
//                if(rowString.equals("java") && columnString.equals("12345")){
//                    status.setText("success");
//                }
//                else{
//                    status.setText("wrong");
//                }
//                MainView_XXX mainViewXXX = new MainView_XXX(totalRow,totalCol);
                MainView mainView = new MainView(totalRow);
                primaryStage.close();
            }
        });



        vbox2.getChildren().addAll(rowField,columnField,button,status);



        borderPane.setTop(vbox);
        borderPane.setCenter(vbox2);

        Scene scene=new Scene(borderPane);
        primaryStage.setTitle("Ghost Buster");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

