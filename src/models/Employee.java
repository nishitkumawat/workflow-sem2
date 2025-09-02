package models;

public class Employee {

    private int employeeId;
    private int companyId;
    private String employeeName;
    private String employeeUsername;
    private String employeePassword;

    public Employee(int employeeId, int companyId, String employeeName,String employeeUsername, String employeePassword) {
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.employeeName = employeeName;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", companyId=" + companyId +
                ", employeeName='" + employeeName + '\'' +
                ", employeePassword='" + employeePassword + '\'' +
                '}';
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }
}
