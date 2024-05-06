package com.zain.robot.backend.service;

import com.zain.robot.backend.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {PositionCalculatorServiceImpl.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.yml")
class PositionCalculatorServiceImplTest {
    @Autowired
    private PositionCalculatorServiceImpl positionCalculatorServiceImpl;


    @Test
    void testDeduceColPosition() {
        // Arrange and Act
        assertEquals(2L,
                (new PositionCalculatorServiceImpl(5)).deduceColPosition(1L, 1, "RIGHT"));
        assertEquals(1L,
                (new PositionCalculatorServiceImpl(5)).deduceColPosition(5L, 1, "RIGHT"));
    }

    @Test
    void testDeduceRowPosition() {
        // Arrange and Act
        assertEquals(2L,
                (new PositionCalculatorServiceImpl(5)).deduceRowPosition(1L, 1, "DOWN"));
        assertEquals(1L,
                (new PositionCalculatorServiceImpl(5)).deduceRowPosition(5L, 1, "DOWN"));
    }

    @Test
    void testDeduceFacePosition() {
        // Arrange, Act and Assert
        assertEquals("DOWN",
                (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.POSITION, "Command Face Position"));
        assertEquals("RIGHT", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.TURNAROUND, "LEFT"));
        assertEquals("LEFT", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.TURNAROUND, "RIGHT"));
        assertEquals("DOWN", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.TURNAROUND, "UP"));
        assertEquals("UP", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.TURNAROUND, "DOWN"));
        assertEquals("UP", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.RIGHT, "LEFT"));
        assertEquals("DOWN", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.RIGHT, "RIGHT"));
        assertEquals("RIGHT", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.RIGHT, "UP"));
        assertEquals("LEFT", (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.RIGHT, "DOWN"));
        assertEquals("Command Face Position",
                (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.FORWARD, "Command Face Position"));
        assertEquals("Command Face Position",
                (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.TURNAROUND, "Command Face Position"));
        assertEquals("Command Face Position",
                (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.RIGHT, "Command Face Position"));
        assertEquals("Command Face Position",
                (new PositionCalculatorServiceImpl(5)).deduceFacePosition(OperationType.WAIT, "Command Face Position"));
    }
}
