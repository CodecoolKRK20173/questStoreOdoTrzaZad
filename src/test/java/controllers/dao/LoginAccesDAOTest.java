package controllers.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void readLoginDataReturnsEmptyListIfInputsAreEmpty(){
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);

        List<Integer> emptyMailResult = loginAccesDAO.readLoginData("", "abc");
        List<Integer> emptyPasswordResult = loginAccesDAO.readLoginData("abc", "");

        assertTrue(emptyMailResult.isEmpty());
        assertTrue(emptyPasswordResult.isEmpty());
    }

    @Test
    public void readLoginDataReturnsEmptyListWhenUserDoesntExists() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);

        List<Integer> invalidUserResult = loginAccesDAO.readLoginData("xxx", "xxx");

        assertTrue(invalidUserResult.isEmpty());
    }
}