package com.zain.robot.backend.controller;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.service.RobotBackendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/robot-backend")
@Slf4j
@AllArgsConstructor
public class RobotBackendControllerImpl implements RobotBackendController {

    private final RobotBackendService robotBackendService;

    @Override
    @PostMapping("/execute-commands")
    public ResponseEntity<CommandResponseDTO> executeCommands(@RequestBody CommandRequestDTO commandRequestDTO) {
        log.info("Entering executeCommands controller method to execute command {}", commandRequestDTO);
        CommandResponseDTO commandResponseDTO = robotBackendService.executeCommand(commandRequestDTO);

        if (Objects.isNull(commandResponseDTO)) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(commandResponseDTO);
    }

    @Override
    @PostMapping("/execute-all-commands")
    public ResponseEntity<List<CommandResponseDTO>> executeAllCommands(@RequestBody List<CommandRequestDTO> commandRequestDTO) {
        log.info("Entering executeAllCommands controller method to execute commands {}", commandRequestDTO);
        List<CommandResponseDTO> commandResponseDTO = robotBackendService.executeAllCommands(commandRequestDTO);

        if (CollectionUtils.isEmpty(commandResponseDTO)) return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(commandResponseDTO);
    }
}
