package dao;

import models.Message;
import utils.ConnectionProvider;
import utils.MessageQueue;
import java.sql.*;

public class MessageDao {

    private Connection connection;

    public MessageDao() {
        this.connection = ConnectionProvider.getConnection();
    }

    public boolean addMessage(Message message) {
        String query = "INSERT INTO Messages (sender_id, receiver_id, message_text, timestamp, is_broadcast) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, message.getSenderId());
            if (message.getReceiverId() == null) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, message.getReceiverId());
            }
            stmt.setString(3, message.getMessageText());
            stmt.setTimestamp(4, new Timestamp(message.getTimestamp().getTime()));
            stmt.setBoolean(5, message.isBroadcast());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Message getMessageById(int messageId) {
        String query = "SELECT * FROM Messages WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, messageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("message_text"),
                        rs.getTimestamp("timestamp"),
                        rs.getBoolean("is_broadcast")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public List<Message> getMessagesBySenderId(int senderId) {
    //     List<Message> messages = new ArrayList<>();
    //     String query = "SELECT * FROM Messages WHERE sender_id = ?";
    //     try (PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setInt(1, senderId);
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             messages.add(new Message(
    //                     rs.getInt("message_id"),
    //                     rs.getInt("sender_id"),
    //                     rs.getInt("receiver_id"),
    //                     rs.getString("message_text"),
    //                     rs.getTimestamp("timestamp"),
    //                     rs.getBoolean("is_broadcast")
    //             ));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return messages;
    // }

    // public List<Message> getMessagesByReceiverId(int receiverId) {
    //     List<Message> messages = new ArrayList<>();
    //     String query = "SELECT * FROM Messages WHERE receiver_id = ?";
    //     try (PreparedStatement stmt = connection.prepareStatement(query)) {
    //         stmt.setInt(1, receiverId);
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             messages.add(new Message(
    //                     rs.getInt("message_id"),
    //                     rs.getInt("sender_id"),
    //                     rs.getInt("receiver_id"),
    //                     rs.getString("message_text"),
    //                     rs.getTimestamp("timestamp"),
    //                     rs.getBoolean("is_broadcast")
    //             ));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return messages;
    // }

    public MessageQueue getMessages(int employeeId1, int employeeId2) {
        MessageQueue messages = new MessageQueue();
        String query = "SELECT m.*, e.employee_name FROM Messages m JOIN Employees e ON m.sender_id = e.employee_id WHERE (m.sender_id = ? AND m.receiver_id = ?) OR (m.sender_id = ? AND m.receiver_id = ?) ORDER BY m.timestamp";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId1);
            stmt.setInt(2, employeeId2);
            stmt.setInt(3, employeeId2);
            stmt.setInt(4, employeeId1);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String senderName = rs.getString("employee_name");
                String messageText = rs.getString("message_text");
                String formattedMessage = senderName + ": " + messageText;
                messages.enqueue(formattedMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    public boolean sendMessage(int senderId, int receiverId, String messageText) {
        String query = "INSERT INTO Messages (sender_id, receiver_id, message_text) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, messageText);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMessage(int messageId) {
        String query = "DELETE FROM Messages WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, messageId);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
