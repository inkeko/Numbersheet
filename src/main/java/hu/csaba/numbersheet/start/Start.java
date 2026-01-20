package hu.csaba.numbersheet.start;


import hu.csaba.numbersheet.error.AppException;
import hu.csaba.numbersheet.error.ErrorHandler;

public class Start {

    public void run() {
        try {
            ProgramLauncher.start();
        } catch (AppException e) {
            ErrorHandler.handle(e);
        }
    }
}





