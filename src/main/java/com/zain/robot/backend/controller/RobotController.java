package com.zain.robot.backend.controller;

import com.zain.robot.backend.domain.dto.CommandRspDTO;
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
    public ResponseEntity<List<CommandRspDTO>> executeCommands(@RequestBody List<String> commands) {
        log.info("Entering executeCommands controller method to execute command {}", commands);
        List<CommandRspDTO> commandResponseList = robotService.executeCommands(commands);

        if (CollectionUtils.isEmpty(commandResponseList)) return ResponseEntity.badRequest().body(Collections.emptyList());
        return ResponseEntity.ok(commandResponseList);
    }
}
