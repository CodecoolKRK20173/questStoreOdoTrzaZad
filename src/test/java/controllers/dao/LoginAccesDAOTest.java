package controllers.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginAccesDAOTest {

    ResultSet resultSet;
    Statement statement;
    Connection connection;

    @BeforeEach
    public void initialize() throws SQLException {
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);
        connection = mock(Connection.class);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    public void readLoginDataReturnsCorrectData() throws SQLException {
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("access_level")).thenReturn(1);
        when(resultSet.getInt("id")).thenReturn(1);
        List expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);

        List<Integer> actual = loginAccesDAO.readLoginData("abc", "abc");

        assertEquals(expected, actual);
    }

    @Test
    public void readLoginDataThrowsExceptionIfInputsAreEmpty(){
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);

        Executable emptyEmail = () -> {
            loginAccesDAO.readLoginData("", "abc");
        };
        Executable emptyPassword = () -> {
            loginAccesDAO.readLoginData("abc", "");
        };

        assertThrows(Exception.class, emptyEmail);
        assertThrows(Exception.class, emptyPassword);
    }

    @Test
    public void readLoginDataThrowsExceptionWhenUserDoesntExists() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);

        Executable invalidInput = () -> {
            loginAccesDAO.readLoginData("xxx", "xxx");
        };

        assertThrows(Exception.class, invalidInput);
    }
}