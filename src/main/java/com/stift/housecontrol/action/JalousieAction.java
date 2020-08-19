package com.stift.housecontrol.action;

public enum JalousieAction {

    UP("0 up"),
    DOWN("1 down");

    private final String command;

    JalousieAction(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
