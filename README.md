# 🔋 Emulador de Sistema de Gestión de Baterías (BMS)

Este proyecto es un emulador de software desarrollado en **Java**, diseñado para simular el comportamiento físico y la telemetría de un **Battery Management System (BMS)** acoplado a múltiples paquetes de baterías de iones de litio. 

El sistema fue desarrollado como proyecto práctico para aplicar conceptos avanzados de **Programación Orientada a Objetos (POO)**, emulando rutinas de control mecatrónico sin necesidad de hardware físico mediante el uso de interrupciones de tiempo controladas.

## ✨ Características Principales

* **Gestión Multibatería Independiente:** Permite instanciar dinámicamente múltiples paquetes de baterías. Cada batería opera de forma asíncrona en su propio estado (`CHARGING`, `DISCHARGING`, `IDLE`).
* **Telemetría en Tiempo Real:** Monitorización continua de Voltaje (V), Corriente (A), Estado de Carga (SoC %) y Temperatura (°C).
* **Protecciones de Seguridad Simuladas:** * **OVP (Over-Voltage Protection):** Corte automático de carga al alcanzar el límite máximo de la celda (12.6V).
  * **UVP (Under-Voltage Protection):** Corte automático de descarga para prevenir degradación profunda (9.0V).
  * **OTP (Over-Temperature Protection):** Alertas visuales ante el estrés térmico (temperaturas > 40°C).
* **Simulación de Hardware:** Utiliza un bucle de tiempo (`javax.swing.Timer`) configurado a 500ms para emular el *polling* de un microcontrolador real leyendo sensores físicos.

## 🏗️ Arquitectura y Patrones de Diseño

El código está estructurado bajo el patrón arquitectónico **MVC (Modelo-Vista-Controlador)** para garantizar la separación de responsabilidades y la escalabilidad del sistema:

* **Modelo (`BatteryPack`):** Encapsula las propiedades físicas de las baterías y la lógica matemática de la simulación (suma/resta de valores según el estrés). No tiene dependencias de la interfaz gráfica.
* **Vista (`BMSDashboard`):** Interfaz gráfica desarrollada con **Java Swing**. Se encarga exclusivamente del renderizado de los datos y la captura de eventos de usuario (clics).
* **Controlador (`BMSController`):** Actúa como el puente lógico ("Microcontrolador"). Gestiona la lista de instancias de baterías, procesa los eventos de la vista y ejecuta el ciclo de reloj de la simulación.

### Patrones GoF Aplicados:
1. **Observer (Observador):** Implementado mediante `PropertyChangeListener`. El Modelo notifica automáticamente a la Vista sobre cualquier cambio en la telemetría, permitiendo que la interfaz sea completamente reactiva.
2. **Singleton:** Implementado en el módulo de registros (`SystemLogger`) para centralizar las alertas y eventos del sistema en un único punto de acceso global (emulando la escritura en una EEPROM).

## 📂 Estructura del Proyecto

```text
src/
├── main/
│   └── Main.java             # Punto de entrada de la aplicación
├── model/
│   └── BatteryPack.java      # Lógica de la celda y máquina de estados
├── view/
│   └── BMSDashboard.java     # Interfaz gráfica de usuario (GUI)
└── controller/
    ├── BMSController.java    # Bucle de simulación y gestión de la lista
    └── SystemLogger.java     # Módulo centralizado de logs (Singleton)