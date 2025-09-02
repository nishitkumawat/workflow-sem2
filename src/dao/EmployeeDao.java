package dao;

import models.Employee;
import utils.ConnectionProvider;
import utils.EmployeeList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private Connection connection;

    public EmployeeDao() {
        this.connection = ConnectionProvider.getConnection();
    }

    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO Employees (company_id, employee_name, employee_username, employee_password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employee.getCompanyId());
            stmt.setString(2, employee.getEmployeeName());
            stmt.setString(3, employee.getEmployeeUsername());
            stmt.setString(4, employee.getEmployeePassword());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getEmployeeById(int employeeId) {
        String query = "SELECT * FROM Employees WHERE employee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employee_id"),
                        rs.getInt("company_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByUsername(String employeeUsername) {
        String query = "SELECT * FROM Employees WHERE employee_username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employeeUsername);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employee_id"),
                        rs.getInt("company_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getEmployeesByCompanyId1(int companyId) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employees WHERE company_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, companyId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getInt("company_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public EmployeeList getEmployeesByCompanyId(int companyId) {
        EmployeeList employees = new EmployeeList();
        String query = "SELECT * FROM Employees WHERE company_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, companyId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.addFirst(new Employee(
                        rs.getInt("employee_id"),
                        rs.getInt("company_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Integer> getAllEmployeeIds() {
        List<Integer> employeeIds = new ArrayList<>();
        String query = "SELECT employee_id FROM Employees";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employeeIds.add(rs.getInt("employee_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeIds;
    }

    public String getEmployeeNameById(int employeeId) {
        String query = "SELECT employee_name FROM Employees WHERE employee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("employee_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmployee(Employee employee) {
        String query = "UPDATE Employees SET company_id = ?, employee_name = ?, employee_username = ?, employee_password = ? WHERE employee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, employee.getCompanyId());
            stmt.setString(2, employee.getEmployeeName());
            stmt.setString(3, employee.getEmployeeUsername());
            stmt.setString(4, employee.getEmployeePassword());
            stmt.setInt(5, employee.getEmployeeId());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int employeeId) {
        String callProcedureQuery = "{CALL deleteEmployee(?)}";
        
        try (PreparedStatement callProcedureStmt = connection.prepareStatement(callProcedureQuery)) {
            
            callProcedureStmt.setInt(1, employeeId);
            int result = callProcedureStmt.executeUpdate();
            
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employees";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("employee_id"),
                        rs.getInt("company_id"),
                        rs.getString("employee_name"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
