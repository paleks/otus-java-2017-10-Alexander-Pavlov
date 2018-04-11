package ru.otus.atmdepartment.actor;

import ru.otus.atmdepartment.messages.Message;
import ru.otus.atmdepartment.commands.*;

import java.util.HashMap;
import java.util.Map;

public class ActorImpl implements Actor {
    static Map<Message.Type, CommandWithResult> cmd = new HashMap<>();
    static {
        cmd.put(Message.Type.TAKE_IN,  new TakeInCommand());
        cmd.put(Message.Type.WITHDRAW, new WithdrawCommand());
        cmd.put(Message.Type.BALANCE,  new GetBalanceCommand());
        cmd.put(Message.Type.RESET,    new ResetCommand());
        cmd.put(Message.Type.TOTAL_BALANCE,  new GetTotalBalanceCommand());
        cmd.put(Message.Type.RESET_ALL,new ResetAllCommand());
    }

    @Override
    public Object act(Message msg) {
        CommandWithResult command = cmd.get(msg.getType());
        command.execute(msg);
        return command.getResult();
    }
}
