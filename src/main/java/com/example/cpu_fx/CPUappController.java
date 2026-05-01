package com.example.cpu_fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.*;

public class CPUappController implements IObserver {
    ICPU cpu = BCPU.build();
    static Program prog = BProgram.build();

    @FXML
    public GridPane allCommands;

    @FXML
    public TextField cmdText;

    @FXML
    private Label text_reg_A;
    @FXML
    private Label text_reg_B;
    @FXML
    private Label text_reg_C;
    @FXML
    private Label text_reg_D;

    @FXML
    private ListView<String> mem_status;
    @FXML
    private ListView<String> instruction_set;

    @FXML
    void onAddButtonClick() {
        CommandController cc = new CommandController();
        FXMLLoader fxmlLoader = new FXMLLoader(CPU_Application.class.getResource("command-view.fxml"));
        Command cmd = new Command(cmdText.getText());
        cc.setCommand(cmd);
        prog.add(cmd);
        fxmlLoader.setController(cc);
        try {
            AnchorPane pane = fxmlLoader.load();
            ((Label)pane.getChildren().get(3)).setText(cmd.getDescription());
            allCommands.addColumn(0, pane);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        prog.addObserver(this);
    }

    private int i = 0;
    @FXML
    public void executeOne() {
        if (i < prog.size()) {
            (allCommands.getChildren().get(i)).setStyle("-fx-background-color: #87CEFA; -fx-border-color: red; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-width:2px");
            if (i > 0) {
                (allCommands.getChildren().get(i - 1)).setStyle("-fx-border-color: #00518f; -fx-background-color: #87CEFA; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-width:2px");
            }

            String description = ((Label) ((AnchorPane) (allCommands.getChildren().get(i))).getChildren().get(3)).getText();
            cpu.exec(new Command(description));

            text_reg_A.setText(Integer.toString(cpu.getReg("A")));
            text_reg_B.setText(Integer.toString(cpu.getReg("B")));
            text_reg_C.setText(Integer.toString(cpu.getReg("C")));
            text_reg_D.setText(Integer.toString(cpu.getReg("D")));
            mem_status.getItems().clear();
            List<String> mem = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                String stat = j + ": " + cpu.getMemCell(j);
                mem.add(stat);
            }
            mem_status.getItems().addAll(mem);
            i++;
        }
    }

    @FXML
    public void reset_click() {
        if (i > 0) {
            (allCommands.getChildren().get(i - 1)).setStyle("-fx-border-color:  #00518f; -fx-background-color: #87CEFA; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-width:2px");
        }
        text_reg_A.setText("0");
        text_reg_B.setText("0");
        text_reg_C.setText("0");
        text_reg_D.setText("0");
        mem_status.getItems().clear();
        instruction_set.getItems().clear();
        cpu.clearFlagsState();
        cpu.clearMemory();
        cpu.clearRegisters();
        i = 0;
    }

    @FXML
    public void runProg() {
        Executor exec = new Executor();
        exec.run(prog);
        text_reg_A.setText(Integer.toString(cpu.getReg("A")));
        text_reg_B.setText(Integer.toString(cpu.getReg("B")));
        text_reg_C.setText(Integer.toString(cpu.getReg("C")));
        text_reg_D.setText(Integer.toString(cpu.getReg("D")));
        List<String> mem = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String stat = i +": "+ cpu.getMemCell(i);
            mem.add(stat);
        }
        mem_status.getItems().addAll(mem);
        List<String> instr = new ArrayList<>();

        ProgramAnalyzer p = new ProgramAnalyzer();
        p.init(prog);
        Map<String, Integer> instructions = p.printInstructions();
        for (Map.Entry<String, Integer> entry: instructions.entrySet()) {
            instr.add(entry.getKey() + ": " + entry.getValue());
        }
        instruction_set.getItems().addAll(instr);
        p.clearCmdMap();
    }

    @Override
    public void event(Program p) {
        allCommands.getChildren().clear();
        for (Command cmd : prog) {
            CommandController cc = new CommandController();
            FXMLLoader fxmlLoader = new FXMLLoader(CPU_Application.class.getResource("command-view.fxml"));
            cc.setCommand(cmd);
            fxmlLoader.setController(cc);
            try {
                AnchorPane pane = fxmlLoader.load();
                ((Label)pane.getChildren().get(3)).setText(cmd.getName() + " " + cmd.getFirstArg() + " " + cmd.getSecondArg());
                allCommands.addColumn(0,pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}