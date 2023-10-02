package com.example.appsound;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class HelloController {
    @FXML
    private Label welcomeText;
    int BuffToGraph[];
    public AudioCapture01 AC1 = new AudioCapture01();

    @FXML
    protected void onRecordButtonClick() {
        AC1.captureAudio();
    }

    @FXML
    protected void onStopButtonClick() {
        AC1.stopCapture=true;
    }

    @FXML
    protected void onPlayButtonClick() {
        AC1.playAudio();
    }

    public void GBtoF () {
        BuffToGraph=new int[AC1.GBuffer.length/2];
        int f=0;
        for(int i = 0; i < AC1.GBuffer.length-1; i=i+2)
        {
            BuffToGraph[f]= (int)(AC1.GBuffer[i+1])*256+ Byte.toUnsignedInt(AC1.GBuffer[i]);
                    f=f+1;
        }
        int y=0;
    }
    @FXML
    protected void onGraphButtonClick() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("graph-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Hello!");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Time");
            final LineChart<Number,Number> lineChart =
                    new LineChart<Number,Number>(xAxis,yAxis);

            lineChart.setTitle("Frequency response");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("Signal 1");


           GBtoF();
            //series1.getData().add(new XYChart.Data(8,  AC1.GBuffer[1]));

            for(int i = 0; i < BuffToGraph.length-4; i=i+5) {
                series1.getData().add(new XYChart.Data(i,  BuffToGraph[i]));


            }
            /*for(int i = 0; i < AC1.GBuffer.length; i++) {
                series1.getData().add(new XYChart.Data(i,  AC1.GBuffer[i]));


            }*/
            lineChart.getData().addAll(series1);


            stage.setScene(new Scene(lineChart,800,600));
            stage.show();
        }
        catch (Exception e) {
        System.out.println("Can't load");
        }
    }
}