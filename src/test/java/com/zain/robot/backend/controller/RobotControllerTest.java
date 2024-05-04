package com.zain.robot.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import com.zain.robot.backend.service.RobotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RobotController.class})
@ExtendWith(SpringExtension.class)
class RobotControllerTest {
    @Autowired
    private RobotController robotController;

    @MockBean
    private RobotService robotService;

    @Test
    void testExecuteCommands() throws Exception {
        // Given
        when(robotService.executeCommands(Mockito.any())).thenReturn(new ArrayList<>());

        CommandRequestDTO commandRequestDTO = CommandRequestDTO.builder()
                .listOfCommands(new ArrayList<>())
                .build();
        String content = (new ObjectMapper()).writeValueAsString(commandRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/execute-commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Action
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(robotController)
                .build()
                .perform(requestBuilder);

        // Then
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testExecuteCommands2() throws Exception {
        // Given
        ArrayList<CommandResponseDTO> commandResponseDTOList = new ArrayList<>();
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .movingSteps(1)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        commandResponseDTOList.add(buildResult);
        when(robotService.executeCommands(Mockito.any())).thenReturn(commandResponseDTOList);

        CommandRequestDTO commandRequestDTO = new CommandRequestDTO();
        commandRequestDTO.setListOfCommands(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(commandRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/execute-commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Action and Then
        MockMvcBuilders.standaloneSetup(robotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"operationType\":\"POSITION\",\"movingSteps\":1,\"otherInfo\":\"Other Info\"}]"));
    }

    @Test
    void testExecuteCommands_EmptyResponse() {
        // Given
        List<String> commands = List.of("INVALID_COMMAND");
        when(robotService.executeCommands(CommandRequestDTO.builder().listOfCommands(commands).build()))
                .thenReturn(Collections.emptyList());

        // Action
        ResponseEntity<List<CommandResponseDTO>> responseEntity =
                robotController.executeCommands(CommandRequestDTO.builder().listOfCommands(commands).build());

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }
}
