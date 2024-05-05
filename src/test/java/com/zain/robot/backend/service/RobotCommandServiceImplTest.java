package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.exception.NoCommandFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RobotCommandServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RobotCommandServiceImplTest {
    @Autowired
    private RobotCommandServiceImpl robotCommandServiceImpl;

    @Test
    void testProcessCommandRequest() {
        // Given, When and Then
        assertThrows(NoCommandFoundException.class,
                () -> robotCommandServiceImpl.processCommandRequest(new CommandRequestDTO()));
        assertNull(robotCommandServiceImpl.processCommandRequest(
                new CommandRequestDTO("No commands found to be executed.", 1L, 1L, "No commands found to be executed.")));
        assertThrows(NoCommandFoundException.class, () -> robotCommandServiceImpl.processCommandRequest(null));
    }

    @Test
    void testProcessCommandRequest2() {
        // Given
        CommandRequestDTO commandRequestDTO = CommandRequestDTO.builder()
                .currentColPosition(1L)
                .currentRowPosition(1L)
                .facePosition("Face Position")
                .stringCommand("String Command")
                .build();

        // When and Then
        assertNull(robotCommandServiceImpl.processCommandRequest(commandRequestDTO));
    }

    @Test
    void testProcessCommandRequest3() {
        // Given
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenReturn("String Command");

        // When
        CommandResponseDTO actualProcessCommandRequestResult = robotCommandServiceImpl
                .processCommandRequest(commandRequestDTO);

        // Then
        verify(commandRequestDTO, atLeast(1)).getStringCommand();
        assertNull(actualProcessCommandRequestResult);
    }

    @Test
    void testProcessCommandRequest4() {
        // Given
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenReturn(" ");

        // When and Then
        assertThrows(NoCommandFoundException.class, () -> robotCommandServiceImpl.processCommandRequest(commandRequestDTO));
        verify(commandRequestDTO).getStringCommand();
    }

    @Test
    void testProcessCommandRequest5() {
        // Given
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenReturn("WAIT");

        // When
        CommandResponseDTO actualProcessCommandRequestResult = robotCommandServiceImpl
                .processCommandRequest(commandRequestDTO);

        // Then
        verify(commandRequestDTO, atLeast(1)).getStringCommand();
        assertNotNull(actualProcessCommandRequestResult.getNewColPosition());
        assertNotNull(actualProcessCommandRequestResult.getNewRowPosition());
        assertNull(actualProcessCommandRequestResult.getOtherInfo());
        assertEquals(0, actualProcessCommandRequestResult.getMovingSteps());
        assertEquals(OperationType.WAIT, actualProcessCommandRequestResult.getOperationType());
    }

    @Test
    void testProcessCommandRequest6() {
        // Given
        CommandRequestDTO commandRequestDTO = mock(CommandRequestDTO.class);
        when(commandRequestDTO.getStringCommand()).thenThrow(new NoCommandFoundException());

        // When and Then
        assertThrows(NoCommandFoundException.class, () -> robotCommandServiceImpl.processCommandRequest(commandRequestDTO));
        verify(commandRequestDTO).getStringCommand();
    }
}
