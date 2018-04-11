package ru.otus.atmdepartment.messages;

public abstract class Message {
    public enum Type {
        TAKE_IN, WITHDRAW, BALANCE, RESET, TOTAL_BALANCE, RESET_ALL
    }

    private final Type type;

    public Message(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
