package com.example.cpu_fx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CPU implements ICPU {
    private final int[] memory;
    private final Map<String, Integer> registers = new HashMap<>();
    private final Map<String, Integer> flags = new HashMap<>();
    public CPU() {
        memory = new int[1024];
        registers.put("A", Integer.valueOf(0));
        registers.put("B", 0);
        registers.put("C", 0);
        registers.put("D", 0);
        flags.put("e",  0); // equal
        flags.put("g",  0); // greater
        flags.put("l",  0); // less
        flags.put("le", 0); // less or equal
        flags.put("ge", 0); // greater or equal
        flags.put("ne", 0); // not equal
    }

    @Override
    public int getMemCell(int ind) {
        if (ind >= 0 && ind < memory.length)
            return memory[ind];
        return 0;
    }

    @Override
    public int getReg(String name) {
        return registers.get(name);
    }
    @Override
    public int getFlagState(String compare) {
        return flags.get(compare);
    }
    @Override
    public void clearFlagsState() {
        flags.replaceAll((key, value) -> 0);
    }

    @Override
    public void clearMemory() {
        Arrays.fill(memory, 0);
    }

    @Override
    public void clearRegisters() {
        registers.replaceAll((key, value) -> 0);
    }

    @Override
    public void exec(Command command) {
        switch (command.getName()) {
            case "ADD" :
                registers.put("D", registers.get("A") + registers.get("B"));
                break;
            case "DIV" :
                registers.put("D", registers.get("A") / registers.get("B"));
                break;
            case "INIT" :
                memory[Integer.parseInt(command.getFirstArg())] = Integer.parseInt(command.getSecondArg());
                break;
            case "LD" :
                registers.put(command.getFirstArg(), memory[Integer.parseInt(command.getSecondArg())]);
                break;
            case "MUL" :
                registers.put("D", Integer.parseInt(command.getFirstArg()) * Integer.parseInt(command.getSecondArg()));
                break;
            case "MV" :
                registers.put(command.getFirstArg(), registers.get(command.getSecondArg()));
                break;
            case "PRINT" :
                System.out.printf(  "A = " + registers.get("A") + ", B = " + registers.get("B") +
                                  ", C = " + registers.get("C") + ", D = " + registers.get("D") + "\n");
                break;
            case "ST" :
                memory[Integer.parseInt(command.getFirstArg())] = registers.get(command.getSecondArg());
                break;
            case "SUB" :
                registers.put("D", registers.get("A") - registers.get("B"));
                break;
            case "CMP" :
                int firstReg  = registers.get(command.getFirstArg());
                int secondReg = registers.get(command.getSecondArg());
                flags.put("e",  firstReg == secondReg ? 1 : 0);
                flags.put("ne", firstReg != secondReg ? 1 : 0);
                flags.put("g",  firstReg  > secondReg ? 1 : 0);
                flags.put("l",  firstReg  < secondReg ? 1 : 0);
                flags.put("ge", firstReg >= secondReg ? 1 : 0);
                flags.put("le", firstReg <= secondReg ? 1 : 0);
                break;
        }
    }
}
