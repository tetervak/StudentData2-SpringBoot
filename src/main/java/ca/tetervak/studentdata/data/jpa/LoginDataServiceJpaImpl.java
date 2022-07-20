package ca.tetervak.studentdata.data.jpa;

import ca.tetervak.studentdata.data.LoginDataService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LoginDataServiceJpaImpl implements LoginDataService {

    private final UserDataRepositoryJpa userDataRepository;
    private final RoleDataRepositoryJpa roleDataRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginDataServiceJpaImpl(
            UserDataRepositoryJpa userDataRepository,
            RoleDataRepositoryJpa roleDataRepository,
            PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.roleDataRepository = roleDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean userExists(String userName) {
        return userDataRepository.findUserEntityByUserNameIs(userName) != null;
    }

    @Override
    public void insertUser(String userName, String password) {
        UserEntityJpa userEntity = new UserEntityJpa();
        userEntity.setUserName(userName);
        userEntity.setPassword(passwordEncoder.encode(password));
        userDataRepository.save(userEntity);
    }

    @Override
    public void insertRole(String userName, String roleName) {
        UserEntityJpa userEntity = userDataRepository.findUserEntityByUserNameIs(userName);
        if(userEntity != null){
            RoleEntityJpa roleEntity = roleDataRepository.findRoleEntityByRoleNameIs(roleName);
            if(roleEntity != null){
                userEntity.getRoles().add(roleEntity);
                userDataRepository.save(userEntity);
            }
        }
    }

    @Override
    public void removeUser(String userName) {
        userDataRepository.deleteUserEntityByUserNameIs(userName);
    }

    @Override
    public void removeRole(String userName, String roleName) {
        UserEntityJpa userEntity = userDataRepository.findUserEntityByUserNameIs(userName);
        if(userEntity != null){
            List<RoleEntityJpa> roles = userEntity.getRoles();
            for(RoleEntityJpa roleEntity: roles){
                if(roleEntity.getRoleName().equals(roleName)){
                    roles.remove(roleEntity);
                    break;
                }
            }
            userDataRepository.save(userEntity);
        }
    }

    @Override
    public void removeRoles(String userName) {
        UserEntityJpa userEntity = userDataRepository.findUserEntityByUserNameIs(userName);
        if(userEntity != null) {
            userEntity.getRoles().clear();
            userDataRepository.save(userEntity);
        }
    }

    @Override
    public List<String> getAllUserNames(String roleName) {
        List<String> userNames = new ArrayList<>();
        RoleEntityJpa roleEntity = roleDataRepository.findRoleEntityByRoleNameIs(roleName);
        if(roleEntity != null){
            List<UserEntityJpa> users = roleEntity.getUsers();
            for(UserEntityJpa userEntity: users){
                userNames.add(userEntity.getUserName());
            }
        }
        return userNames;
    }

    @Override
    public List<String> getAllRoleNames(String userName) {
        List<String> roleNames = new ArrayList<>();
        UserEntityJpa userEntity = userDataRepository.findUserEntityByUserNameIs(userName);
        if(userEntity != null){
            List<RoleEntityJpa> roles = userEntity.getRoles();
            for(RoleEntityJpa roleEntity: roles){
                roleNames.add(roleEntity.getRoleName());
            }
        }

        return roleNames;
    }

    @Override
    public void updatePassword(String userName, String password) {
        UserEntityJpa userEntity = userDataRepository.findUserEntityByUserNameIs(userName);
        if(userEntity != null){
            userEntity.setPassword(passwordEncoder.encode(password));
            userDataRepository.save(userEntity);
        }
    }

    @Override
    public boolean checkPassword(String userName, String password) {
        String storedPassword = getPassword(userName);
        if(storedPassword != null) {
            return passwordEncoder.matches(password, storedPassword);
        }else{
            return false;
        }
    }

    @Override
    public String getPassword(String userName) {
        UserEntityJpa userEntity = userDataRepository.findUserEntityByUserNameIs(userName);
        if(userEntity != null){
            return userEntity.getPassword();
        }else{
            return null;
        }
    }
}
