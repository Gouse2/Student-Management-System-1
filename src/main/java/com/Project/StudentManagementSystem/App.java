package com.Project.StudentManagementSystem;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread (correct way for Swing)
        SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI();
        });
    }
}