package controllers.dao;

import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CodecoolerDAOTest {
    ResultSet resultSet;
    Connection connection;
    Statement statement;

    @BeforeEach
    public void initializeStubs() throws SQLException {
        resultSet = mock(ResultSet.class);
        connection = mock(Connection.class);
        statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }


}
