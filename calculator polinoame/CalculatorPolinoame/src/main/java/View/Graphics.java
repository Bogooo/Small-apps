package View;

import Controller.Controller;
import Model.Operation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Graphics extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox();
        Scene scene=new Scene(root,1140,370);

        Label pLabel1 = new Label("First polynomial:");
        pLabel1.setStyle(" -fx-font-size: 25 ; -fx-text-fill: white");

        TextField p1 = new TextField();
        p1.setPromptText(".......ex: 3X^2+2");
        p1.setAlignment(Pos.CENTER);
        p1.setMaxSize(535,50);
        p1.setPrefSize(500,45);
        p1.setStyle("-fx-font-size: 20;-fx-text-fill: white; -fx-border-radius: 10 ; -fx-background-color: #282828; -fx-background-radius: 10");

        Label pLabel2 = new Label("Second polynomial:");
        pLabel2.setStyle("-fx-font-size: 25;-fx-text-fill: white");
        TextField p2 = new TextField();
        p2.setPromptText(".......ex: 2X^1-7");
        p2.setAlignment(Pos.CENTER);
        p2.setMaxSize(535,50);
        p2.setPrefSize(500,45);
        p2.setStyle("-fx-font-size: 20; -fx-text-fill: white;-fx-border-radius: 10 ; -fx-background-color: #282828; -fx-background-radius: 10");

        HBox radios = new HBox();
        RadioButton div = createRadioButton("Division");
        RadioButton mul = createRadioButton("Multiplication");
        RadioButton add = createRadioButton("Addition");
        RadioButton sub = createRadioButton("Subtraction");
        RadioButton intr = createRadioButton("Integral");
        RadioButton der = createRadioButton("Derivative");


        VBox addSub = new VBox(add,sub);
        addSub.setSpacing(15);
        VBox mulDiv = new VBox(mul,div);
        mulDiv.setSpacing(15);
        VBox derIn=new VBox(intr,der);
        derIn.setSpacing(15);

        radios.getChildren().addAll(addSub,mulDiv,derIn);
        radios.setSpacing(25);
        radios.setAlignment(Pos.CENTER);

        Operation type = new Operation();
        tipOper(type,add,sub,mul,div,intr,der);


        Label resLabel = new Label("Result:");
        resLabel.setStyle("-fx-font-size: 25;-fx-text-fill: white");
        resLabel.setPrefSize(500,30);
        resLabel.setAlignment(Pos.CENTER_LEFT);

        Label result= new Label();
        result.setStyle("-fx-text-fill: white; -fx-font-size: 25; -fx-border-radius: 10 ; -fx-background-color: #282828; -fx-background-radius: 10");
        result.setPrefSize(500,80);
        result.setMaxSize(535,100);
        result.setAlignment(Pos.CENTER);

        Label infoLabel = new Label("The polynomials should look like this: +/-coefX^pow (all are optional, \nand you can use x instead of X). For example, is correct to write: 3X,\n  -2.0,  4.67x^4,  -X^4. Integral and derivative works for first polynomial");
        infoLabel.setStyle("-fx-font-size: 15;-fx-text-fill: rgba(255,255,255,0.67)");

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-font-size: 15;-fx-text-fill: red");

        Button calculate = new Button("Calculate");
        calculate.setStyle("-fx-font-size: 15; -fx-text-fill: white; -fx-font-weight:bold; -fx-background-radius: 15;-fx-background-color: #656464");
        calculate.setPrefSize(220,65);
        calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // trimit la modul pt procesare si afisez rezultatul
                errorLabel.setText("");
                try {
                     String res = Controller.calculateOperations(p1.getText(),p2.getText(), type);
                     if(res.equals(""))
                         res = "0";
                     result.setText(res);
                } catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                }
            }
        });

        VBox inputs = new VBox();
        VBox outputs = new VBox();
        inputs.getChildren().addAll(pLabel1,p1,pLabel2,p2,radios);
        inputs.setSpacing(20);
        inputs.setAlignment(Pos.TOP_CENTER);

        outputs.getChildren().addAll(resLabel,result,infoLabel,errorLabel,calculate);
        outputs.setSpacing(20);
        outputs.setAlignment(Pos.TOP_CENTER);

        root.setStyle("-fx-background-color: #1f1f1f");
        root.setPadding(new Insets(20,20,20,20));
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.getChildren().addAll(inputs,outputs);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Polynomial Calculator");

        primaryStage.show();
    }

    public void tipOper(Operation op, RadioButton add, RadioButton sub, RadioButton mul, RadioButton div, RadioButton intr, RadioButton der){
        add.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sub.setSelected(false);
                mul.setSelected(false);
                div.setSelected(false);
                intr.setSelected(false);
                der.setSelected(false);
                op.setTip(1);
            }
        });

        sub.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                add.setSelected(false);
                mul.setSelected(false);
                div.setSelected(false);
                intr.setSelected(false);
                der.setSelected(false);
                op.setTip(2);
            }
        });

        mul.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sub.setSelected(false);
                add.setSelected(false);
                div.setSelected(false);
                intr.setSelected(false);
                der.setSelected(false);
                op.setTip(3);
            }
        });

        div.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sub.setSelected(false);
                mul.setSelected(false);
                add.setSelected(false);
                intr.setSelected(false);
                der.setSelected(false);
                op.setTip(4);
            }
        });

        intr.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sub.setSelected(false);
                mul.setSelected(false);
                div.setSelected(false);
                add.setSelected(false);
                der.setSelected(false);
                op.setTip(5);
            }
        });

        der.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                sub.setSelected(false);
                mul.setSelected(false);
                div.setSelected(false);
                add.setSelected(false);
                intr.setSelected(false);
                op.setTip(6);
            }
        });
    };



    public RadioButton createRadioButton(String name)
    {
        RadioButton rad = new RadioButton(name);
        rad.setPrefSize(160,40);
        rad.setAlignment(Pos.CENTER);
        rad.setStyle(" -fx-font-size: 15; -fx-font-weight:bold;-fx-text-fill: white; -fx-background-color: #ff7f00 ; -fx-background-radius: 15");

        return rad;
    }
}

