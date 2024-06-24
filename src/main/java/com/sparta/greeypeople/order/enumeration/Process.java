package com.sparta.greeypeople.order.enumeration;

import lombok.Getter;

@Getter
public enum Process {
    COMPLETED("완료"),
    CANCELED("취소");

    private final String process;

    Process(String process) {
        this.process = process;
    }
}
