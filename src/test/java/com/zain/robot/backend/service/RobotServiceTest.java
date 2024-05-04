package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.exception.NoCommandFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RobotServiceTest {

    @InjectMocks
    private RobotService robotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteCommands_NullCommands() {
        // Act
        assertThrows(NoCommandFoundException.class, () -> robotService.executeCommands(null));
    }

    @Test
    void testExecuteCommands_EmptyCommands() {
        // Act
        assertThrows(NoCommandFoundException.class, () -> robotService.executeCommands(
                CommandRequestDTO.builder().listOfCommands(Collections.emptyList()).build()));
    }

    @Test
    void testExecuteCommands_InvalidCommands() {
        // Given
        List<String> commands = Collections.singletonList("INVALID_COMMAND");

        // Action
        List<CommandResponseDTO> result = robotService.executeCommands(CommandRequestDTO.builder().listOfCommands(commands).build());

        // Then
        assertTrue(CollectionUtils.isEmpty(result));
    }

    @Test
    void testExecuteCommands_ValidCommands() {
        // Given
        List<String> commands = List.of("WAIT", "FORWARD 3");

        // Action
        List<CommandResponseDTO> result = robotService.executeCommands(CommandRequestDTO.builder().listOfCommands(commands).build());

        // Then
        assertFalse(result.isEmpty());
        assertEquals(OperationType.WAIT, result.get(0).getOperationType());
        assertEquals(OperationType.FORWARD, result.get(1).getOperationType());
        assertEquals(3, result.get(1).getMovingSteps());
        assertNull(result.get(0).getOtherInfo());
    }
}