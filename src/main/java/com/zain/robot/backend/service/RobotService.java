package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.exception.NoCommandFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zain.robot.backend.domain.enums.OperationType.*;

@Service
@Slf4j
public class RobotService {

    public List<CommandResponseDTO> executeCommands(CommandRequestDTO commandRequestDTO) {
        if (Objects.isNull(commandRequestDTO) || CollectionUtils.isEmpty(commandRequestDTO.getListOfCommands())) {
            log.info("No commands found to be executed.");
            throw new NoCommandFoundException();
        }

        List<String> commands = commandRequestDTO.getListOfCommands();
        List<CommandResponseDTO> commandResponseDTOList = createCommandRspDtoList(commands);
        log.info("Mapped list of commands {}", commandResponseDTOList);
        return commandResponseDTOList;
    }

    private List<CommandResponseDTO> createCommandRspDtoList(List<String> commands) {
        List<CommandResponseDTO> commandResponseDTOList = new ArrayList<>();
        commands.stream()
                .map(command -> command.split(" "))
                .forEach(subCommands -> {
                    if (isValidCommand(subCommands[0])) {
                        CommandResponseDTO commandResponseDTO = CommandResponseDTO.builder()
                                .operationType(OperationType.findByValue(subCommands[0]))
                                .build();

                        if (isForwardOrReverseCommand(subCommands[0])) {
                            commandResponseDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
                        } else if (isChangePositionCommand(subCommands[0])) {
                            commandResponseDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
                            commandResponseDTO.setOtherInfo(Integer.parseInt(subCommands[2]) + " " + subCommands[3]);
                        }

                        commandResponseDTOList.add(commandResponseDTO);
                    }
                });

        return commandResponseDTOList;
    }

    private boolean isValidCommand(String subCommand) {
        return Objects.nonNull(OperationType.findByValue(subCommand));
    }

    private boolean isForwardOrReverseCommand(String subCommand) {
        return Objects.equals(subCommand, FORWARD.getValue()) || Objects.equals(subCommand, REVERSE.getValue());
    }

    private boolean isChangePositionCommand(String subCommand) {
        return Objects.equals(subCommand, POSITION.getValue());
    }
}
