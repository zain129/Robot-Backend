package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RobotBackendServiceImpl implements RobotBackendService {

    private final RobotCommandService robotCommandService;

    public CommandResponseDTO executeCommands(CommandRequestDTO commandRequestDTO) {
        CommandResponseDTO commandResponseDTO = robotCommandService.processCommandRequest(commandRequestDTO);
        log.info("Command Response after processing Request {}", commandResponseDTO);
        return commandResponseDTO;
    }
}
