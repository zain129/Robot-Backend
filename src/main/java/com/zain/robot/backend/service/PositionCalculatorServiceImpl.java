package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.util.RobotBackendConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.zain.robot.backend.util.RobotBackendConstant.*;

@Service
public class PositionCalculatorServiceImpl implements PositionCalculatorService {

    @Value("${robot.grid.size}")
    private Integer gridSize;

    @Override
    public Long deduceColPosition(Long currentPosition, int steps, String facePosition) {
        long newPosition = currentPosition;
        if (Objects.equals(facePosition, RobotBackendConstant.FACE_RIGHT)) {
            newPosition += steps;
        }
        return (newPosition > gridSize) ? (newPosition % gridSize) : newPosition;
    }

    @Override
    public Long deduceRowPosition(Long currentPosition, int steps, String facePosition) {
        long newPosition = currentPosition;
        if (Objects.equals(facePosition, RobotBackendConstant.FACE_UP)) {
            newPosition -= steps;
            if (newPosition < 1) {
                newPosition = Math.abs(newPosition);
            }
        } else if (Objects.equals(facePosition, RobotBackendConstant.FACE_DOWN)) {
            newPosition += steps;
        }
        return (newPosition > gridSize) ? (newPosition % gridSize) : newPosition;
    }

    @Override
    public String deduceFacePosition(OperationType operationType, String commandFacePosition) {
        switch (operationType) {
            case POSITION:
                return FACE_DOWN;
            case TURNAROUND:
                switch (commandFacePosition) {
                    case FACE_LEFT:
                        return FACE_RIGHT;
                    case FACE_RIGHT:
                        return FACE_LEFT;
                    case FACE_UP:
                        return FACE_DOWN;
                    case FACE_DOWN:
                        return FACE_UP;
                }
                break;
            case RIGHT:
                switch (commandFacePosition) {
                    case FACE_LEFT:
                        return FACE_UP;
                    case FACE_RIGHT:
                        return FACE_DOWN;
                    case FACE_UP:
                        return FACE_RIGHT;
                    case FACE_DOWN:
                        return FACE_LEFT;
                }
                break;
        }
        return commandFacePosition;
    }
}
