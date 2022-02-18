package ca.tetervak.studentdata.model;

public interface PasswordGenerator {
    String randomPassword();
    String randomPassword(int length);
}
