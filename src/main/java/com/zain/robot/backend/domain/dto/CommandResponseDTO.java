package com.zain.robot.backend.domain.dto;

import com.zain.robot.backend.domain.enums.OperationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CommandResponseDTO {
    private OperationType operationType;
    private int movingSteps;
    private String otherInfo;
    private Long newRowPosition;
    private Long newColPosition;
    private String facePosition;
}
