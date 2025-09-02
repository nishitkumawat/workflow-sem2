package models;

public class Company {

    private int companyId;
    private String companyName;
    private String adminUserId;
    private String adminPassword;

    public Company(){
        
    }

    public Company(int companyId, String companyName, String adminUserId, String adminPassword) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.adminUserId = adminUserId;
        this.adminPassword = adminPassword;
    }

    public Company(String companyName, String adminUserId, String adminPassword) {
        this.companyName = companyName;
        this.adminUserId = adminUserId;
        this.adminPassword = adminPassword;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", adminUserId='" + adminUserId + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
