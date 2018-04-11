package ru.otus.atmdepartment.commands;

import ru.otus.atmdepartment.messages.Message;

@FunctionalInterface
public interface Command {
    public void execute(Message msg);
}
