package com.example.ura.myapplication_15;


public class Control {
    Command slot;
    Control() {        super();    }
    void SetCommand(Command command) {
        this.slot = command;
    }
    void press(int i){
        slot.execute(i);
    }
}
