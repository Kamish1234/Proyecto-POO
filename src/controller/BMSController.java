package controller;

import model.BatteryPack;
import view.BMSDashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class BMSController {
    private BMSDashboard view;
    private Timer simulationTimer;
    private int batteryCounter = 1;
    private ArrayList<BatteryPack> batteryList; 

    public BMSController(BatteryPack initialModel, BMSDashboard view) {
        this.view = view;
        this.batteryList = new ArrayList<>();
        initController();
        
        view.getBtnAddBattery().doClick(); 
    }

    private void initController() {
        view.getBtnAddBattery().addActionListener(e -> {
            BatteryPack newPack = new BatteryPack("Batería #" + batteryCounter++);
            newPack.addPropertyChangeListener(view);
            batteryList.add(newPack); 
            view.getCmbBatteries().addItem(newPack);
        });

        view.getBtnCharge().addActionListener(e -> {
            BatteryPack selected = (BatteryPack) view.getCmbBatteries().getSelectedItem();
            if (selected != null) selected.setState(BatteryPack.State.CHARGING);
        });

        view.getBtnDischarge().addActionListener(e -> {
            BatteryPack selected = (BatteryPack) view.getCmbBatteries().getSelectedItem();
            if (selected != null) selected.setState(BatteryPack.State.DISCHARGING);
        });

        view.getBtnStop().addActionListener(e -> {
            BatteryPack selected = (BatteryPack) view.getCmbBatteries().getSelectedItem();
            if (selected != null) selected.setState(BatteryPack.State.IDLE);
        });

        simulationTimer = new Timer(500, (ActionEvent e) -> {
            for (BatteryPack pack : batteryList) {
                switch (pack.getState()) {
                    case CHARGING:
                        pack.applyCharge(2.0);
                        break;
                    case DISCHARGING:
                        pack.applyDischarge(3.5);
                        break;
                    case IDLE:
                        pack.idle();
                        break;
                }
            }
        });
        simulationTimer.start();
    }
}