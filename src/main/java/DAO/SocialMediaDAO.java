package DAO;

import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
// import Application.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocialMediaDAO {
    

    /**
     * get all messages from message table
     * @return List of Message objects. List may be empty if no messages in table.
    */ 
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                messages.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * gets a specific mmessage record from message table
     * @param id of the message to look at
     * @return Message object if message found intable. Null if not found
    */ 
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                return msg;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * gets a specific message record from message table
     * @param id of the user to look at. Represents the posted_by column in message table
     * @return List of Message objects belonging to the user. List may be empty
    */ 
    public  List<Message> getMessagesByUser(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                messages.add(msg);
            }

        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * gets a specific message record from message table
     * @param accountName of the user to look at. 
     * @return Account of the user
    */ 
    public Account getAccountByUserName(String accountName){
        Connection connection = ConnectionUtil.getConnection();

        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountName);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account sqlAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return sqlAccount;
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * gets a the account corresponding to the id given in the parameter
     * @param id of the user to look at. 
     * @return Account of the user
    */ 
    public Account getAccountByUserId(int id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE account_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account sqlAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return sqlAccount;
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * get specific message in table by column values.
     * @param message of the user to look into. 
     * @return Message that was found. Returns Null otherwise
    */ 
    public Message getMessageByValues(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ? AND message_text = ? AND time_posted_epoch = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                return msg;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //POST methods

    /**
     * add a new account to the database, so long as it passes username and password checks. And that the
     * account doesn't already exist.
     * @param accountName of the user to look at. 
     * @return Account of the user added. Null if it fails checks
    */ 
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        String username = account.getUsername();
        String password = account.getPassword();
        Account sqlAccount = getAccountByUserName(username);

        if(sqlAccount != null || username  == "" || password.length() < 4){
            System.out.println("returning null");
            return null;
        }

        try {
            String sql = "INSERT INTO account (username, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

            return getAccountByUserName(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }


    /**
     * log into an account in database by username and password.
     * @param account of the user to that's logging in. 
     * @return Account that was logged inot. Null if no such account.
    */ 
    public Account logAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            //Write SQL logic here
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account sqlAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return sqlAccount;
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * insert a new message into the message table
     * @param message to be inserted. 
     * @return Message that was inserted. Returns Null otherwise
    */ 
     public Message postMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        String msgText = message.getMessage_text();
        int posted_by = message.getPosted_by();
        Account account = getAccountByUserId(posted_by);
        if(msgText == "" || msgText.length() > 254|| account == null){
            System.out.println("check failed in postMessage()");
            return null;
        }
        try {
            String sql = "INSERT INTO message (posted_by, message_text,time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            return getMessageByValues(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * updates a message record in message table by message_id. Replaces message_text with 
     * a new text
     * @param message to be updated. 
     * @return Message that was updated. Returns Null otherwise
    */ 
    public Message patchMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        String msgText = message.getMessage_text();
        int msg_id = message.getMessage_id();
        Message msg = getMessageById(msg_id);
        if(msgText == "" || msgText.length() > 254 || msg == null){
            System.out.println("check failed in patchMessage()");
            return null;
        }
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, msgText);
            preparedStatement.setInt(2, msg_id);
            preparedStatement.executeUpdate();
            return getMessageById(msg_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    //
    //returns deleted message
    /**
     * deletes a message in message table by message_id
     * @param id of message to be deleted. 
     * @return Message that was deleted. Returns Null otherwise
    */ 
    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message msg = getMessageById(id);

        if(msg == null){
            return null;
        }

        try {
            String sql = "DELETE FROM message WHERE message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return msg;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
