package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;

public interface RobotCommandService {
    CommandResponseDTO processCommandRequest(CommandRequestDTO commandRequestDTO);
}
