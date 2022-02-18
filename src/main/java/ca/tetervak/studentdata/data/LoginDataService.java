package ca.tetervak.studentdata.data;

import java.util.List;

public interface LoginDataService {
    boolean userExists(String userName);
    void insertUser(String userName, String password);
    void insertRole(String userName, String roleName);
    void removeUser(String userName);
    void removeRole(String userName, String roleName);
    void removeRoles(String userName);
    List<String> getAllUserNames(String roleName);
    List<String> getAllRoleNames(String userName);
    void updatePassword(String userName, String password);
    boolean checkPassword(String userName, String password);
    String getPassword(String userName);
}
