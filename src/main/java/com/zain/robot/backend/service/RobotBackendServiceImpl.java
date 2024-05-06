package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.dto.CommandRequestDTO;
import com.zain.robot.backend.domain.dto.CommandResponseDTO;
import com.zain.robot.backend.domain.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zain.robot.backend.util.RobotBackendConstant.*;

@Service
@Slf4j
@AllArgsConstructor
public class RobotBackendServiceImpl implements RobotBackendService {

    private final RobotCommandService robotCommandService;

    public CommandResponseDTO executeCommand(CommandRequestDTO commandRequestDTO) {
        CommandResponseDTO commandResponseDTO = robotCommandService.processCommandRequest(commandRequestDTO);
        log.info("Command Response after processing Request {}", commandResponseDTO);
        return commandResponseDTO;
    }

    @Override
    public List<CommandResponseDTO> executeAllCommands(List<CommandRequestDTO> commandRequestDTOList) {
        List<CommandResponseDTO> commandResponseDTOList = new ArrayList<>();
        for (int i = 0; i < commandRequestDTOList.size(); i++) {
            CommandRequestDTO commandRequestDTO = commandRequestDTOList.get(i);
            CommandResponseDTO commandResponseDTO = executeCommand(commandRequestDTO);
            if (i + 1 < commandRequestDTOList.size()) {
                commandRequestDTOList.get(i + 1).setFacePosition(commandResponseDTO.getFacePosition());
                commandRequestDTOList.get(i + 1).setCurrentRowPosition(commandResponseDTO.getNewRowPosition());
                commandRequestDTOList.get(i + 1).setCurrentColPosition(commandResponseDTO.getNewColPosition());
            }
            commandResponseDTOList.add(commandResponseDTO);
        }
        return commandResponseDTOList;
    }


}
