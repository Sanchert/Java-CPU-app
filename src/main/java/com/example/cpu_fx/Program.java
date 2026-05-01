package com.example.cpu_fx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Program implements IProgram, Iterable<Command> {
    ICPU cpu = BCPU.build();
    private final ArrayList<Command> commandList = new ArrayList<>();
    private final ArrayList<IObserver> allObserver = new ArrayList<>();

    public void eventCall() {
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver iob) {
        allObserver.add(iob);
    }

    @Override
    public void add(Command command) {
        commandList.add(command);
    }

    public void removeCmd(Command command) {
        commandList.remove(command);
        eventCall();
    }

    @Override
    public Iterator<Command> iterator() {
        return commandList.iterator();
    }

    @Override
    public Iterator<Command> IteratorExecutor() {
        return new iteratorExecutor();
    }

    private class iteratorExecutor implements Iterator<Command> {
        private int IP = 0;

        @Override
        public boolean hasNext() {
            return IP < commandList.size();
        }

        @Override
        public Command next() {
            Command cmd = commandList.get(IP);

            if (cmd.getName().equals("JMP")) {
                IP = Integer.parseInt(cmd.getFirstArg());
            } else {
                IP += handleJump(cmd.getName().equals("JE"), "e", cpu, cmd.getFirstArg());
                IP += handleJump(cmd.getName().equals("JLE"), "le", cpu, cmd.getFirstArg());
                IP += handleJump(cmd.getName().equals("JGE"), "ge", cpu, cmd.getFirstArg());
                IP += handleJump(cmd.getName().equals("JG"), "g", cpu, cmd.getFirstArg());
                IP += handleJump(cmd.getName().equals("JL"), "l", cpu, cmd.getFirstArg());
                IP += handleJump(cmd.getName().equals("JNE"), "ne", cpu, cmd.getFirstArg());
            }
            return commandList.get(IP++);
        }
    }

    private int handleJump(boolean isJump, String flag, ICPU cpu, String strArg) {
        if (isJump && cpu.getFlagState(flag) == 1) {
            cpu.clearFlagsState();
            return Integer.parseInt(strArg);
        }
        return 0;
    }

    public int index(Command cmd) {
        return commandList.indexOf(cmd);
    }

    public void mvUp(Command cmd) {
        Collections.swap(commandList, commandList.indexOf(cmd), commandList.indexOf(cmd) - 1);
        eventCall();
    }

    public void mvDown(Command cmd) {
        Collections.swap(commandList, commandList.indexOf(cmd), commandList.indexOf(cmd) + 1);
        eventCall();
    }

    public int size() {
        return commandList.size();
    }
}
