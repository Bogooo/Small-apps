package GUI;

import BusinessLogic.CartsBLL;
import BusinessLogic.ClientsBLL;
import BusinessLogic.OrdersBLL;
import BusinessLogic.ProductsBLL;
import DataAccess.BillDAO;
import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


public class UI extends Application {

    private Scene sceneStart;
    private ObservableList clientList;
    private ObservableList productList;
    private ObservableList orderList;
    private ObservableList cartList;
    private ClientsBLL clientsBLL;
    private ProductsBLL productsBLL;
    private OrdersBLL ordersBLL;
    private CartsBLL cartsBLL;
    private Products prodCart;
    private int clientID;
    private int productID;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // scene init
        ordersBLL=new OrdersBLL();
        cartsBLL=new CartsBLL();
        productsBLL=new ProductsBLL();
        clientsBLL= new ClientsBLL();
        orderList = FXCollections.observableArrayList();
        cartList = FXCollections.observableArrayList();
        clientList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
        VBox root = new VBox();
        sceneStart = new Scene(root,700,400);
        sceneStart.getStylesheets().add("file:///home/user/Info/PT/PT2023_30223_Chiorean_Bogdan_Assigment_3/DataManipulation/src/main/java/GUI/styleSheets.css");
        BackgroundImage myBi = new BackgroundImage(new Image("https://img.freepik.com/premium-vector/hand-drawn-flat-design-trendy-background_716135-504.jpg",700,400f,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBi));

        // titlu
        HBox title =new HBox();
        Label p1 =new Label("Pic");
        p1.getStyleClass().add("client_lab");
        p1.setStyle(" -fx-font-family: 'Satisfy'; -fx-font-size: 90;");
        Label p2 =new Label("asso");
        p2.getStyleClass().add("product_lab");
        p2.setStyle(" -fx-font-family: 'Satisfy'; -fx-font-size: 90;");
        Label p3 =new Label("Art");
        p3.getStyleClass().add("order_lab");
        p3.setStyle(" -fx-font-family: 'Satisfy'; -fx-font-size: 90;");
        title.getChildren().addAll(p1,p2,p3);
        title.setSpacing(0);
        title.setAlignment(Pos.CENTER);


        // buttons
        HBox buttons =new HBox();
        Button client =new Button("Clients");
        client.getStyleClass().add("client_butt");
        Button product =new Button("Products");
        product.getStyleClass().add("product_butt");
        Button order =new Button("Orders");
        order.getStyleClass().add("order_butt");

        client.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(clientScene(primaryStage));
            }
        });

        product.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(productScene(primaryStage));
            }
        });
        order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(orderScene(primaryStage));
            }
        });

        buttons.getChildren().addAll(client,product,order);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(15);

        //Bills button
        HBox bilBox=new HBox();
        Button billsBtn =new Button("Bills");
        billsBtn.getStyleClass().add("order_butt");
        billsBtn.setStyle("-fx-text-fill: black; -fx-border-color: black; -fx-font-size: 15; -fx-font-weight: BOLD;");
        billsBtn.setAlignment(Pos.BASELINE_RIGHT);
        bilBox.getChildren().add(billsBtn);
        bilBox.setAlignment(Pos.BASELINE_RIGHT);
        bilBox.setPadding(new Insets(90,0,0,0));


        //BillButton
        billsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = new File("Bills.txt");
                try {
                    BillDAO billDAO=new BillDAO();
                    file.createNewFile();
                    FileWriter myWriter = new FileWriter("Bills.txt");

                    List<Bills> list = billDAO.findAll();
                    String text="";

                    for(Bills b:list)
                        text+=b+"\n";

                    myWriter.write(text);
                    myWriter.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        // scene settings
        root.getChildren().addAll(title,buttons,bilBox);
        root.setPadding(new Insets(55,5,0,0));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(0);
        primaryStage.setScene(sceneStart);
        primaryStage.setTitle("ORDERS MANAGEMENT");
        primaryStage.show();
    }


    private Scene clientScene(Stage primaryStage)
    {
        VBox root = new VBox();
        //root.setStyle("-fx-background-color: #2d2d2d");
        BackgroundImage myBi = new BackgroundImage(new Image("https://img.freepik.com/premium-vector/hand-drawn-flat-design-trendy-background_716135-504.jpg",700,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBi));
        Scene scene = new Scene(root,700,400);
        scene.getStylesheets().add("file:///home/user/Info/PT/PT2023_30223_Chiorean_Bogdan_Assigment_3/DataManipulation/src/main/java/GUI/styleSheets.css");

        Label title = new Label("Clients");
        title.getStyleClass().add("client_lab");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20");


        // lista
        refreshClientsList();
        VBox listsBox = new VBox();
        Clients or=new Clients();
        Label describeLs = new Label(retrieveProperties(or));
        describeLs.getStyleClass().add("client_lab");
        refreshOrdersList();
        ListView listView=new ListView<>(clientList);
        listView.getSelectionModel().clearSelection();
        listView.setPrefSize(300,200);
        listsBox.getChildren().addAll(describeLs,listView);
        listsBox.setAlignment(Pos.CENTER);

        // edit prof
        VBox account = new VBox();
        account.setSpacing(10);
        account.setAlignment(Pos.CENTER);

        HBox idBox = new HBox();
        Label idLb = new Label("ID:");
        idLb.getStyleClass().add("client_lab");
        TextField idTF =new TextField();
        idTF.getStyleClass().add("client_tf");
        idBox.getChildren().addAll(idLb,idTF);
        idBox.setSpacing(33);

        HBox nameBox = new HBox();
        Label nameLb = new Label("Name:");
        nameLb.getStyleClass().add("client_lab");
        TextField nameTF =new TextField();
        nameTF.getStyleClass().add("client_tf");
        nameBox.getChildren().addAll(nameLb,nameTF);
        nameBox.setSpacing(5);

        HBox mailBox = new HBox();
        Label mailLb = new Label("Mail:   ");
        mailLb.getStyleClass().add("client_lab");
        TextField mailTF =new TextField();
        mailTF.getStyleClass().add("client_tf");
        mailBox.getChildren().addAll(mailLb,mailTF);
        mailBox.setSpacing(5);

        HBox buttons = new HBox();
        buttons.setSpacing(5);

        Button addBtn = new Button("Add");
        addBtn.getStyleClass().add("client_butt2");
        Button delBtn = new Button("Delete");
        delBtn.getStyleClass().add("client_butt2");
        Button editBtn = new Button("Save");
        editBtn.getStyleClass().add("client_butt2");
        Button resetBtn = new Button("Reset");
        resetBtn.getStyleClass().add("client_butt2");
        buttons.getChildren().addAll(addBtn,editBtn,delBtn,resetBtn);
        buttons.setAlignment(Pos.CENTER);

        Label errorsLB = new Label();
        errorsLB.getStyleClass().add("client_lab");
        account.getChildren().addAll(idBox,nameBox,mailBox,buttons,errorsLB);

        HBox center= new HBox();
        center.setAlignment(Pos.CENTER);
        center.setSpacing(15);
        center.getChildren().addAll(listsBox,account);

        //back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("client_butt");

        //Action buttons
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                errorsLB.setText("");
                Clients client= (Clients) listView.getSelectionModel().getSelectedItem();
                idTF.setText(String.valueOf(client.getId_client()));
                nameTF.setText(client.getName());
                mailTF.setText(client.getEmail());
            }
        });
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                Clients client=new Clients(Integer.valueOf(idTF.getText()),nameTF.getText(),mailTF.getText());
                try {
                    clientsBLL.createClient(client);
                    refreshClientsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                idTF.clear();
                nameTF.clear();
                mailTF.clear();
                listView.getSelectionModel().clearSelection();
                errorsLB.setText("");
            }
        });

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                Clients client=new Clients(Integer.valueOf(idTF.getText()),nameTF.getText(),mailTF.getText());
                try {
                    clientsBLL.saveClient(client);
                    refreshClientsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    clientsBLL.deleteClientById(Integer.valueOf(idTF.getText()));
                    refreshClientsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sceneStart);
            }
        });

        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.getChildren().addAll(title,center,backButton);
        return scene;
    }






    private Scene productScene(Stage primaryStage)
    {
        VBox root = new VBox();
        BackgroundImage myBi = new BackgroundImage(new Image("https://img.freepik.com/premium-vector/hand-drawn-flat-design-trendy-background_716135-504.jpg",700,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBi));
        Scene scene = new Scene(root,700,400);
        scene.getStylesheets().add("file:///home/user/Info/PT/PT2023_30223_Chiorean_Bogdan_Assigment_3/DataManipulation/src/main/java/GUI/styleSheets.css");

        Label title = new Label("Products");
        title.getStyleClass().add("product_lab");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        // lista
        refreshProductsList();
        VBox listsBox = new VBox();
        Products or=new Products();
        Label describeLs = new Label(retrieveProperties(or));
        describeLs.getStyleClass().add("product_lab");
        refreshOrdersList();
        ListView listView=new ListView<>(productList);
        listView.getSelectionModel().clearSelection();
        listView.setPrefSize(300,200);
        listsBox.getChildren().addAll(describeLs,listView);
        listsBox.setAlignment(Pos.CENTER);

        // edit prof
        VBox account = new VBox();
        account.setSpacing(10);
        account.setAlignment(Pos.CENTER);

        HBox idBox = new HBox();
        Label idLb = new Label("ID:");
        idLb.getStyleClass().add("product_lab");
        TextField idTF =new TextField();
        idTF.getStyleClass().add("product_tf");
        idBox.getChildren().addAll(idLb,idTF);
        idBox.setSpacing(33);

        HBox nameBox = new HBox();
        Label nameLb = new Label("Name:");
        nameLb.getStyleClass().add("product_lab");
        TextField nameTF =new TextField();
        nameTF.getStyleClass().add("product_tf");
        nameBox.getChildren().addAll(nameLb,nameTF);
        nameBox.setSpacing(5);

        HBox priceBox = new HBox();
        Label priceLb = new Label("Price:");
        priceLb.getStyleClass().add("product_lab");
        TextField priceTF =new TextField();
        priceTF.getStyleClass().add("product_tf");
        priceBox.getChildren().addAll(priceLb,priceTF);
        priceBox.setSpacing(13);

        HBox stockBox = new HBox();
        Label stockLb = new Label("Stock:");
        stockLb.getStyleClass().add("product_lab");
        TextField stockTF =new TextField();
        stockTF.getStyleClass().add("product_tf");
        stockBox.getChildren().addAll(stockLb,stockTF);
        stockBox.setSpacing(7);

        HBox buttons = new HBox();
        buttons.setSpacing(5);

        Button addBtn = new Button("Add");
        addBtn.getStyleClass().add("product_butt2");
        Button delBtn = new Button("Delete");
        delBtn.getStyleClass().add("product_butt2");
        Button editBtn = new Button("Save");
        editBtn.getStyleClass().add("product_butt2");
        Button resetBtn = new Button("Reset");
        resetBtn.getStyleClass().add("product_butt2");
        buttons.getChildren().addAll(addBtn,editBtn,delBtn,resetBtn);
        buttons.setAlignment(Pos.CENTER);
        Label errorsLB = new Label();
        errorsLB.getStyleClass().add("product_lab");
        account.getChildren().addAll(idBox,nameBox,priceBox,stockBox,buttons,errorsLB);

        HBox center= new HBox();
        center.setAlignment(Pos.CENTER);
        center.setSpacing(15);
        center.getChildren().addAll(listsBox,account);

        //back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("product_butt");

        //actions
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                errorsLB.setText("");
                Products product= (Products) listView.getSelectionModel().getSelectedItem();
                idTF.setText(String.valueOf(product.getId_product()));
                nameTF.setText(product.getName());
                priceTF.setText(String.valueOf(product.getPrice()));
                stockTF.setText(String.valueOf(product.getStock()));
            }
        });
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                Products product=new Products(Integer.valueOf(idTF.getText()),nameTF.getText(),Float.valueOf(priceTF.getText()),Integer.valueOf(stockTF.getText()));
                try {
                    productsBLL.createProducts(product);
                    refreshProductsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                idTF.clear();
                nameTF.clear();
                priceTF.clear();
                stockTF.clear();
                listView.getSelectionModel().clearSelection();
                errorsLB.setText("");
            }
        });

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                Products product=new Products(Integer.valueOf(idTF.getText()),nameTF.getText(),Float.valueOf(priceTF.getText()),Integer.valueOf(stockTF.getText()));
                try {
                    productsBLL.saveProduct(product);
                    refreshProductsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    productsBLL.deleteProductById(Integer.valueOf(idTF.getText()));
                    refreshProductsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sceneStart);
            }
        });

        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.getChildren().addAll(title,center,backButton);
        return scene;
    }


    private Scene orderScene(Stage primaryStage)
    {
        VBox root = new VBox();
        BackgroundImage myBi = new BackgroundImage(new Image("https://img.freepik.com/premium-vector/hand-drawn-flat-design-trendy-background_716135-504.jpg",700,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBi));
        Scene scene = new Scene(root,700,400);
        scene.getStylesheets().add("file:///home/user/Info/PT/PT2023_30223_Chiorean_Bogdan_Assigment_3/DataManipulation/src/main/java/GUI/styleSheets.css");

        Label title = new Label("Orders");
        title.getStyleClass().add("order_lab");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20");

        // lista
        refreshOrdersList();
        VBox listsBox = new VBox();
        Orders or=new Orders();
        Label describeLs = new Label(retrieveProperties(or));
        describeLs.getStyleClass().add("order_lab");
        refreshOrdersList();
        ListView listView=new ListView<>(orderList);
        listView.getSelectionModel().clearSelection();
        listView.setPrefSize(300,200);
        listsBox.getChildren().addAll(describeLs,listView);
        listsBox.setAlignment(Pos.TOP_CENTER);
        // edit prof
        VBox account = new VBox();
        account.setSpacing(10);
        account.setAlignment(Pos.CENTER);

        HBox idBox = new HBox();
        Label idLb = new Label("ID:");
        idLb.getStyleClass().add("order_lab");
        TextField idTF =new TextField();
        idTF.getStyleClass().add("order_tf");
        idBox.getChildren().addAll(idLb,idTF);
        idBox.setSpacing(33);


        refreshClientsList();
        HBox nameBox = new HBox();
        Label nameLb = new Label("Client:");
        nameLb.getStyleClass().add("order_lab");
        ComboBox clientCB = new ComboBox(clientList);
        AutoCompleteBox completeBox= new AutoCompleteBox(clientCB);
        nameBox.getChildren().addAll(nameLb,clientCB);
        nameBox.setSpacing(5);

        refreshProductsList();
        HBox prodBox = new HBox();
        Label prodLb = new Label("Product: ");
        prodLb.getStyleClass().add("order_lab");
        TextField prodTF =new TextField();
        prodTF.getStyleClass().add("order_tf");
        prodTF.setPrefSize(40,10);
        ComboBox productCB = new ComboBox(productList);
        productCB.setMaxSize(100,30);
        AutoCompleteBox completeBox2= new AutoCompleteBox(productCB);
        Button addProdBtn = new Button("Add");
        addProdBtn.getStyleClass().add("order_butt2");
        prodBox.getChildren().addAll(prodLb,productCB,prodTF,addProdBtn);
        prodBox.setSpacing(5);

        HBox lsBox = new HBox();
        ListView lsProducts = new ListView(cartList);
        lsProducts.getSelectionModel().clearSelection();
        lsProducts.setPrefSize(200,70);
        Button delProdBtn = new Button("Remove");
        delProdBtn.getStyleClass().add("order_butt2");
        lsBox.getChildren().addAll(lsProducts,delProdBtn);
        lsBox.setSpacing(5);
        lsBox.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        buttons.setSpacing(5);

        Button addBtn = new Button("Add");
        addBtn.getStyleClass().add("order_butt2");
        Button delBtn = new Button("Delete");
        delBtn.getStyleClass().add("order_butt2");
        Button editBtn = new Button("Save");
        editBtn.getStyleClass().add("order_butt2");
        Button resetBtn = new Button("Reset");
        resetBtn.getStyleClass().add("order_butt2");
        buttons.getChildren().addAll(addBtn,editBtn,delBtn,resetBtn);
        buttons.setAlignment(Pos.CENTER);
        Label errorsLB = new Label();
        errorsLB.getStyleClass().add("order_lab");
        account.getChildren().addAll(idBox,nameBox,prodBox,lsBox,buttons,errorsLB);


        HBox center= new HBox();
        center.setAlignment(Pos.CENTER);
        center.setSpacing(15);
        center.getChildren().addAll(listsBox,account);

        //back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("order_butt");

        // Actions
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                errorsLB.setText("");
                Orders order= (Orders) listView.getSelectionModel().getSelectedItem();
                idTF.setText(String.valueOf(order.getId_order()));
                int id=0;
                for(Object c:clientList) {
                    Clients cl= (Clients) c;
                    if(cl.getId_client() == order.getId_client())
                        id=clientList.indexOf(c);
                }
                clientCB.getSelectionModel().select(id);

                productCB.getSelectionModel().clearSelection();
                prodTF.clear();
                refreshCartsList(order.getId_order());
                lsProducts.getSelectionModel().clearSelection();
            }
        });

        lsProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //try {
                    int id = lsProducts.getSelectionModel().getSelectedIndex();
                    Carts pr = (Carts) cartList.get(id);
                    productID = pr.getId_product();
                    System.out.println(pr);
               // }catch (Exception e){};
            }
        });
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    Orders order= new Orders(clientID, Integer.valueOf(idTF.getText()), 0);
                    ordersBLL.createOrder(order);
                    refreshOrdersList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                idTF.clear();
                clientCB.getSelectionModel().clearSelection();
                productCB.getSelectionModel().clearSelection();
                cartList.clear();
                prodTF.clear();
                productCB.getSelectionModel().clearSelection();
                clientCB.getSelectionModel().clearSelection();
                listView.getSelectionModel().clearSelection();
                errorsLB.setText("");
            }
        });

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                System.out.println(clientID);
                Orders order= new Orders(clientID, Integer.valueOf(idTF.getText()), 0);
                try {
                    ordersBLL.saveOrder(order);
                    refreshOrdersList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    ordersBLL.deleteOrderById(Integer.valueOf(idTF.getText()));
                    refreshOrdersList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });

        productCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    int id = productCB.getSelectionModel().getSelectedIndex();
                    prodCart = (Products) productList.get(id);
                }catch (Exception e){};
            }
        });
        clientCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");

                try {
                    int id = clientCB.getSelectionModel().getSelectedIndex();
                    clientID = ((Clients) clientList.get(id)).getId_client();
                }catch (Exception e){};
            }
        });

        addProdBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    Carts cart = new Carts(Integer.valueOf(idTF.getText()),prodCart.getId_product(),prodCart.getPrice(),Integer.valueOf(prodTF.getText()));
                    cartsBLL.createCarts(cart);
                    Products pr = (Products) productsBLL.findProductById(cart.getId_product());
                    productsBLL.saveProduct(new Products(pr.getId_product(),pr.getName(),pr.getPrice(),pr.getStock()-cart.getItems_number()));
                    ordersBLL.calculateTotalPrice(Integer.valueOf(idTF.getText()));
                    refreshCartsList(Integer.valueOf(idTF.getText()));
                    refreshOrdersList();
                    listView.getSelectionModel().select(Integer.valueOf(idTF.getText()));
                    refreshProductsList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });
        delProdBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                errorsLB.setText("");
                try {
                    System.out.println(productID);
                    cartsBLL.deleteCartById(Integer.valueOf(idTF.getText()),productID);
                    lsProducts.getSelectionModel().clearSelection();
                    ordersBLL.calculateTotalPrice(Integer.valueOf(idTF.getText()));
                    refreshCartsList(Integer.valueOf(idTF.getText()));
                    refreshProductsList();
                    listView.getSelectionModel().select(Integer.valueOf(idTF.getText()));
                    refreshOrdersList();
                } catch (Exception e) {
                    errorsLB.setText(e.getMessage());
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sceneStart);
            }
        });

        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.getChildren().addAll(title,center,backButton);
        return scene;
    }


    private void refreshClientsList()
    {
        clientList.clear();
        clientList.addAll(clientsBLL.allClients());
    }

    private void refreshOrdersList()
    {
        orderList.clear();
        orderList.addAll(ordersBLL.allOrders());
    }

    private void refreshProductsList()
    {
        productList.clear();
        productList.addAll(productsBLL.allProducts());
    }

    private void refreshCartsList(int id)
    {
        cartList.clear();
        try {
            cartList.addAll(cartsBLL.findAllCartsById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String retrieveProperties(Object object) {
        String s="(";
        int i=0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            s+= field.getName()+",";
            i++;
            if(i>1)break;
        }
        s=s.substring(0,s.length()-1);
        s+=")";
        return s;
    }

}


