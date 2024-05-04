package com.zain.robot.backend.domain.enums;

import lombok.ToString;

import java.util.Arrays;

@ToString
public enum OperationType {

    POSITION("POSITION"),
    FORWARD("FORWARD"),
    REVERSE("REVERSE"),
    WAIT("WAIT"),
    TURNAROUND("TURNAROUND"),
    RIGHT("RIGHT");

    private final String operationType;

    OperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getValue() {
        return this.operationType;
    }

    public static OperationType findByValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.operationType.equals(value))
                .findFirst()
                .orElse(null);
    }

}
