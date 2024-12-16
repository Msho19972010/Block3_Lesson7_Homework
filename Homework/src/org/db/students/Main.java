package org.db.students;

import com.sun.jdi.Value;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static StudentCommandHandler STUDENT_COMMAND_HANDLER = new StudentCommandHandler();

    public static void main(String[] args) {
        while(true) {
            //Output a list of variants
            printMessage();
            //reading an action's number and additional data
            Command command = readCommand();
            if(command.getAction() == Action.EXIT) {
                return;
            } else if(command.getAction() == Action.ERROR){
                continue;
            } else {
                //perform an action
                STUDENT_COMMAND_HANDLER.processCommand(command);
            }
        }


    }

    private static Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        try{
            String code = scanner.nextLine();
            Integer actionCode = Integer.valueOf(code);
            Action action = Action.fromCode(actionCode);

            if(action.isRequiredAdditionalData()) {
                String data = null;
                if(action == Action.CREATE || action == Action.UPDATE) {
                    System.out.println("Sequence: 'Surname,Name,Course,Town,Age'");
                    data = validation(scanner.nextLine());
                } else if (action == Action.SEARCH) {
                    System.out.println("If you want to see the list of student just press 'Enter'.");
                    System.out.println("If you want to find someone specific, write a surname please.");
                    data = scanner.nextLine();
                } else {
                    data = scanner.nextLine();
                }
                return new Command(action, data);
            } else {
                return new Command(action);
            }
        } catch(Exception ex) {
            System.out.println("The problem of input processing. " + ex.getMessage());
            return new Command(Action.ERROR);
        }
    }

    private static void printMessage() {
        System.out.println("------------------------");
        System.out.println("0. Exit");
        System.out.println("1. Creating data");
        System.out.println("2. Updating data");
        System.out.println("3. Deleting data");
        System.out.println("4. Statistic outputting by courses");
        System.out.println("5. Statistic outputting by cities");
        System.out.println("6. Searching by surnames");
        System.out.println("------------------------");
    }

    private static String validation(String data) {
        Scanner scanner = new Scanner(System.in);
        String[] inputData = data.split(",");
        String mainInfoRegEx = "^[A-Z]+[a-z]+-*[A-z]*[a-z]*$";
        String ageRegEx = "[0-9]+";
        Pattern mainInfoPattern = Pattern.compile(mainInfoRegEx);
        Pattern agePattern = Pattern.compile(ageRegEx);

        //Name,Surname,Course and Town validation
        for (int i = 0; i < 4; i++) {
            boolean mainInfoValid = true;
            Matcher mainInfoMatcher = mainInfoPattern.matcher(inputData[i]);
            while (mainInfoValid != mainInfoMatcher.matches()) {
                System.out.println("Name, Surname, Town and Course have to contain just letters, the first letter must by in High register, please rewrite");
                System.out.println("Example: Brown,Tom,Java,Prague");
                System.out.println("You hava a mistake in: '" + inputData[i] + "' word");
                inputData[i] = scanner.nextLine();
                mainInfoValid = mainInfoMatcher.matches();
            }
        }

        //Age validation
        boolean ageValid = true;
        Matcher ageMatcher = agePattern.matcher(inputData[4]);
        while(ageValid != ageMatcher.matches()) {
            System.out.println("Age can contain just integer, please rewrite");
            inputData[4] = scanner.nextLine();
            ageValid = ageMatcher.matches();
        }

        //Separate extra symbols
        String finalRegEx = "[\\[\\] ]";
        data = Arrays.toString(inputData).replaceAll(finalRegEx, "");

        return data;
    }
}
