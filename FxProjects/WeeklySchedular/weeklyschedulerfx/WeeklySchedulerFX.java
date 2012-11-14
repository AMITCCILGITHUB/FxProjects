/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weeklyschedulerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weeklyschedulerfx.doubleSlider.DoubleSlider;

/**
 *
 * @author José Pereda Llamas <pereda@eii.uva.es>
 * Created on 14-sep-2012, 12:04:34
 */
public class WeeklySchedulerFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("scheduler.fxml"));
        
        Scene scene = new Scene(root);        
        scene.getStylesheets().addAll(DoubleSlider.class.getResource("double_slider.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("JavaFX Tutorial");
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
