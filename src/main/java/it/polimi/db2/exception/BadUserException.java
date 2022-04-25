package it.polimi.db2.exception;

public class BadUserException extends Exception{
    private static final long serialVersionUID = 1L;

    public BadUserException(String message) {super((message));}
}
