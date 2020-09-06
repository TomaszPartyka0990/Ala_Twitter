package pl.sda.partyka.services;

import pl.sda.partyka.model.AppUser;
import pl.sda.partyka.model.dao.AppUserDAO;
import pl.sda.partyka.validation.ValidationError;

import java.util.Set;

public interface UserManagementService {
    void register(AppUser user);
    void follow(String currentUserlogin, String userToFollowLogin);
    void unfollow(String currentUserlogin, String userToUnFollowLogin);
    Set<AppUser> getNotFollowedUsers(String currentUserLogin);
    Set<AppUser> getFollowedUsers(String currentUserLogin);
    Set<AppUser> getFollowers(String currentUserLogin);
    Set<ValidationError> validateRegUser(AppUser user);
    Set<ValidationError> validateLogUser(AppUser user);
}
