
package courseworkthreejavafx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;                // all the necessary imports in order for the code to work
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


/**
 *
 * @author cmpgsbyr
 */
public class FXMLDocumentController implements Initializable {
    private static List<Sales> sales;
    private DashService service;
    private static List<String> strings;
    private static String markup;
    private CheckBox[] checkBoxes;
    private ToggleGroup ToggleGroup2;
    @FXML
    private AnchorPane AnchorPane1;                     //all variables are declared here
    @FXML
    private HBox HBox1;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private BarChart<?, ?> BarChart1; 
    @FXML
    private LineChart LineChart1;
    @FXML
    private AreaChart AreaChart1;
    @FXML
    private PieChart PieChart1;
    
    @FXML
    private TableView TableView1;
     
       
        
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        service = new DashService();
        service.setAddress("http://glynserver.cms.livjm.ac.uk/DashService/SalesGetSales");
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                markup = e.getSource().getValue().toString();
                                
                sales = (new Gson()).fromJson(markup, new TypeToken<LinkedList<Sales>>() {}.getType());
                
                strings = sales.stream().map(o -> o.getYear()).distinct().collect(Collectors.toList());               
                LinkedList<Sales> sales = (new Gson()).fromJson(markup, new TypeToken<LinkedList<Sales>>() {}.getType());
                
                System.out.println(markup);
                constructCheckBoxes();
                constructLineChart();
                constructPieChart();
                constructAreaChart();
                constructTable();
            }
        });

        service.start();
    }       
    
    private void constructCheckBoxes() { 
        checkBoxes = new CheckBox[strings.size()];

        for (byte index = 0; index < strings.size(); index++) {
            checkBoxes[index] = new CheckBox(strings.get(index));
            checkBoxes[index].setSelected(true);
            checkBoxes[index].addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("Firstly, Event Filters !");
                }
            });
            checkBoxes[index].addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("Secondly, Event Handlers !");
                }
            });
            checkBoxes[index].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {                    
                    System.out.println("Thirdly, Convenience Methods !");

                    constructSeries();
                    constructLineChart();
                    constructPieChart();
                    constructAreaChart();
                    constructTable();
                }
            });

            HBox1.getChildren().add(checkBoxes[index]);
        }


        AnchorPane1.getScene().getWindow().sizeToScene();

        constructSeries();
        constructLineChart();
        constructAreaChart();
        constructPieChart();
        constructTable();
    }
    
    private void constructSeries() {
        BarChart1.getData().clear();                        //creating the bar chart

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(checkBox.getText());

                for (Sales sale : sales) {
                    
                    if (sale.getYear().equals(checkBox.getText())){
                    Map<String, Integer> regions = sales.stream()
                    //.filter(o -> o.getVehicle().equals("Elise"))
                    .filter(o -> o.getYear().equals(checkBox.getText()))
                    .collect(Collectors.groupingBy(Sales::getVehicle, Collectors.reducing(0, Sales::getQuantity, Integer::sum)));

                    for (Map.Entry<String, Integer> region : regions.entrySet()) {
                    System.out.println(region.getKey() + ":" + region.getValue());
                    
                    series.getData().add(new XYChart.Data<>(region.getKey(), region.getValue()));
                    
                    }
                }
            }
              
                BarChart1.getData().add(series);
                BarChart1.applyCss();
                BarChart1.layout();
            }
        }
    }
    
    private void constructLineChart() {
        LineChart1.getData().clear();   //creating the line chart

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(checkBox.getText());

                for (Sales sale : sales) {
                    
                    if (sale.getYear().equals(checkBox.getText())){
                    Map<String, Integer> regions = sales.stream()
                    //.filter(o -> o.getVehicle().equals("Elise"))
                    .filter(o -> o.getYear().equals(checkBox.getText()))
                    .collect(Collectors.groupingBy(Sales::getRegion, Collectors.reducing(0, Sales::getQuantity, Integer::sum)));

                    for (Map.Entry<String, Integer> region : regions.entrySet()) {
                    System.out.println(region.getKey() + ":" + region.getValue());
                    
                    series.getData().add(new XYChart.Data<>(region.getKey(), region.getValue()));
                    
                    }
                }
            }
              
                LineChart1.getData().add(series);
                LineChart1.applyCss();
                LineChart1.layout();
            }
        }
    }
    
    private void constructAreaChart() {
        AreaChart1.getData().clear();                       //developing the area chart

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                AreaChart.Series series = new AreaChart.Series();
                series.setName(checkBox.getText());

                for (Sales sale : sales) {
                    
                    if (sale.getYear().equals(checkBox.getText())){
                    Map<String, Integer> regions = sales.stream()
                    //.filter(o -> o.getVehicle().equals("Elise"))
                    .filter(o -> o.getYear().equals(checkBox.getText()))
                    .collect(Collectors.groupingBy(Sales::getRegion, Collectors.reducing(0, Sales::getQuantity, Integer::sum)));

                    for (Map.Entry<String, Integer> region : regions.entrySet()) {
                    System.out.println(region.getKey() + ":" + region.getValue());
                    
                    series.getData().add(new AreaChart.Data<>(region.getKey(), region.getValue()));
                    
                    }
                }
            }
              
                AreaChart1.getData().add(series);
                AreaChart1.applyCss();
                AreaChart1.layout();
            }
        }
    }
    
    
     private void constructPieChart() {
            PieChart1.getData().clear();                //developing the pie chart

        List<PieChart.Data> series = new LinkedList<>();
        

        // NOTE : Java SE 8 Code !
        /*sales.stream().filter((sale) -> (sale.getIntake().equals(RadioButton.class.cast(ToggleGroup2.getSelectedToggle()).getText()))).forEach((sale) -> {
            series.add(new PieChart.Data(sale.getVehicle(), sale.getQuantity()));
        });*/
        
        Map<String, Integer> cars = sales.stream()
                    .collect(Collectors.groupingBy(Sales::getRegion, Collectors.reducing(0, Sales::getQuantity, Integer::sum)));

        
        ObservableList<PieChart.Data> ol = FXCollections.observableArrayList();
        
        // NOTE : Java SE 7 Code !
        cars.entrySet().forEach((car) -> {
            //if (sale.getIntake().equals(RadioButton.class.cast(ToggleGroup2.getSelectedToggle()).getText())) {
                ol.add(new PieChart.Data(car.getKey(), car.getValue()));
            //}
        });
        

        PieChart1.setData(ol);
        PieChart1.applyCss();
        PieChart1.setVisible(true);
        PieChart1.layout();

    }
     
     private void constructTable(){                 //building the table in order to show the data
         TableView1.getItems().clear();
         TableView1.getColumns().clear();
         
         
         TableColumn TableColumn1 = new TableColumn("Year");
         TableColumn1.setCellValueFactory( new PropertyValueFactory("Year"));
         
         TableColumn TableColumn2 = new TableColumn("Qtr");
         TableColumn2.setCellValueFactory( new PropertyValueFactory("QTR"));
         
         TableColumn TableColumn3 = new TableColumn("Region");
         TableColumn3.setCellValueFactory( new PropertyValueFactory("Region"));
         
         TableColumn TableColumn4 = new TableColumn("Vehicle");
         TableColumn4.setCellValueFactory( new PropertyValueFactory("Vehicle"));
         
         TableColumn TableColumn5 = new TableColumn("Sales");
         TableColumn5.setCellValueFactory( new PropertyValueFactory("Quantity"));
         
         
         TableView1.getColumns().addAll(TableColumn1,TableColumn2, TableColumn3, TableColumn4,TableColumn5 );
         TableView1.setItems(FXCollections.observableArrayList(sales));
         
     }
     
     
     

private static class DashService extends Service<String> {
        private StringProperty address = new SimpleStringProperty();

        public final void setAddress(String address) {
            this.address.set(address);
        }

        public final String getAddress() {
            return address.get();
        }

        public final StringProperty addressProperty() {
           return address;
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                private URL url;
                private HttpURLConnection connect;
                private String markup = "";

                @Override
                protected String call() {
                    try {
                        url = new URL(getAddress());
                        connect = (HttpURLConnection)url.openConnection();
                        connect.setRequestMethod("GET");
                        connect.setRequestProperty("Accept", "application/json");
                        connect.setRequestProperty("Content-Type", "application/json");                        

                        markup = (new BufferedReader(new InputStreamReader(connect.getInputStream()))).readLine();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (connect != null) {
                            connect.disconnect();
                        }
                    }

                    return markup;
                }
            };
        }
    }   
}