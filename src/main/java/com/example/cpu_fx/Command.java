package com.example.cpu_fx;

public class Command {
    private final String firstArg;
    private final String secondArg;
    private final String name;

    public Command(String description) {
        String[] arguments = description.split(" ");
        name = arguments[0];
        firstArg  = (arguments.length >= 2) ? arguments[1] : "";
        secondArg = (arguments.length == 3) ? arguments[2] : "";
    }

    public String getDescription() { return name + " " + firstArg + " " + secondArg; }
    public String getFirstArg() { return firstArg; }
    public String getSecondArg() { return secondArg; }
    public String getName() { return name; }
}
