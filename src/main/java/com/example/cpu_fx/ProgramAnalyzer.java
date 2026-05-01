package com.example.cpu_fx;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramAnalyzer {
    private static final Map<String, Integer> commandMap = new HashMap<>();
    public void init(IProgram program) {
        Iterator<Command> iterator = program.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().getName();
            commandMap.put(key, commandMap.getOrDefault(key, 0) + 1);
        }
    }

    public void clearCmdMap() {
        commandMap.clear();
    }

    public void frequentInstruction() {
        String maxCountInstruction = commandMap.entrySet()
                                               .stream()
                                               .max(Map.Entry.comparingByValue())
                                               .map(Map.Entry::getKey)
                                               .orElse(null);
        System.out.println("Most used instruction: " + maxCountInstruction);
    }

    public Map<String, Integer> printInstructions() {
        return commandMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
