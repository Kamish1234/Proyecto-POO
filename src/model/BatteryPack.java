package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BatteryPack {
    public enum State { IDLE, CHARGING, DISCHARGING } 
    
    private String name;
    private State state; 
    private double voltage; 
    private double current; 
    private double temperature; 
    private double stateOfCharge; 
    
    public static final double MAX_VOLTAGE = 12.6; 
    public static final double MIN_VOLTAGE = 9.0;
    public static final double MAX_TEMP = 60.0;

    private PropertyChangeSupport support;

    public BatteryPack(String name) {
        this.name = name;
        this.state = State.IDLE; 
        this.voltage = 11.1; 
        this.current = 0.0;
        this.temperature = 25.0; 
        this.stateOfCharge = 50.0; 
        this.support = new PropertyChangeSupport(this);
    }

    public void setState(State state) { this.state = state; }
    public State getState() { return state; }

    @Override
    public String toString() { return name; }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void applyCharge(double chargeCurrent) {
        if (voltage >= MAX_VOLTAGE) {
            this.state = State.IDLE; 
            return;
        }
        double oldVoltage = this.voltage;
        this.current = chargeCurrent;
        this.voltage += 0.05; 
        this.stateOfCharge += 0.5; 
        this.temperature += 0.1; 
        support.firePropertyChange("telemetry", oldVoltage, this.voltage);
    }

    public void applyDischarge(double loadCurrent) {
        if (voltage <= MIN_VOLTAGE) {
            this.state = State.IDLE; 
            return;
        }
        double oldVoltage = this.voltage;
        this.current = -loadCurrent;
        this.voltage -= 0.05;
        this.stateOfCharge -= 0.5;
        this.temperature += 0.2; 
        support.firePropertyChange("telemetry", oldVoltage, this.voltage);
    }

    public void idle() {
        double oldVoltage = this.voltage;
        this.current = 0.0;
        if (this.temperature > 25.0) this.temperature -= 0.1;
        support.firePropertyChange("telemetry", oldVoltage, this.voltage);
    }

    public double getVoltage() { return voltage; }
    public double getCurrent() { return current; }
    public double getTemperature() { return temperature; }
    public double getStateOfCharge() { return stateOfCharge; }
}