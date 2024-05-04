package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRspDTO;
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

    public List<CommandRspDTO> executeCommands(List<String> commands) {
        if (CollectionUtils.isEmpty(commands)) {
            log.info("No commands found to be executed.");
            throw new NoCommandFoundException();
        }

        List<CommandRspDTO> commandRspDTOList = mapCommands(commands);
        log.info("Mapped list of commands {}", commandRspDTOList);
        return commandRspDTOList;
    }

    private List<CommandRspDTO> mapCommands(List<String> commands) {
        List<CommandRspDTO> commandRspDTOList = new ArrayList<>();
        commands.stream()
                .map(command -> command.split(" "))
                .forEach(subCommands -> {
                    CommandRspDTO commandRspDTO = CommandRspDTO.builder()
                            .operationType(OperationType.findByValue(subCommands[0]))
                            .build();
                    if (Objects.equals(subCommands[0], FORWARD.getValue())
                            || Objects.equals(subCommands[0], REVERSE.getValue())) {
                        commandRspDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
                    } else if (Objects.equals(subCommands[0], POSITION.getValue())) {
                        commandRspDTO.setMovingSteps(Integer.parseInt(subCommands[1]));
                        commandRspDTO.setOtherInfo(Integer.parseInt(subCommands[2]) + " " + subCommands[3]);
                    }
                    commandRspDTOList.add(commandRspDTO);
                });

        return commandRspDTOList;
    }
}
