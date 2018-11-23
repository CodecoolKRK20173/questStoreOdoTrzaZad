package controllers.dao;

import models.CodecoolerModel;
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
    public void readCoinsThrowsNullPointerExceptionWhenWrongQuery() throws SQLException {

        when(statement.executeQuery(anyString())).thenThrow(new SQLException());
        assertThrows(NullPointerException.class, () -> (new CodecoolerDAO(connection)).readCoins(-1));
    }

    @Test
    public void redCoinsReturns0WhenNoCodecoolerFound() throws SQLException {
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        assertEquals(0, (new CodecoolerDAO(connection)).readCoins(1));
    }

    @Test
    public void getCodecoolerModelReturnsValidModel() throws SQLException{
        int codecoolerId = 1;
        String sql = String.format("SELECT codecoolers.*, teams.id, login_access.email, login_access.password FROM codecoolers INNER JOIN" +
                " teams ON codecoolers.codecooler_id = teams.codecooler_id INNER JOIN login_access ON codecoolers.codecooler_id = login_access.id WHERE" +
                " codecoolers.codecooler_id = %d;", codecoolerId);
        String password = "wTibie";
        String email = "trzaskamWTibie@cc.com";
        String nickName = "Cyklon";
        String first_name = "Karol";
        String second_name = "Trzaska";
        int teamID = 1234;
        int questInProgress = 1;
        int coolCoinsEverEarned = 999;
        int room = 3;
        int expLevel = 10;
        int coolcoins = 99;
        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("coolcoins")).thenReturn(coolcoins);
        when(resultSet.getInt("exp_level")).thenReturn(expLevel);
        when(resultSet.getInt("actual_room")).thenReturn(room);
        when(resultSet.getInt("coolcoins_ever_earned")).thenReturn(coolCoinsEverEarned);
        when(resultSet.getInt("quest_in_progress")).thenReturn(questInProgress);
        when(resultSet.getString("first_name")).thenReturn(first_name);
        when(resultSet.getString("last_name")).thenReturn(second_name);
        when(resultSet.getString("nickname")).thenReturn(nickName);
        when(resultSet.getInt("id")).thenReturn(teamID);
        when(resultSet.getString("email")).thenReturn(email);
        when(resultSet.getString("password")).thenReturn(password);

        CodecoolerModel model = (new CodecoolerDAO(connection)).getCodecoolerModel(codecoolerId);

        assertEquals(password, model.getPassword());
        assertEquals(email, model.getEmail());
        assertEquals(nickName, model.getNickname());
        assertEquals(first_name, model.getFirstName());
        assertEquals(second_name, model.getLastName());
        assertEquals(teamID, model.getTeamID());
        assertEquals(questInProgress, model.getQuestInProgress());
        assertEquals(coolCoinsEverEarned, model.getCoolcoinsEverEarned());
        assertEquals(room, model.getRoom());
        assertEquals(expLevel, model.getExpLevel());
        assertEquals(coolcoins, model.getCoolcoins());
    }

    @Test
    public void getCodecoolerModelReturnsNullWhenWrongCodecoolerId() throws SQLException{
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        assertEquals(null, (new CodecoolerDAO(connection)).getCodecoolerModel(1));
    }
}
