package ui.layouts.ribbonBar;

import javafx.scene.control.TabPane;
import ui.layouts.ribbonBar.tabs.home.HomeTab;
import ui.layouts.ribbonBar.tabs.commerce.CommerceTab;

/**
 * RibbonBar. Drop this into application UI. This class creates the entire
 * Ribbon Bar and could be placed in the BorderPane.NORTH region (for example).
 */
public class RibbonBar {
    
    private TabPane tabPane;
    
    public RibbonBar() {
        
        tabPane = new TabPane();
        
        buildTabs();
    }
    
    /**
     * get. Return instance of the RibbonBar (TabPane).
     * @return 
     */
    public TabPane get() {
        return this.tabPane;
    }
    
    /**
     * buidlTabs. Build each Tab that is added to TabPane.
     */
    private void buildTabs() {
        
        HomeTab homeTab = new HomeTab();
        CommerceTab insertTab = new CommerceTab();
        
        tabPane.getTabs().addAll(homeTab.get(), insertTab.get());
    }
}
