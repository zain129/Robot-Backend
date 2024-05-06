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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CommandParserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CommandParserServiceImplTest {
    @Autowired
    private CommandParserServiceImpl commandParserServiceImpl;

    @MockBean
    private PositionCalculatorService positionCalculatorService;

    @Test
    void testParseCommand() {
        // Arrange
        CommandRequestDTO commandRequestDTO = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();

        // Act and Assert
        assertNull(commandParserServiceImpl.parseCommand(commandRequestDTO));
    }

    @Test
    void testParseCommand2() {
        // Arrange
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenReturn("String Command");

        // Act
        CommandResponseDTO actualParseCommandResult = commandParserServiceImpl.parseCommand(commandRequestDTO);

        // Assert
        verify(commandRequestDTO, atLeast(1)).getStringCommand();
        assertNull(actualParseCommandResult);
    }

    @Test
    void testParseCommand3() {
        // Arrange
        when(positionCalculatorService.deduceFacePosition(Mockito.any(), Mockito.any()))
                .thenReturn("Deduce Face Position");
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getCurrentColPosition()).thenReturn(1L);
        when(commandRequestDTO.getCurrentRowPosition()).thenReturn(1L);
        when(commandRequestDTO.getFacePosition()).thenReturn("Face Position");
        when(commandRequestDTO.getStringCommand()).thenReturn("WAIT");

        // Act
        CommandResponseDTO actualParseCommandResult = commandParserServiceImpl.parseCommand(commandRequestDTO);

        // Assert
        verify(commandRequestDTO).getCurrentColPosition();
        verify(commandRequestDTO, atLeast(1)).getCurrentRowPosition();
        verify(commandRequestDTO).getFacePosition();
        verify(commandRequestDTO).getStringCommand();
        verify(positionCalculatorService).deduceFacePosition(eq(OperationType.WAIT), eq("Face Position"));
        assertEquals("Deduce Face Position", actualParseCommandResult.getFacePosition());
        assertNull(actualParseCommandResult.getOtherInfo());
        assertEquals(0, actualParseCommandResult.getMovingSteps());
        assertEquals(1L, actualParseCommandResult.getNewColPosition().longValue());
        assertEquals(1L, actualParseCommandResult.getNewRowPosition().longValue());
        assertEquals(OperationType.WAIT, actualParseCommandResult.getOperationType());
    }
}
