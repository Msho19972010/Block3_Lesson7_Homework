package org.db.students;

import org.db.students.exeptions.StudentNotFoundException;
import org.db.students.exeptions.TheDataBaseIsEmpty;

import java.util.Map;

public class StudentCommandHandler {

    private StudentStorage studentStorage = new StudentStorage();

    public void processCommand(Command command) throws StudentNotFoundException, TheDataBaseIsEmpty {
        Action action = command.getAction();
        switch (action) {
            case CREATE -> {
                processCreateCommand(command);
                break;
            }
            case UPDATE -> {
                processUpdateCommand(command);
                break;
            }
            case DELETE -> {
                processDeleteCommand(command);
                break;
            }
            case STATS_BY_COURSES -> {
                processStatsByCourseCommand(command);
                break;
            }
            case STATS_BY_CITIES -> {
                processStatsByCitiesCommand(command);
                break;
            }
            case SEARCH -> {
                processSearchCommand(command);
                break;
            }
            case SHAW_ALL_SURNAMES -> {
                processShawAllStudents(command);
                break;
            }
            default -> {
                System.out.println("This action "+ action + " doesn't supported");
            }
        }
        System.out.println("A command processing. " +
                "Action: " + command.getAction().name() +
                ", data: " + command.getData());
    }

    private void processSearchCommand(Command command) throws TheDataBaseIsEmpty, StudentNotFoundException {
        int surnamesCount = command.getData().split(",").length;
        if (surnamesCount == 1) {
            String surname = command.getData();
            System.out.println(studentStorage.search(surname));
        } else if (surnamesCount == 2) {
            String[] surnames = command.getData().split(",");
            studentStorage.searchTheRangeOfSurnames(surnames[0], surnames[1]);
        }

    }

    private void processShawAllStudents(Command command) throws TheDataBaseIsEmpty {
        studentStorage.printAll();
    }

    private void processStatsByCourseCommand(Command command) throws TheDataBaseIsEmpty {
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }

    private void processStatsByCitiesCommand(Command command) throws TheDataBaseIsEmpty {
        Map<String, Long> data = studentStorage.getCountByCities();
        studentStorage.printMap(data);
    }

    private void processCreateCommand(Command command) throws TheDataBaseIsEmpty {
        String data = command.getData();
        String[] dataArray = data.split(",");

        Student student = new Student();
        student.setSurname(dataArray[0]);
        student.setName(dataArray[1]);
        student.setCourse(dataArray[2]);
        student.setTown(dataArray[3]);
        student.setAge(Integer.valueOf(dataArray[4]));

        studentStorage.createStudent(student);
        studentStorage.printAll();
    }

    public void processUpdateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");
        Long id = Long.valueOf(dataArray[0]);

        Student student = new Student();
        student.setSurname(dataArray[1]);
        student.setName(dataArray[2]);
        student.setCourse(dataArray[3]);
        student.setTown(dataArray[4]);
        student.setAge(Integer.valueOf(dataArray[5]));

        studentStorage.updateStudent(id, student);
    }

    public void processDeleteCommand(Command command) throws StudentNotFoundException, TheDataBaseIsEmpty {
        String data = command.getData();
        Long id = Long.valueOf(data);

        studentStorage.deleteStudent(id);
        studentStorage.printAll();
    }
}
