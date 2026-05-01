package com.example.cpu_fx;

import javafx.fxml.FXML;

public class CommandController {
    static Program prog = BProgram.build();
    private Command cmd;

    public void setCommand(Command _cmd) {
        cmd = _cmd;
    }

    @FXML
    protected void onDeleteButtonClick() {
        prog.removeCmd(cmd);
    }

    @FXML
    protected void move_up() {
        if (prog.index(cmd) != 0) {
            prog.mvUp(cmd);
        }
    }

    @FXML
    protected void move_down() {
        if (prog.index(cmd) < prog.size() - 1) {
            prog.mvDown(cmd);
        }
    }
}
