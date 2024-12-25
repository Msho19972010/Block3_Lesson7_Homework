package org.db.students;

import java.util.Arrays;
import java.util.Scanner;
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
                    data = scanner.nextLine();
                    if(!(data.split(",").length == 5)) {
                        while (!(data.split(",").length == 5)) {
                            System.out.println("Please enter right sequence: 'Surname,Name,Course,Town,Age'");
                            data = scanner.nextLine();
                        }
                    }
                    data = mainInfoValidation(data);
                } else if (action == Action.SEARCH) {
                    System.out.println("If you want to see the list of student just press 'Enter'.");
                    System.out.println("If you want to find someone specific, write a surname please.");
                    data = scanner.nextLine();
                    if(!data.isEmpty()) {
                        data = mainInfoValidation(data);
                    }
                } else if(action == Action.DELETE) {
                    System.out.println("If you want to delete some student please enter a student id.");
                    data = numbersValidation(scanner.nextLine());
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

    private static String mainInfoValidation(String data) {
        String[] inputData = data.split(",");
        Scanner scanner = new Scanner(System.in);
        String mainInfoRegEx = "^[A-Z]+[a-z]+-*[A-z]*[a-z]*$";
        Pattern mainInfoPattern = Pattern.compile(mainInfoRegEx);

        for (int i = 0; i < inputData.length; i++) {
            if(i < 4) {
                while (!mainInfoPattern.matcher(inputData[i]).matches()) {
                    //Name,Surname,Course and Town validation
                    System.out.println("Surname, Name, Town and Course have to contain just letters, the first letter must by in High register, please rewrite");
                    System.out.println("Example: Brown,Tom,Java,Prague");
                    System.out.println("You hava a mistake in: '" + inputData[i] + "' word");
                    inputData[i] = scanner.nextLine();
                }
            } else {
                inputData[i] = numbersValidation(inputData[i]);
            }
        }

        String finalRegEx = "[\\[\\] ]";
        data = Arrays.toString(inputData).replaceAll(finalRegEx, "");

        return data;
    }

    private static String numbersValidation(String data) {
        //Numbers validation
        Scanner scanner = new Scanner(System.in);
        String numbersRegEx = "[0-9]+";
        Pattern numsPattern = Pattern.compile(numbersRegEx);
        while(!numsPattern.matcher(data).matches()) {
            System.out.println("Here must be integer, but you wrote: '" + data +  "' please rewrite");
            data = scanner.nextLine();
        }

        return data;
    }
}
