package ru.pylaev.toDoProject.businessLogicLayer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    State state = new State();

    @Test
    void checkOwnerIsOk() {
        state.setCorrectOwner(("user"));
        assertEquals("user", state.getOwner());
    }

    @Test
    void checkOwnerIsRejected() {
        state.setCorrectOwner(":");
        assertNull(state.getOwner());
    }
}