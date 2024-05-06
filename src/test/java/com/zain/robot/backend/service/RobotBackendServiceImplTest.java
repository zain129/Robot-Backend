package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RobotBackendServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RobotBackendServiceImplTest {
    @Autowired
    private RobotBackendServiceImpl robotBackendServiceImpl;

    @MockBean
    private RobotCommandService robotCommandService;

    @Test
    void testExecuteCommand() {
        // Arrange
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .facePosition("Face Position")
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        when(robotCommandService.processCommandRequest(Mockito.any())).thenReturn(buildResult);

        // Act
        robotBackendServiceImpl.executeCommand(new CommandRequestDTO());

        // Assert
        verify(robotCommandService).processCommandRequest(isA(CommandRequestDTO.class));
    }

    @Test
    void testExecuteAllCommands() {
        // Arrange, Act and Assert
        assertTrue(robotBackendServiceImpl.executeAllCommands(new ArrayList<>()).isEmpty());
    }

    @Test
    void testExecuteAllCommands2() {
        // Arrange
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .facePosition("Face Position")
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        when(robotCommandService.processCommandRequest(Mockito.any())).thenReturn(buildResult);

        ArrayList<CommandRequestDTO> commandRequestDTOList = new ArrayList<>();
        CommandRequestDTO buildResult2 = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();
        commandRequestDTOList.add(buildResult2);

        // Act
        List<CommandResponseDTO> actualExecuteAllCommandsResult = robotBackendServiceImpl
                .executeAllCommands(commandRequestDTOList);

        // Assert
        verify(robotCommandService).processCommandRequest(isA(CommandRequestDTO.class));
        assertEquals(1, actualExecuteAllCommandsResult.size());
    }

    @Test
    void testExecuteAllCommands3() {
        // Arrange
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .facePosition("Face Position")
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        when(robotCommandService.processCommandRequest(Mockito.any())).thenReturn(buildResult);

        ArrayList<CommandRequestDTO> commandRequestDTOList = new ArrayList<>();
        CommandRequestDTO buildResult2 = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();
        commandRequestDTOList.add(buildResult2);
        CommandRequestDTO buildResult3 = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();
        commandRequestDTOList.add(buildResult3);

        // Act
        List<CommandResponseDTO> actualExecuteAllCommandsResult = robotBackendServiceImpl
                .executeAllCommands(commandRequestDTOList);

        // Assert
        verify(robotCommandService, atLeast(1)).processCommandRequest(Mockito.any());
        CommandRequestDTO getResult = commandRequestDTOList.get(1);
        assertEquals("Face Position", getResult.getFacePosition());
        assertEquals(1L, getResult.getCurrentColPosition().longValue());
        assertEquals(1L, getResult.getCurrentRowPosition().longValue());
        assertEquals(2, actualExecuteAllCommandsResult.size());
    }
}
