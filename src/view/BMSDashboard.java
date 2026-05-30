package view;

import model.BatteryPack;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BMSDashboard extends JFrame implements PropertyChangeListener {
    private JLabel lblVoltage, lblCurrent, lblSoC, lblTemp;
    private JButton btnCharge, btnDischarge, btnStop, btnAddBattery;
    private JComboBox<BatteryPack> cmbBatteries;

    public BMSDashboard() {
        setTitle("BMS Dashboard - Baterías Independientes");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnlTop = new JPanel(new FlowLayout());
        cmbBatteries = new JComboBox<>();
        btnAddBattery = new JButton("Agregar Batería Nueva");
        pnlTop.add(new JLabel("Seleccionar: "));
        pnlTop.add(cmbBatteries);
        pnlTop.add(btnAddBattery);
        add(pnlTop, BorderLayout.NORTH);

        JPanel pnlData = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlData.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnlData.add(new JLabel("Voltaje (V):"));
        lblVoltage = new JLabel("0.00 V");
        pnlData.add(lblVoltage);
        pnlData.add(new JLabel("Corriente (A):"));
        lblCurrent = new JLabel("0.00 A");
        pnlData.add(lblCurrent);
        pnlData.add(new JLabel("Estado de Carga (SoC %):"));
        lblSoC = new JLabel("0.0 %");
        pnlData.add(lblSoC);
        pnlData.add(new JLabel("Temperatura (°C):"));
        lblTemp = new JLabel("0.0 °C");
        pnlData.add(lblTemp);
        add(pnlData, BorderLayout.CENTER);

        JPanel pnlControls = new JPanel(new FlowLayout());
        btnCharge = new JButton("Cargar");
        btnDischarge = new JButton("Descargar");
        btnStop = new JButton("Parar");
        pnlControls.add(btnCharge);
        pnlControls.add(btnDischarge);
        pnlControls.add(btnStop);
        add(pnlControls, BorderLayout.SOUTH);

        cmbBatteries.addActionListener(e -> {
            BatteryPack selected = (BatteryPack) cmbBatteries.getSelectedItem();
            if (selected != null) {
                updateScreen(selected);
            }
        });
    }

    public JButton getBtnCharge() { return btnCharge; }
    public JButton getBtnDischarge() { return btnDischarge; }
    public JButton getBtnStop() { return btnStop; }
    public JButton getBtnAddBattery() { return btnAddBattery; }
    public JComboBox<BatteryPack> getCmbBatteries() { return cmbBatteries; }

    private void updateScreen(BatteryPack pack) {
        lblVoltage.setText(String.format("%.2f V", pack.getVoltage()));
        lblCurrent.setText(String.format("%.2f A", pack.getCurrent()));
        lblSoC.setText(String.format("%.1f %%", pack.getStateOfCharge()));
        lblTemp.setText(String.format("%.1f °C", pack.getTemperature()));
        lblTemp.setForeground(pack.getTemperature() > 40.0 ? Color.RED : Color.BLACK);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("telemetry".equals(evt.getPropertyName())) {
            BatteryPack pack = (BatteryPack) evt.getSource();
            // Solo actualiza si es la batería que estamos mirando
            if (pack == cmbBatteries.getSelectedItem()) {
                updateScreen(pack);
            }
        }
    }
}