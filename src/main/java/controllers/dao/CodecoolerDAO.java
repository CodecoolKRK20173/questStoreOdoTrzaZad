package controllers.dao;

import models.CodecoolerModel;
import models.Artifact;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAO implements CodecoolerDAOInterface {
    private Connection connection;
    private Statement statement;

    CodecoolerDAO(Connection connection){
        this.connection = connection;
        getStatement();
    }

    @Override
    public int readCoins(int codecoolerId) {

        String query = "SELECT coolcoins FROM codecoolers WHERE id = " + codecoolerId + ";";
        ResultSet resultSet = getResultSet(query);
        int coins = 0;
        try{
            coins = resultSet.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coins;
    }

    public CodecoolerModel getCodecoolerModel(int codecoolerId){
/*        String codecoolerTableQuery = "SELECT * FROM Codecoolers WHERE codecooler_id = " + codecoolerId + ";";
        String teamsTableQuery = "SELECT team_name FROM Teams WHERE codecooler_id = " + codecoolerId + ";";
        String loginAccesQuery = "SELECT email, password FROM LoginAccess WHERE id = " + codecoolerId + ";";
        ResultSet resultSetCodecooler = null;
        ResultSet resultSetTeams = null;
        ResultSet resultSetLogin = null;
        resultSetCodecooler = statement.executeQuery(codecoolerTableQuery);
        resultSetTeams = statement.executeQuery(teamsTableQuery);
        resultSetLogin = statement.executeQuery(loginAccesQuery);
        */
        String codecoolerModelQuery = "SELECT Codecoolers.*, Teams.team_name, LoginAccess.email, LoginAccess.password FROM Codecoolers INNER JOIN" +
                " Teams ON Codecoolers.codecooler_id = Teams.codecooler_id INNER JOIN LoginAccess ON Codecoolers.codecooler_id = LoginAccess.id WHERE" +
                " codecoolerId = " + codecoolerId + ";";
        ResultSet resultSetModel = null;
        CodecoolerModel codecoolerModel = null;
        try{
            resultSetModel = statement.executeQuery(codecoolerModelQuery);
        }catch(SQLException e){
            System.out.println("Couldn't find selected query");
        }
        try{
            int coolcoins = resultSetModel.getInt(2);
            int expLevel = resultSetModel.getInt(3);//robocza nazwa
            int room = resultSetModel.getInt(4);
            int coolCoinsEverEarned = resultSetModel.getInt(5);
            int questInProgress = resultSetModel.getInt(6);//robocza nazwa
            String first_name = resultSetModel.getString(7);
            String second_name = resultSetModel.getString(8);
            String nickName = resultSetModel.getString(9);
            int teamID = resultSetModel.getInt(10);
            String email = resultSetModel.getString(11);
            String password = resultSetModel.getString(12);

            codecoolerModel = new CodecoolerModel(codecoolerId, first_name, second_name, email, nickName, password,
                   1, coolcoins, expLevel, room, coolCoinsEverEarned, questInProgress, teamID);  //uporządkowane, z passwordem, chcemy password w sumie tutaj trzymać czy nie? 1 = access level

        }catch(SQLException e){
            e.printStackTrace();
        }
        return codecoolerModel;
    }

    @Override
    public int checkCoinsEverOwned(int id) {
        String coinsEverOwnedQuery = "SELECT coolcoins_ever_earned FROM Codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetCoins = getResultSet(coinsEverOwnedQuery);
        int coins  = 0;
        try{
            coins = resultSetCoins.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coins;
    } //korzystanie

    @Override
    public int checkQuestInProgress(int id) {
        String questInProgressQuery = "SELECT quest_in_progress FROM Codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetQuest = getResultSet(questInProgressQuery);
        int questId = 0;
        try{
            questId = resultSetQuest.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return questId;
    } //korzystanie

    @Override
    public int readCodecoolerClass(int id) {
        String codecoolerClassQuery = "SELECT room FROM Codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetClass = getResultSet(codecoolerClassQuery);
        int classId = 0;
        try{
            classId = resultSetClass.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return classId;
    }

    @Override
    public String readTeamName(int id) {
        String teamNameQuery = "SELECT team_name FROM Teams WHERE codecooler_id = " + id + ";";
        ResultSet resultSetTeamName = getResultSet(teamNameQuery);
        String teamName = "";
        try{
            teamName = resultSetTeamName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return teamName;
    } //korzystanie

    @Override
    public String getNickName(int id) {
        String nickNameQuery = "SELECT nickname FROM codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetNickName = getResultSet(nickNameQuery);
        String nickName = "";
        try{
            nickName = resultSetNickName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nickName;
    }

    @Override
    public String getFirstName(int id) {
        String firstNameQuery = "SELECT name FROM codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetName = getResultSet(firstNameQuery);
        String firstName = "";
        try{
            firstName = resultSetName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return firstName;
    }

    @Override
    public String getSecondName(int id) {
        String secondNameQuery = "SELECT last_name FROM codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetSecondName = getResultSet(secondNameQuery);
        String secondName = "";
        try{
            secondName = resultSetSecondName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return secondName;
    }

    @Override
    public String getEmail(int id) {
        String emailQuery = "SELECT email FROM LoginAccess WHERE id = " + id + ";";
        ResultSet resultSetEmail = getResultSet(emailQuery);
        String email = "";
        try{
            email = resultSetEmail.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return email;
    } //korzystanie

    @Override
    public String readArtifacts() {
        String artifactsQuery = "SELECT * FROM Artifacts";
        ResultSet resultSetArtefacts = getResultSet(artifactsQuery);
        String artifacts = "";
        ResultSetMetaData resultSetMetaData;
        try{
            resultSetMetaData = resultSetArtefacts.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            for(int i = 1; i <= columnsNumber; i++){
                artifacts = artifacts + resultSetMetaData.getColumnName(i) + " ";
            }
            artifacts = artifacts + "\n";
            while(resultSetArtefacts.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if(i > 1) artifacts = artifacts + ", ";
                    artifacts = artifacts + resultSetArtefacts.getString(i);
                }
                artifacts = artifacts + "\n";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return artifacts;
    } //korzystanie

    @Override
    public List<Artifact> readCodecoolersArtifacts(int codecoolerId) {
        String artifactsInPossessQuery = "SELECT artifact_id FROM ArtifactsInPossess WHERE codecooler_id = " + codecoolerId + ";";
        String artifactsQuery = "SELECT * FROM artifacts ";
        List<Artifact> artifactsList;
        ResultSet resultSetArtifactsPossessed = getResultSet(artifactsInPossessQuery);

        String whereClauseIds = createWhereWithPossessedArtifacts(resultSetArtifactsPossessed);

        artifactsQuery += whereClauseIds;

        ResultSet resultSetArtifacts = getResultSet(artifactsQuery);

        artifactsList = createArtifactsList(resultSetArtifacts);


        return artifactsList;
    } //korzystnaie

    private String createWhereWithPossessedArtifacts(ResultSet resultSetArtifactsPossessed){
        ResultSetMetaData resultSetMetaData;
        String whereClauseIds = "WHERE";
        try{

            resultSetMetaData = resultSetArtifactsPossessed.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            while(resultSetArtifactsPossessed.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if(i == columnsNumber){
                        whereClauseIds += " artifact_id = " + resultSetArtifactsPossessed.getInt(i) + ";";
                    }else{
                        whereClauseIds += " artifact_id = " + resultSetArtifactsPossessed.getInt(i) + " OR";
                    }

                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return whereClauseIds;
    }

    private List<Artifact> createArtifactsList(ResultSet resultSetArtifacts){
        List<Artifact> artifactsList = new ArrayList<>();
        ResultSetMetaData resultSetArtifactsMetaData;
        try{
            resultSetArtifactsMetaData = resultSetArtifacts.getMetaData();

            int columnsNumber = resultSetArtifactsMetaData.getColumnCount();
            while(resultSetArtifacts.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    int id = resultSetArtifacts.getInt(1);
                    String name = resultSetArtifacts.getString(2);
                    String description = resultSetArtifacts.getString(3);
                    int price = resultSetArtifacts.getInt(4);
                    artifactsList.add(new Artifact(id,name ,description, price ));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return artifactsList;
    }


    @Override
    public int getPriceOfArtifact(int artifactId) {
        String artifactsTableQuery = "SELECT price FROM Artifacts WHERE artifact_id = " + artifactId + ";";
        ResultSet resultSetArtifacts = getResultSet(artifactsTableQuery);
        int price = 0;
        try{
            price = resultSetArtifacts.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return price;

    } //musthave

    @Override
    public void addNewPossesion(int codecoolerId, int artifactId) { //musthave
        String addPossesionQuery = "INSERT INTO ArtifactsInPossess (artifact_id, codecooler_id VALUES (\"" + artifactId +"\", \"" + codecoolerId +"\")";
        try{
            statement.executeQuery(addPossesionQuery);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Integer> readTeamMembersId(int artefactId) {
        return null;
    } //musthave

    @Override
    public void subtractCodecoolersCoolcoins(int codecoolerId, int artifactPrice) {
        int coins = readCoins(codecoolerId) - artifactPrice;
        String updateCoinsQuery = "UPDATE Codecoolers SET coolcoins = " + coins + ";";
        try{
            statement.executeQuery(updateCoinsQuery);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    private ResultSet getResultSet(String query){
        ResultSet resultSet = null;
        try{
            resultSet = statement.executeQuery(query);
        }catch(SQLException e){
            System.out.println("Couldn't find selected query");
        }
        return resultSet;
    }

    private void getStatement(){
        try{
            statement = connection.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}