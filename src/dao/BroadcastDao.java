package dao;

import models.Broadcast;
import utils.BroadcastQueue;
import utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastDao {

    private Connection connection;

    public BroadcastDao() {
        this.connection = ConnectionProvider.getConnection();
    }

    public boolean addBroadcast(Broadcast broadcast) {
        String query = "INSERT INTO Broadcasts (company_id, message, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, broadcast.getCompanyId());
            stmt.setString(2, broadcast.getMessage());
            stmt.setTimestamp(3, broadcast.getTimestamp());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Broadcast> getBroadcastsByCompanyId1(int companyId) {
        List<Broadcast> broadcasts = new ArrayList<>();
        String query = "SELECT * FROM Broadcasts WHERE company_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, companyId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                broadcasts.add(new Broadcast(
                        rs.getInt("id"),
                        rs.getInt("company_id"),
                        rs.getString("message"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return broadcasts;
    }

    public BroadcastQueue getBroadcastsByCompanyId(int companyId) {
        BroadcastQueue broadcasts = new BroadcastQueue();
        String query = "SELECT * FROM Broadcasts WHERE company_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, companyId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                broadcasts.enqueue(new Broadcast(
                        rs.getInt("id"),
                        rs.getInt("company_id"),
                        rs.getString("message"),
                        rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return broadcasts;
    }
}
