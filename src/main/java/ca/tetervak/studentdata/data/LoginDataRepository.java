package ca.tetervak.studentdata.data;

import java.util.List;

public interface LoginDataRepository {
    boolean userExists(String login);
    void insertUser(String login, String password);
    void insertRole(String login, String role);
    void removeUser(String login);
    void removeRole(String login, String role);
    void removeRoles(String login);
    List<String> getAllUserNames(String role);
    List<String> getAllRoleNames(String login);
    void updatePassword(String login, String password);
    boolean checkPassword(String login, String password);
    String getPassword(String login);
}
