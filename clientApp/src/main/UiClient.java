package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class UiClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

//        primaryStage.setTitle("Alternative Banking System");
//        Parent load = FXMLLoader.load(getClass().getResource("../main/mainScreen.fxml"));
//        Scene scene = new Scene(load, 700, 650);
//        primaryStage.setScene(scene);
//        primaryStage.show();

        primaryStage.setTitle("Alternative Banking System");

        URL location = getClass().getResource("mainScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1250, 650);
        scene.getStylesheets().add("main/mainScreen1.css");
        primaryStage.setScene(scene);
        MainController mainController = fxmlLoader.getController();
        mainController.setStage(primaryStage);
        //primaryStage.setMaximized(true);
        primaryStage.show();

        //FXMLLoader loader = new FXMLLoader();
        // load main fxml

        //URL mainFXML = getClass().getResource("/javaFX/Scene Builder.fxml");
        //loader.setLocation(mainFXML);
        //BorderPane root = loader.load();



        // wire up controller
        //ABScontroller absController = loader.getController();
//        //BusinessLogic businessLogic = new BusinessLogic(histogramController);
          //  absController.setPrimaryStage(primaryStage);
//        //absController.setBusinessLogic(businessLogic);





//        primaryStage.setTitle("HistogramS");
//        Scene scene = new Scene(root, 1050, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
