package org.db.students;

import java.util.*;
import java.util.stream.Collectors;

public class StudentStorage {
    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private Long currentId = 0L;

    /**
     * Creating data about students
     * @param student a student's data
     * @return a generated identification for student
     */

    public Long createStudent(Student student) {
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreate(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Updating students' data
     * @param id a student's identification
     * @param student a student's data
     * @return true if data were updated or false if student wasn't found
     */
    public boolean updateStudent(Long id, Student student) {
        if(!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            String newSurname = student.getSurname();
            String oldSurname = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldSurname, newSurname);
            studentStorageMap.put(id, student);
            return true;
        }
    }

    /**
     * Deleting a student's data
     * @param id a student's id
     * @return true if a student was found and false if wasn't
     */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if(removed != null) {
            String surname = removed.getSurname();
            System.out.println("Student" + surname + " is deleted");
            studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;
    }

    public Set<Student> search(String surname) {
        isEmptyDB();
        Set<Student> studentsWithEqualSurnames = new HashSet<>();
        for(Student student : studentStorageMap.values()) {
            if(surname.equals(student.getSurname())) {
                studentsWithEqualSurnames.add(student);
            } else {
                System.out.println("The student with the surname: " + surname + " doesn't exist in our database");
            }
        }
        return studentsWithEqualSurnames;
    }

    public void searchTheRangeOfSurnames(String surname1, String surname2) {
        isEmptyDB();
        for(Student student : studentStorageMap.values()) {
            if(studentSurnameStorage.getStudentsBySurnames(surname1, surname2).contains(student.getSurname()) || student.getSurname().equals(surname2)) {
                System.out.println(student);
            }
        }
    }


    public Long getNextId() {
        currentId = currentId + 1;
        return currentId;
    }

    public void printAll() {
        isEmptyDB();
        for(Student student : studentStorageMap.values()) {
            System.out.println(" - " + student);
        }
    }

    public void printMap(Map<String, Long> data) {
        data.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey() + " - " + e.getValue());
        });
    }

    public Map<String, Long> getCountByCourse() {
        isEmptyDB();
        Map<String, Long> data = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCourse(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));

        return data;
    }

    public Map<String, Long> getCountByCities() {
        isEmptyDB();
        Map<String, Long> data = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getTown(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));

        return data;
    }

    public void isEmptyDB() {
        if(studentStorageMap.isEmpty()) {
            System.out.println("The database is empty.");
        }
    }
}
