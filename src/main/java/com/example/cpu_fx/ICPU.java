package com.example.cpu_fx;

public interface ICPU {
    void exec(Command command);
    Integer getMemCell(Integer ind);
    Integer getReg(String name);
    Integer getFlagState(String compare);
    void clearFlagsState();
    void clearMemory();
    void clearRegisters();
}
