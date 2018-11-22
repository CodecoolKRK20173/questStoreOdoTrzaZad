package controllers.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CodecoolerDAOTest {
    ResultSet resultSet;
    Connection connection;
    Statement statement;

    @BeforeEach
    public void initialize() throws SQLException {
        this.resultSet = mock(ResultSet.class);
        connection = mock(Connection.class);
        statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    public void readCoinsReturnsValidNumber() throws SQLException {
//        resultSet = mock(ResultSet.class);
        int codecoolerId = 1;
        int dbCoins = 99;
        String row = "coolcoins";
        String sql = String.format("SELECT %s FROM codecoolers WHERE codecooler_id = %d;", row, codecoolerId);
        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(row)).thenReturn(dbCoins);

        assertEquals(dbCoins, (new CodecoolerDAO(connection)).readCoins(codecoolerId));
    }

    @Test
    public void readCoinsThrowsNullPointerExceptionWhenCodecoolerDoesntExist() throws SQLException {
        /**I propose a change not to catch SQLExceptions so early and throw them with appropriate message, than actually
         *  catch them higher so user would know what happened :) and omit creating result sets as null :) */

        when(statement.executeQuery(anyString())).thenThrow(new SQLException());
        assertThrows(NullPointerException.class, () -> (new CodecoolerDAO(connection)).readCoins(-1));
    }

    @Test
    public void getCodecoolerModelReturnsValidModel() {
        int codecoolerId = 1;
        String codecoolerModelQuery = String.format("SELECT codecoolers.*, teams.id, login_access.email, login_access.password FROM codecoolers INNER JOIN" +
                " teams ON codecoolers.codecooler_id = teams.codecooler_id INNER JOIN login_access ON codecoolers.codecooler_id = login_access.id WHERE" +
                " codecoolers.codecooler_id = %d;", codecoolerId);
    }
}
