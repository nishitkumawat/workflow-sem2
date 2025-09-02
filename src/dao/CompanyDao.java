package dao;

import models.Company;
import utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDao {

    private Connection connection;

    public CompanyDao() {
        this.connection = ConnectionProvider.getConnection();
    }

    public boolean checkExistence(String companyName){
        String query = "SELECT * FROM Companies WHERE company_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, companyName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addCompany(Company company) {
        String query = "INSERT INTO Companies (company_name, admin_user_id, admin_password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, company.getCompanyName());
            stmt.setString(2, company.getAdminUserId());
            stmt.setString(3, company.getAdminPassword());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Company getCompanyById(int companyId) {
        String query = "SELECT * FROM Companies WHERE company_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, companyId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Company(
                        rs.getInt("company_id"),
                        rs.getString("company_name"),
                        rs.getString("admin_user_id"),
                        rs.getString("admin_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Company getCompanyByAdminUserId(String adminUserId) {
        String query = "SELECT * FROM Companies WHERE admin_user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, adminUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Company(
                        rs.getInt("company_id"),
                        rs.getString("company_name"),
                        rs.getString("admin_user_id"),
                        rs.getString("admin_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCompany(Company company) {
        String query = "UPDATE Companies SET company_name = ?, admin_user_id = ?, admin_password = ? WHERE company_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, company.getCompanyName());
            stmt.setString(2, company.getAdminUserId());
            stmt.setString(3, company.getAdminPassword());
            stmt.setInt(4, company.getCompanyId());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCompany(int companyId) {
        String query = "DELETE FROM Companies WHERE company_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, companyId);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
