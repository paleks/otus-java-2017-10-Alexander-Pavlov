package ru.otus.atmdepartment.commands;

public interface CommandWithResult extends Command {
    public Object getResult();
}
