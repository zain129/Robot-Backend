package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;

import java.util.List;

public interface RobotBackendService {
    CommandResponseDTO executeCommand(CommandRequestDTO commandRequestDTO);
    List<CommandResponseDTO> executeAllCommands(List<CommandRequestDTO> commandRequestDTO);
}
