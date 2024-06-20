package com.sparta.greeypeople.order.entity;

public enum Process {
    COMPLETED("완료"),
    CANCELED("관리자");

    private final String process;

    Process(String process) {
        this.process = process;
    }

    public String getProcess() {
        return this.process;
    }
}
