package com.zain.robot.backend.controller;

import com.zain.robot.backend.domain.dto.CommandRspDTO;
import com.zain.robot.backend.service.RobotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RobotControllerTest {

    @Mock
    private RobotService robotService;

    @InjectMocks
    private RobotController robotController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteCommands() {
        // Arrange
        List<String> commands = Collections.singletonList("FORWARD 3");
        List<CommandRspDTO> expectedResponse = Collections.singletonList(new CommandRspDTO());
        when(robotService.executeCommands(commands)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<List<CommandRspDTO>> responseEntity = robotController.executeCommands(commands);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
        verify(robotService).executeCommands(commands);
    }

    @Test
    void testExecuteCommands_EmptyResponse() {
        // Arrange
        List<String> commands = Collections.singletonList("INVALID_COMMAND 1");
        when(robotService.executeCommands(commands)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<CommandRspDTO>> responseEntity = robotController.executeCommands(commands);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
        verify(robotService).executeCommands(commands);
    }
}