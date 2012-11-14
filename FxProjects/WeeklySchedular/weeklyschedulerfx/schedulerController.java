/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weeklyschedulerfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import weeklyschedulerfx.doubleSlider.DoubleSlider;

/**
 *
 * @author José Pereda Llamas <pereda@eii.uva.es>
 * Created on 14-sep-2012, 12:04:34
 * 
 */
public class schedulerController implements Initializable {
    
    @FXML
    private GridPane grid;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final Label[] rangeLabel=new Label[7];
        
        int cont=0;
        for(Node n:grid.getChildren()){
            if(n instanceof VBox){
                VBox cell=(VBox)n;
                cell.setStyle("-fx-background-color: #ecebe9,rgba(0,0,0,0.05),linear-gradient(#dcca8a, #c7a740),"
                        + "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),"
                        + "linear-gradient(#f6ebbe, #e6c34d);"
                        + "-fx-background-radius: 4;-fx-background-insets: 0,2 2 1 2,2,3,4;");
                if(cont<7){                   
                    rangeLabel[cont]=new Label();
                    rangeLabel[cont].textProperty().set("[00:00 - 00:00]");
                    cell.getChildren().add(rangeLabel[cont]);                    
                } 
                else {
                    final int day=cont-7;
                    
                    final DoubleSlider doubleSlider1 = new DoubleSlider();
                    doubleSlider1.setPrefWidth(300);                
                    doubleSlider1.setShowTickMarks(true);
                    doubleSlider1.setShowTickLabels(true);
                    doubleSlider1.setMajorTickUnit(2);
                    doubleSlider1.setMinorTickCount(1);
                    doubleSlider1.setSnapToTicks(true);
                    doubleSlider1.setMin(0);
                    doubleSlider1.setMax(48);
                    doubleSlider1.setLabelFormatter(new StringConverter<Double>(){              
                        @Override
                        public String toString(Double object) {
                            if(object==null){
                                return null;
                            }
                            return toTime(object.doubleValue());
                        }

                        @Override
                        public Double fromString(String string) {
                            return (string!=null?new Double(string):new Double(0));
                        }
                    });
                    doubleSlider1.value1Property().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> arg0,
                                        Number arg1, Number arg2) {
                            rangeLabel[day].textProperty().set("["+toTime(arg2.intValue())+" - "+toTime(doubleSlider1.getValue2())+"]");
                        }
                    });
                    doubleSlider1.value2Property().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> arg0,
                                        Number arg1, Number arg2) {
                            rangeLabel[day].textProperty().set("["+toTime(doubleSlider1.getValue1())+" - "+toTime(arg2.intValue())+"]");
                        }
                    });
                    
                    /*
                     * sample values
                     */
                    doubleSlider1.setValue1(day<5?16:0); // 8:00
                    doubleSlider1.setValue2(day<5?32:0); // 16:00

                    cell.getChildren().add(doubleSlider1);                                
                }
                cont+=1;
            }
        }
    }    
    
    private String toTime(double value){
        return String.format("%02d:%02d", (int)(value/2), (int)(30*(value%2)));
    }
}
