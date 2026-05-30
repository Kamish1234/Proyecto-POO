package main;

import controller.BMSController;
import view.BMSDashboard;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Crear la Vista (La interfaz gráfica)
            BMSDashboard dashboardView = new BMSDashboard();
            
            // 2. Crear el Controlador
            // Pasamos 'null' como batería inicial porque el Controlador ahora 
            // crea la primera batería automáticamente con su propio botón.
            new BMSController(null, dashboardView);
            
            // 3. Mostrar la ventana
            dashboardView.setLocationRelativeTo(null); // Centrar en pantalla
            dashboardView.setVisible(true);
        });
    }
}