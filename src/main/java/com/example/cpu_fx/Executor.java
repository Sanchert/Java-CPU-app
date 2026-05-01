package com.example.cpu_fx;

import java.util.Iterator;

public class Executor {
    ICPU cpu = BCPU.build();
    public void run(IProgram prog) {
        Iterator<Command> progIterator = prog.IteratorExecutor();
        while (progIterator.hasNext()) {
            cpu.exec(progIterator.next());
        }
    }
}