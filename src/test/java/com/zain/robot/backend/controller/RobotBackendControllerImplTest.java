package com.zain.robot.backend.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RobotBackendControllerImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RobotBackendControllerImplTest {
    @Autowired
    private RobotBackendControllerImpl robotBackendControllerImpl;

    @MockBean
    private RobotBackendService robotBackendService;

    @Test
    void testExecuteAllCommands() throws Exception {
        // Arrange
        when(robotBackendService.executeAllCommands(Mockito.any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/robot-backend/execute-all-commands")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(robotBackendControllerImpl)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testExecuteAllCommands2() throws Exception {
        // Arrange
        ArrayList<CommandResponseDTO> commandResponseDTOList = new ArrayList<>();
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .facePosition("Face Position")
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        commandResponseDTOList.add(buildResult);
        when(robotBackendService.executeAllCommands(Mockito.<List<CommandRequestDTO>>any()))
                .thenReturn(commandResponseDTOList);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/robot-backend/execute-all-commands")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(robotBackendControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"operationType\":\"POSITION\",\"movingSteps\":1,\"otherInfo\":\"Other Info\",\"newRowPosition\":1,\"newColPosition"
                                        + "\":1,\"facePosition\":\"Face Position\"}]"));
    }

    @Test
    void testExecuteCommands() throws Exception {
        // Arrange
        CommandResponseDTO buildResult = CommandResponseDTO.builder()
                .facePosition("Face Position")
                .movingSteps(1)
                .newColPosition(1L)
                .newRowPosition(1L)
                .operationType(OperationType.POSITION)
                .otherInfo("Other Info")
                .build();
        when(robotBackendService.executeCommand(Mockito.<CommandRequestDTO>any())).thenReturn(buildResult);

        CommandRequestDTO commandRequestDTO = new CommandRequestDTO();
        commandRequestDTO.setCurrentColPosition(1L);
        commandRequestDTO.setCurrentRowPosition(1L);
        commandRequestDTO.setFacePosition("Face Position");
        commandRequestDTO.setStringCommand("String Command");
        String content = (new ObjectMapper()).writeValueAsString(commandRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/robot-backend/execute-commands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(robotBackendControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"operationType\":\"POSITION\",\"movingSteps\":1,\"otherInfo\":\"Other Info\",\"newRowPosition\":1,\"newColPosition"
                                        + "\":1,\"facePosition\":\"Face Position\"}"));
    }
}
