package Main;

import frame.Home; 

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
            
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            @Override public void write(int b) {}
        }));
        System.setErr(new java.io.PrintStream(new java.io.OutputStream() {
            @Override public void write(int b) {}
        }));
        });
    }
}
