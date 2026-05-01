package com.example.cpu_fx;

public interface ICPU {
    void exec(Command command);
    int getMemCell(int ind);
    int getReg(String name);
    int getFlagState(String compare);
    void clearFlagsState();
    void clearMemory();
    void clearRegisters();
}
