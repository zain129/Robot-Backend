package com.zain.robot.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.service.RobotBackendService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RobotBackendControllerImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RobotBackendControllerImplTest {
    @Autowired
    private RobotBackendControllerImpl robotBackendControllerImpl;

    @MockBean
    private RobotBackendService robotBackendService;

    @Test
    void testExecuteCommands() throws Exception {
        // Given
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        when(robotBackendService.executeCommands(Mockito.<CommandRequestDTO>any())).thenReturn(buildResult);

        CommandRequestDTO commandRequestDTO = new CommandRequestDTO();
        commandRequestDTO.setCurrentColPosition(1L);
        commandRequestDTO.setCurrentRowPosition(1L);
        commandRequestDTO.setFacePosition("Face Position");
        commandRequestDTO.setStringCommand("String Command");
        String content = (new ObjectMapper()).writeValueAsString(commandRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/robot-backend/execute-commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Then
        MockMvcBuilders.standaloneSetup(robotBackendControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"operationType\":\"POSITION\",\"movingSteps\":1,\"otherInfo\":\"Other Info\",\"newRowPosition\":1,\"newColPosition"
                                        + "\":1}"));
    }

    @Test
    void testExecuteCommands_EmptyResponse() {
        // Given
        when(robotBackendService.executeCommands(CommandRequestDTO.builder().stringCommand("INVALID_COMMAND").build()))
                .thenReturn(null);

        // Action
        ResponseEntity<CommandResponseDTO> responseEntity =
                robotBackendControllerImpl.executeCommands(CommandRequestDTO.builder().stringCommand("INVALID_COMMAND").build());

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
