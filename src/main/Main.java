package main;

import controller.BMSController;
import view.BMSDashboard;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            BMSDashboard dashboardView = new BMSDashboard();
            
            new BMSController(null, dashboardView);
            
            dashboardView.setLocationRelativeTo(null); 
            dashboardView.setVisible(true);
        });
    }
}