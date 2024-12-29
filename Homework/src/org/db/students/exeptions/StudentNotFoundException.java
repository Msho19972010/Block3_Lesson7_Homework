package org.db.students.exeptions;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException() {
        super("Student not found.");
    }
}
