package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.enums.OperationType;

public interface PositionCalculatorService {
    Long deduceColPosition(Long currentPosition, int steps, String facePosition);

    Long deduceRowPosition(Long currentPosition, int steps, String facePosition);

    String deduceFacePosition(OperationType operationType, String commandFacePosition);
}
