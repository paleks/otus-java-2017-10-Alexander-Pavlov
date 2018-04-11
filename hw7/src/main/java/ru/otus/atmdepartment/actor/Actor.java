package ru.otus.atmdepartment.actor;

import ru.otus.atmdepartment.messages.Message;

public interface Actor {
    public Object act(Message message);
}
