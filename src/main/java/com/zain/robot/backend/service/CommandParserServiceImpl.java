package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.zain.robot.backend.domain.enums.OperationType.FORWARD;
import static com.zain.robot.backend.domain.enums.OperationType.POSITION;
import static com.zain.robot.backend.util.RobotBackendConstant.FACE_DOWN;

@Service
@Slf4j
@AllArgsConstructor
public class CommandParserServiceImpl implements CommandParserService {

    private final PositionCalculatorService positionCalculatorService;

    @Override
    public CommandResponseDTO parseCommand(CommandRequestDTO commandRequestDTO) {
        String[] subCommands = commandRequestDTO.getStringCommand().split(" ");
        if (!isValidCommand(subCommands[0])) {
            log.error("Invalid command received: {}", commandRequestDTO.getStringCommand());
            return null;
        }
        return createCommandResponseDTO(commandRequestDTO, subCommands);
    }

    private CommandResponseDTO createCommandResponseDTO(CommandRequestDTO commandRequestDTO, String[] subCommands) {
        if (Objects.isNull(commandRequestDTO.getCurrentRowPosition())
                && Objects.isNull(commandRequestDTO.getCurrentColPosition())
                && StringUtils.isBlank(commandRequestDTO.getFacePosition())) {
            commandRequestDTO.setFacePosition(FACE_DOWN);
            commandRequestDTO.setCurrentRowPosition(1L);
            commandRequestDTO.setCurrentColPosition(1L);
        }

        CommandResponseDTO commandResponseDTO = CommandResponseDTO.builder()
                .operationType(OperationType.findByValue(subCommands[0]))
                .newColPosition(commandRequestDTO.getCurrentColPosition())
                .newRowPosition(commandRequestDTO.getCurrentRowPosition())
                .build();

        if (isPositionCommand(subCommands[0])) {
            commandResponseDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
            commandResponseDTO.setNewRowPosition(1L);
            commandResponseDTO.setNewColPosition(1L);

        } else if (isForwardCommand(subCommands[0])) {
            int steps = Integer.parseInt(subCommands[1]);
            commandResponseDTO.setMovingSteps(steps);
            commandResponseDTO.setNewRowPosition(
                    positionCalculatorService.deduceRowPosition(commandRequestDTO.getCurrentRowPosition(), steps, commandRequestDTO.getFacePosition()));
            commandResponseDTO.setNewColPosition(
                    positionCalculatorService.deduceColPosition(commandRequestDTO.getCurrentColPosition(), steps, commandRequestDTO.getFacePosition()));

        } else if (isChangePositionCommand(subCommands[0])) {
            commandResponseDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
            commandResponseDTO.setOtherInfo(Integer.parseInt(subCommands[2]) + " " + subCommands[3]);
        }

        commandResponseDTO.setFacePosition(
                positionCalculatorService.deduceFacePosition(commandResponseDTO.getOperationType(), commandRequestDTO.getFacePosition()));

        return commandResponseDTO;
    }

    private boolean isValidCommand(String subCommand) {
        return Objects.nonNull(OperationType.findByValue(subCommand));
    }

    private boolean isPositionCommand(String subCommand) {
        return Objects.equals(subCommand, POSITION.getValue());
    }

    private boolean isForwardCommand(String subCommand) {
        return Objects.equals(subCommand, FORWARD.getValue());
    }

    private boolean isChangePositionCommand(String subCommand) {
        return Objects.equals(subCommand, POSITION.getValue());
    }
}
