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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RobotBackendServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RobotBackendServiceImplTest {
    @Autowired
    private RobotBackendServiceImpl robotBackendServiceImpl;

    @MockBean
    private RobotCommandService robotCommandService;

    @Test
    void testExecuteCommands() {
        // Given
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();

        // When
        when(robotCommandService.processCommandRequest(Mockito.any())).thenReturn(buildResult);
        robotBackendServiceImpl.executeCommands(new CommandRequestDTO());

        // Then
        verify(robotCommandService).processCommandRequest(isA(CommandRequestDTO.class));
    }
}
