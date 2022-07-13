package ru.pylaev.toDoProject.businessLogicLayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UiStateTest {
    UiState uiState = new UiState();

    @Test
    void checkOwnerIsOk() {
        uiState.setCorrectOwner(("user"));
        assertEquals("user", uiState.getOwner());
    }

    @Test
    void checkOwnerIsRejected() {
        uiState.setCorrectOwner(":");
        assertNull(uiState.getOwner());
    }
}