package com.zain.robot.backend.domain.dto;

import com.zain.robot.backend.domain.enums.OperationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CommandRspDTO {

    private OperationType operationType;
    private int movingSteps;
    private String otherInfo;
}
