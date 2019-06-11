package sample;

import com.github.javafx.charts.zooming.LineChartSample;
import com.github.javafx.charts.zooming.ZoomManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {


    @FXML
    private DatePicker datePicker;

    private List<String> blackRedZeroList;
    private List<String> evenOddZeroList;
    private List<Element> elementList;

    @FXML
    private void btn(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date= LocalDate.now().format(formatter);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(false);
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setLabel("Bitdouble "+date);
        // creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        blackRedZeroList=new ArrayList<>();
        evenOddZeroList=new ArrayList<>();
        Bitdouble bitdouble=new Bitdouble();
        try {
            elementList=new ArrayList<>(bitdouble.loadElements(date));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No internet connection");
            alert.setContentText(e.toString());
            alert.show();
            e.printStackTrace();
        }

        for (int i = 0; i <elementList.size() ; i++) {
            evenOddZeroList.add(elementList.get(i).child(0).getElementsByTag("span").toString().replaceAll("<span>","").replaceAll("</span>",""));
            blackRedZeroList.add(elementList.get(i).child(0).toString());
        }
        XYChart.Series<Number, Number> series=new XYChart.Series<Number, Number>();
        XYChart.Series<Number, Number> series2=new XYChart.Series<Number, Number>();

        int y=0;
        for (int i=0;i<blackRedZeroList.size();i++){
            if(blackRedZeroList.get(i).contains("icon ball-1")){
                int x= i;
                y++;
                series.getData().add(new XYChart.Data<Number, Number>(x,y));
            }
            if(blackRedZeroList.get(i).contains("icon ball-0")){
                int x= i;
                series.getData().add(new XYChart.Data<Number, Number>(x,y));
            }
            if(blackRedZeroList.get(i).contains("icon ball-2")){
                int x= i;
                y--;
                series.getData().add(new XYChart.Data<Number, Number>(x,y));
            }
        }
        series.setName("Black/Red/Zero");

        y=0;
        for (int i=0;i<evenOddZeroList.size();i++){
            int number= Integer.parseInt(evenOddZeroList.get(i));
            if(number%2==0&&number!=0){
                int x= i;
                y++;
                series2.getData().add(new XYChart.Data<Number, Number>(x,y));
            }
            if(number==0){
                int x= i;
                series2.getData().add(new XYChart.Data<Number, Number>(x,y));
            }
            if(number%2==1&&number!=0){
                int x=i;
                y--;
                series2.getData().add(new XYChart.Data<Number, Number>(x,y));
            }
        }

        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No internet connection");
        alert.setContentText(blackRedZeroList.get(0));
        alert.show();*/


        series2.setName("Even/Odd/Zero");

        Stage stage=new Stage();
        final StackPane pane = new StackPane();
        pane.getChildren().add(lineChart);
        final Scene scene = new Scene(pane, 1000, 800);
        new ZoomManager(pane, lineChart, series,series2);
        stage.setScene(scene);
        stage.show();


        //lineChart.getData().addAll(series,series2);





    }
}
