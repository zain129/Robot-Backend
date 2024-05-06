package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.exception.NoCommandFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class RobotCommandServiceImpl implements RobotCommandService {

    private final CommandParserService commandParserService;

    @Override
    public CommandResponseDTO processCommandRequest(CommandRequestDTO commandRequestDTO) {
        if (Objects.isNull(commandRequestDTO) || StringUtils.isBlank(commandRequestDTO.getStringCommand())) {
            log.info("No commands found to be executed.");
            throw new NoCommandFoundException();
        }

        return commandParserService.parseCommand(commandRequestDTO);
    }

}
