package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.exception.NoCommandFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RobotCommandServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RobotCommandServiceImplTest {
    @MockBean
    private CommandParserService commandParserService;

    @Autowired
    private RobotCommandServiceImpl robotCommandServiceImpl;

    @Test
    void testProcessCommandRequest() {
        // Arrange, Act and Assert
        assertThrows(NoCommandFoundException.class,
                () -> robotCommandServiceImpl.processCommandRequest(new CommandRequestDTO()));
        assertThrows(NoCommandFoundException.class, () -> robotCommandServiceImpl.processCommandRequest(null));
    }

    @Test
    void testProcessCommandRequest2() {
        // Arrange
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .facePosition("Face Position")
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        when(commandParserService.parseCommand(Mockito.any())).thenReturn(buildResult);
        CommandRequestDTO commandRequestDTO = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();

        // Act
        robotCommandServiceImpl.processCommandRequest(commandRequestDTO);

        // Assert
        verify(commandParserService).parseCommand(isA(CommandRequestDTO.class));
    }

    @Test
    void testProcessCommandRequest3() {
        // Arrange
        when(commandParserService.parseCommand(Mockito.any())).thenThrow(new NoCommandFoundException());
        CommandRequestDTO commandRequestDTO = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();

        // Act and Assert
        assertThrows(NoCommandFoundException.class, () -> robotCommandServiceImpl.processCommandRequest(commandRequestDTO));
        verify(commandParserService).parseCommand(isA(CommandRequestDTO.class));
    }

    /**
     * Method under test:
     * {@link RobotCommandServiceImpl#processCommandRequest(CommandRequestDTO)}
     */
    @Test
    void testProcessCommandRequest4() {
        // Arrange
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenThrow(new NoCommandFoundException());

        // Act and Assert
        assertThrows(NoCommandFoundException.class, () -> robotCommandServiceImpl.processCommandRequest(commandRequestDTO));
        verify(commandRequestDTO).getStringCommand();
    }
}
