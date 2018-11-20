package controllers.dao;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Test
    public void readLoginDataReturnsCorrectData() throws SQLException{
        ResultSet resultSet = mock(ResultSet.class);
        Statement statement = mock(Statement.class);
        Connection connection = mock(Connection.class);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("access_level")).thenReturn(1);
        when(resultSet.getInt("id")).thenReturn(1);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        LoginAccesDAO loginAccesDAO = new LoginAccesDAO(connection);

        List expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);

        assertEquals(expected, loginAccesDAO.readLoginData("", ""));
    }
}