package com.Project.StudentManagementSystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentService {

    // Single SessionFactory for the whole application
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Add a new student
    public void addStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get student by ID
    public Student getStudentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all students
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Student", Student.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update student
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete student by ID
    public void deleteStudent(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Close factory when application ends (optional – can call from GUI exit)
    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
