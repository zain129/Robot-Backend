package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.exception.NoCommandFoundException;
import com.zain.robot.backend.util.RobotBackendConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.zain.robot.backend.domain.enums.OperationType.FORWARD;
import static com.zain.robot.backend.domain.enums.OperationType.POSITION;

@Service
@Slf4j
public class RobotBackendServiceImpl implements RobotBackendService {

    public CommandResponseDTO executeCommands(CommandRequestDTO commandRequestDTO) {
        if (Objects.isNull(commandRequestDTO) || StringUtils.isBlank(commandRequestDTO.getStringCommand())) {
            log.info("No commands found to be executed.");
            throw new NoCommandFoundException();
        }

        CommandResponseDTO commandResponseDTO = createCommandResponseDTO(commandRequestDTO);
        log.info("Command Response after processing Request {}", commandResponseDTO);
        return commandResponseDTO;
    }

    private CommandResponseDTO createCommandResponseDTO(CommandRequestDTO commandRequestDTO) {
        CommandResponseDTO commandResponseDTO = null;
        String[] subCommands = commandRequestDTO.getStringCommand().split("");

        if (isValidCommand(subCommands[0])) {
            commandResponseDTO = CommandResponseDTO.builder()
                    .operationType(OperationType.findByValue(subCommands[0]))
                    .build();
            if (isForwardCommand(subCommands[0])) {
                int steps = Integer.parseInt(subCommands[1]);
                commandResponseDTO.setMovingSteps(steps);
                commandResponseDTO.setNewRowPosition(
                        deduceRowPosition(commandRequestDTO.getCurrentRowPosition(), steps, commandRequestDTO.getFacePosition()));
                commandResponseDTO.setNewColPosition(
                        deduceColPosition(commandRequestDTO.getCurrentColPosition(), steps, commandRequestDTO.getFacePosition()));

            } else if (isChangePositionCommand(subCommands[0])) {
                commandResponseDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
                commandResponseDTO.setOtherInfo(Integer.parseInt(subCommands[2]) + " " + subCommands[3]);
            }
        }

        return commandResponseDTO;
    }

    private Long deduceRowPosition(Long currentPosition, int steps, String facePosition) {
        long newPosition = currentPosition;
        if (Objects.equals(facePosition, RobotBackendConstant.FACE_RIGHT)) {
            newPosition += steps;
            if (newPosition > 5) {
                newPosition = newPosition % 5;
            }
        }
        return newPosition;
    }

    private Long deduceColPosition(Long currentPosition, int steps, String facePosition) {
        long newPosition = currentPosition;
        if (Objects.equals(facePosition, RobotBackendConstant.FACE_UP)) {
            newPosition -= steps;
            if (newPosition < 1) {
                newPosition = Math.abs(newPosition);
            }
            if (newPosition > 5) {
                newPosition = newPosition % 5;
            }
        } else if (Objects.equals(facePosition, RobotBackendConstant.FACE_DOWN)) {
            newPosition += steps;
            if (newPosition > 5) {
                newPosition = newPosition % 5;
            }
        }
        return newPosition;
    }

    private boolean isValidCommand(String subCommand) {
        return Objects.nonNull(OperationType.findByValue(subCommand));
    }

    private boolean isForwardCommand(String subCommand) {
        return Objects.equals(subCommand, FORWARD.getValue());
    }

    private boolean isChangePositionCommand(String subCommand) {
        return Objects.equals(subCommand, POSITION.getValue());
    }
}
