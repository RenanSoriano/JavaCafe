package main.java.com.cafe;

public class CustomExceptions {
    public static class OutOfStockException extends Exception {
        public OutOfStockException(String message) {
            super(message);
        }
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }
}
