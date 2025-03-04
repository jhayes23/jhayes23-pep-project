package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {


    public Message insertNewMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) values (?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            int rows = preparedStatement.executeUpdate();

            if(rows > 0){
                ResultSet pkResultSet = preparedStatement.getGeneratedKeys();
                if(pkResultSet.next()){
                    int generated_message_id = (int) pkResultSet.getLong(1);
                    message.setMessage_id(generated_message_id);
                    return message;
                }
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                    );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();
        try {
                    String sql = "SELECT * FROM message WHERE message_id = ?";
        
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    ResultSet rs = preparedStatement.executeQuery();
                    if(rs.next()){
                        message.setMessage_id(rs.getInt("message_id"));
                        message.setPosted_by(rs.getInt("posted_by"));
                        message.setMessage_text(rs.getString("message_text"));
                        message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

                        return message;
                    }
        } catch (SQLException e) {
                    System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = getMessageById(id);
        if( message != null){
            try {
                String sql = "DELETE * FROM message WHERE message_id = ?";
    
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
        }
        return message;
    }

    public Message updateMessageById(Message message){
        Connection connection = ConnectionUtil.getConnection();
        
        if( message != null){
            try {
                String sql = "UPDATE message SET message_text = ? WHERE message_id = ? ";
    
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, message.getMessage_text());
                preparedStatement.setInt(2, message.getMessage_id());
                preparedStatement.executeUpdate();
            
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
        }
        return getMessageById(message.getMessage_id());
    }

    public List<Message> getAllMessagesByUserId(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                    );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }
    
}
