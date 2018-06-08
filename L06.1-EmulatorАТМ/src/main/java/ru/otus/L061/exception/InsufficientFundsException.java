package ru.otus.L061.exception;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(int requestedAmount) {
        super("Недостаточно Средств: " + requestedAmount);
    }
}
