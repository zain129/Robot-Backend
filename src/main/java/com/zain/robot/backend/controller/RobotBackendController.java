package com.zain.robot.backend.controller;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/robot-backend-default")
public interface RobotBackendController {

    ResponseEntity<CommandResponseDTO> executeCommands(@RequestBody CommandRequestDTO commandRequestDTO);

    ResponseEntity<List<CommandResponseDTO>> executeAllCommands(@RequestBody List<CommandRequestDTO> commandRequestDTO);
}
