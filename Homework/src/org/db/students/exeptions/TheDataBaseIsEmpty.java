package org.db.students.exeptions;

public class TheDataBaseIsEmpty extends Exception{
    public TheDataBaseIsEmpty() {
        super("The data base is empty.");
    }
}
