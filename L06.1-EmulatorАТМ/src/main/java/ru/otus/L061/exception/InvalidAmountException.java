package ru.otus.L061.exception;

public class InvalidAmountException extends Exception {
    public InvalidAmountException(int requestedAmount) {
        super("Нет подходящего номинала для выдачи запрашиваемой суммы: " + requestedAmount);
    }
}
