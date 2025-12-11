package com.Project.StudentManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentManagementGUI extends JFrame {

    private final StudentService service;

    public StudentManagementGUI() {
        service = new StudentService();

        setTitle("Student Management System");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Student Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));
        add(title, BorderLayout.NORTH);

        // Button Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 60, 60));

        JButton addBtn = new JButton("Add Student");
        JButton getBtn = new JButton("Get Student");
        JButton updateBtn = new JButton("Update Student");
        JButton deleteBtn = new JButton("Delete Student");
        JButton viewAllBtn = new JButton("View All Students");
        JButton exitBtn = new JButton("Exit");

        Font btnFont = new Font("Arial", Font.PLAIN, 18);
        addBtn.setFont(btnFont); getBtn.setFont(btnFont); updateBtn.setFont(btnFont);
        deleteBtn.setFont(btnFont); viewAllBtn.setFont(btnFont); exitBtn.setFont(btnFont);

        panel.add(addBtn);
        panel.add(getBtn);
        panel.add(updateBtn);
        panel.add(deleteBtn);
        panel.add(viewAllBtn);
        panel.add(exitBtn);

        add(panel, BorderLayout.CENTER);

        // Action Listeners - All using JOptionPane
        addBtn.addActionListener(e -> addStudent());
        getBtn.addActionListener(e -> getStudent());
        updateBtn.addActionListener(e -> updateStudent());
        viewAllBtn.addActionListener(e -> viewAllStudents());
        deleteBtn.addActionListener(e -> deleteStudent());
        exitBtn.addActionListener(e -> exitApp());

        setVisible(true);
    }

    private void addStudent() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField marksField = new JTextField();
        JTextField typeField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Email:", emailField,
            "Marks:", marksField,
            "Type:", typeField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                int marks = Integer.parseInt(marksField.getText().trim());
                String type = typeField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || type.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Student student = new Student(name, email, marks, type);
                service.addStudent(student);
                JOptionPane.showMessageDialog(this, "Student Added Successfully!\nID: " + student.getId());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Marks must be a number!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getStudent() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Student ID:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Student s = service.getStudentById(id);
                if (s != null) {
                    JOptionPane.showMessageDialog(this, s.toString(), "Student Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Student Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateStudent() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Student ID to Update:");
        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());
            Student s = service.getStudentById(id);
            if (s == null) {
                JOptionPane.showMessageDialog(this, "Student Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField marksField = new JTextField(String.valueOf(s.getMarks()));
            JTextField typeField = new JTextField(s.getType());

            Object[] fields = {
                "Current: " + s,
                "", 
                "New Marks (leave blank to keep):", marksField,
                "New Type (leave blank to keep):", typeField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (!marksField.getText().trim().isEmpty()) {
                    s.setMarks(Integer.parseInt(marksField.getText().trim()));
                }
                if (!typeField.getText().trim().isEmpty()) {
                    s.setType(typeField.getText().trim());
                }
                service.updateStudent(s);
                JOptionPane.showMessageDialog(this, "Student Updated Successfully!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Student ID to Delete:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Student s = service.getStudentById(id);
                if (s != null) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Delete Student?\n" + s, "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        service.deleteStudent(id);
                        JOptionPane.showMessageDialog(this, "Student Deleted Successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Student Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewAllStudents() {
        List<Student> students = service.getAllStudents();
        if (students == null || students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Students Found!", "View All", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("All Students:\n\n");
        for (Student s : students) {
            sb.append(s).append("\n\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, scroll, "All Students", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exitApp() {
        service.shutdown();
        System.exit(0);
    }

    // Remove the main() method if you use App.java to launch
}