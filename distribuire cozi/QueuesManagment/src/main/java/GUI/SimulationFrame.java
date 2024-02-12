package GUI;

import BussinessLogic.SelectionPolicy;
import BussinessLogic.SimulatorManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SimulationFrame extends Application {

    private ObservableList<String> queues;
    private ObservableList<String> queuesDWL;
    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private boolean allOrCurrentTimes;
    private RadioButton allOrCurrent;
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox();
        Scene scene = new Scene(root,900,420);
        scene.getStylesheets().add("file:///home/user/Info/PT/PT2023_30223_Chiorean_Bogdan_Assigment_2/QueuesManagment/src/main/java/GUI/design.css");
        Button dwlButton = new Button("Download");
        allOrCurrent = new RadioButton();
        allOrCurrent.setText("All-Current");
        allOrCurrent.setStyle("-fx-font-size: 15;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-background-color: linear-gradient(to top, #00a5ff, #2cb3fc);\n" +
                "    -fx-border-color: #0e9cf8;\n" +
                "    -fx-border-radius: 20;\n" +
                "    -fx-background-radius: 20;");
        allOrCurrent.setPrefSize(120,30);

        dwlButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = new File("Test.txt");
                try {
                    file.createNewFile();
                    FileWriter myWriter = new FileWriter("Test.txt");
                    String text = "";
                    text+="N= "+ numberOfClients +'\n'+"Q= "+numberOfServers +"\ntmaxSimulation= "+timeLimit + "\n[tminArrival,tmaxArrival]= ";
                    text+= "["+minArrivalTime+", "+ maxArrivalTime +"]\n[tminService,tmaxService]= ["+ minProcessingTime+", "+maxProcessingTime+"]\n";

                    for(String s:queuesDWL)
                    {
                        text+=s+'\n';
                    }
                    myWriter.write(text);
                    myWriter.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });


        VBox inputs = inputVBox();
        Label titleLabel=new Label("Simulation");
        titleLabel.getStyleClass().add("title");
        HBox controls = new HBox();
        controls.getChildren().addAll(dwlButton,allOrCurrent);
        controls.setSpacing(15);
        VBox outputs = new VBox();
        outputs.setSpacing(20);
        outputs.setAlignment(Pos.CENTER);
        outputs.getChildren().addAll(titleLabel,controls,outputVBox());
        outputs.setId("out_container");
        outputs.setPadding(new Insets(20,20,20,20));


        root.setSpacing(20);
        root.setId("root");
        root.getChildren().addAll(inputs,outputs);
        root.setPadding(new Insets(20,20,20,20));
        primaryStage.setScene(scene);
        primaryStage.setTitle("QueueDistribution");
        primaryStage.show();
    }

    private VBox inputVBox()
    {
        VBox root =new VBox();
        Label titleLabel = new Label("Setup");
        titleLabel.getStyleClass().add("title");

        VBox labels = new VBox();
        Label nrTasksLabel = new Label("Number of clients");
        Label nrServersLabel = new Label("Number of queues");
        Label timeLimitLabel = new Label("Simulation time");
        Label timeArrivalsLabel = new Label("Arrival interval");
        Label timeProccLabel = new Label("Service interval");
        Label policyLabel = new Label("Select policy");
        labels.getChildren().addAll(nrTasksLabel,nrServersLabel,timeLimitLabel,timeArrivalsLabel,timeProccLabel,policyLabel);
        labels.setSpacing(21);
        labels.setAlignment(Pos.CENTER_LEFT);

        VBox fields = new VBox();
        TextField taskNO = new TextField();
        taskNO.setPromptText("...");
        taskNO.setAlignment(Pos.CENTER);
        taskNO.setPrefSize(120,30);
        TextField serverNo = new TextField();
        serverNo.setPromptText("...");
        serverNo.setAlignment(Pos.CENTER);
        serverNo.setPrefSize(120,30);
        TextField timeLim = new TextField();
        timeLim.setPromptText("...");
        timeLim.setAlignment(Pos.CENTER);
        timeLim.setPrefSize(120,30);

        Label lineLabel1 = new Label("-");
        Label lineLabel2 = new Label("-");
        HBox arrivalInterval = new HBox();
        TextField timeAr1 = new TextField();
        timeAr1.setPromptText("...");
        timeAr1.setAlignment(Pos.CENTER);
        timeAr1.setPrefSize(120,30);
        TextField timeAr2 = new TextField();
        timeAr2.setPromptText("...");
        timeAr2.setAlignment(Pos.CENTER);
        timeAr2.setPrefSize(120,30);
        arrivalInterval.getChildren().addAll(timeAr1,lineLabel1,timeAr2);
        arrivalInterval.setAlignment(Pos.CENTER);

        HBox proccesInterval = new HBox();
        TextField timePr1 = new TextField();
        timePr1.setPromptText("...");
        timePr1.setAlignment(Pos.CENTER);
        timePr1.setPrefSize(120,30);
        TextField timePr2 = new TextField();
        timePr2.setPromptText("...");
        timePr2.setAlignment(Pos.CENTER);
        timePr2.setPrefSize(120,30);
        proccesInterval.getChildren().addAll(timePr1,lineLabel2,timePr2);
        proccesInterval.setAlignment(Pos.CENTER);

        ObservableList<String> policyObservable = FXCollections.observableArrayList("Shortest Queue","Shortest Time");
        ComboBox<String> policySel = new ComboBox<String>(policyObservable);
        fields.getChildren().addAll(taskNO,serverNo,timeLim,arrivalInterval,proccesInterval,policySel);
        fields.setSpacing(10);
        fields.setAlignment(Pos.CENTER_LEFT);

        HBox upData = new HBox();
        upData.getChildren().addAll(labels,fields);
        upData.setSpacing(10);

        VBox buttons = new VBox();
        Button validateButton = new Button("Validate");
        validateButton.setPrefSize(320,35);
        Button startButton = new Button("Start");
        startButton.setPrefSize(320,35);
        buttons.getChildren().addAll(validateButton,startButton);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);

        validateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                validateButton.setStyle("-fx-background-color: linear-gradient(to top, #00a5ff, #2cb3fc); -fx-border-color: #0e9cf8;");
                try{
                    timeLimit =Integer.valueOf(timeLim.getText());
                    numberOfClients=Integer.valueOf(taskNO.getText());
                    numberOfServers=Integer.valueOf(serverNo.getText());
                    minProcessingTime=Integer.valueOf(timePr1.getText());
                    maxProcessingTime=Integer.valueOf(timePr2.getText());
                    minArrivalTime=Integer.valueOf(timeAr1.getText());
                    maxArrivalTime=Integer.valueOf(timeAr2.getText());
                    allOrCurrentTimes = allOrCurrent.isSelected();
                    if(minProcessingTime>=maxProcessingTime)
                            throw  new Exception();
                    if(minArrivalTime>=maxArrivalTime)
                            throw  new Exception();
                    selectionPolicy=(policySel.getSelectionModel().getSelectedItem().toString().equals("Shortest Time"))?SelectionPolicy.SHORTEST_TIME:SelectionPolicy.SHORTEST_QUEUE;
                }catch (Exception e)
                {
                    validateButton.setStyle("-fx-background-color: linear-gradient(to top, #ff0000, #ff004a); -fx-border-color: #700000;");
                }
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                queues.clear();
                queuesDWL.clear();
                SimulatorManager sm = new SimulatorManager(numberOfClients,numberOfServers,timeLimit,maxArrivalTime,minArrivalTime,maxProcessingTime,minProcessingTime,selectionPolicy,queues,queuesDWL,allOrCurrentTimes);
                Thread threadSim = new Thread(sm);
                threadSim.start();


            }
        });


        root.setSpacing(20);
        root.getChildren().addAll(titleLabel,upData,buttons);
        root.setAlignment((Pos.CENTER));
        //root.setPadding(new Insets(10,10,10,10));
        return root;
    }
    private VBox outputVBox()
    {
        VBox root= new VBox();
        ListView ls = new ListView<>();
        queues = FXCollections.observableArrayList();
        queuesDWL = FXCollections.observableArrayList();
        queues.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                  Platform.runLater(new Runnable() {
                      @Override
                      public void run() {
                          synchronized (queues)
                          {
                              ls.setItems(queues);
                          }
                          ls.refresh();
                      }
                  });
            }
        });
        ls.setPrefSize(400,200);

        root.getChildren().add(ls);
        root.setSpacing(10);
        return root;
    }


}
