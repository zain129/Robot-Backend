package com.zain.robot.backend.controller;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.service.RobotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @PostMapping("/execute-commands")
    public ResponseEntity<List<CommandResponseDTO>> executeCommands(@RequestBody CommandRequestDTO commandRequestDTO) {
        log.info("Entering executeCommands controller method to execute command {}", commandRequestDTO);
        List<CommandResponseDTO> commandResponseList = robotService.executeCommands(commandRequestDTO);

        if (CollectionUtils.isEmpty(commandResponseList)) return ResponseEntity.badRequest().body(Collections.emptyList());
        return ResponseEntity.ok(commandResponseList);
    }
}
