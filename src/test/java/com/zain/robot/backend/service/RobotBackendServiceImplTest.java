package com.zain.robot.backend.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.exception.NoCommandFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RobotBackendServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RobotBackendServiceImplTest {
    @Autowired
    private RobotBackendServiceImpl robotBackendServiceImpl;

    @Test
    void testExecuteCommand_EmptyCommand() {
        // Given, When and Then
        assertThrows(NoCommandFoundException.class, () -> robotBackendServiceImpl.executeCommands(new CommandRequestDTO()));
        assertNull(robotBackendServiceImpl.executeCommands(
                new CommandRequestDTO("No commands found to be executed.", 1L, 1L, "No commands found to be executed.")));
        assertThrows(NoCommandFoundException.class, () -> robotBackendServiceImpl.executeCommands(null));
    }

    @Test
    void testExecuteCommand() {
        // Given
        CommandRequestDTO commandRequestDTO = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();

        // Then
        assertNull(robotBackendServiceImpl.executeCommands(commandRequestDTO));
    }

    @Test
    void testExecuteCommands_2() {
        // Given
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenReturn("String Command");

        // When
        CommandResponseDTO actualExecuteCommandsResult = robotBackendServiceImpl.executeCommands(commandRequestDTO);

        // Then
        verify(commandRequestDTO, atLeast(1)).getStringCommand();
        assertNull(actualExecuteCommandsResult);
    }

    @Test
    void testExecuteCommands_NullCommand() {
        // Given
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenThrow(new NoCommandFoundException());

        // When and Then
        assertThrows(NoCommandFoundException.class, () -> robotBackendServiceImpl.executeCommands(commandRequestDTO));
        verify(commandRequestDTO).getStringCommand();
    }
}
