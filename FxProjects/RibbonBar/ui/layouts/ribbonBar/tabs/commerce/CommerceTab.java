package ui.layouts.ribbonBar.tabs.commerce;

import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

/**
 * InsertTab. This class represents the "Commerce Tab".
 */
public class CommerceTab {
    
    private Tab tab;
    
    /**
     * Default Constructor.
     */
    public CommerceTab() {
        tab = new Tab("Commerce");
        buildTab();
    }
    
    /**
     * get. Returns an instance of the Commerce Tab. This will be added to the 
     * TabPane in the RibbonBar class.
     * @return 
     */
    public Tab get() {
        return this.tab;
    }
    
    /**
     * buildTab. Helper method to build the Commerce Tab UI.
     */
    private void buildTab() {

        //Do not allow tab to close.
        tab.setClosable(false);
        
        //The container holds all toolbar sections specific to a Tab.
        HBox container = new HBox();
        
        //Set ID (for CSS styles)
        container.setId("container");
        
        //Set preferred height.
        container.setPrefHeight(90);
        
        //Put 10px spacing b/n each toolbar block
        container.setSpacing(5);
        
        //Add Colorful Icons Ribbon Component.
        ColorfulIcons colorfulIcons = new ColorfulIcons();
        container.getChildren().add(colorfulIcons.get());
        
        //Add Payment Ribbon Component.
        Payment payment = new Payment();
        container.getChildren().add(payment.get());
        
        //Add Container.
        tab.setContent(container); 
    }
   
}
