package com.example.cpu_fx;

import java.util.Iterator;

public interface IProgram {
    void add(Command command) throws Exception;
    Iterator<Command> IteratorExecutor();
//    Iterator<Command> IteratorAllCommands();
    Iterator<Command> iterator();
}
