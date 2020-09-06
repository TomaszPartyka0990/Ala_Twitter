package pl.sda.partyka.services.impl;

import pl.sda.partyka.model.AppUser;
import pl.sda.partyka.model.dao.AppUserDAO;
import pl.sda.partyka.services.UserManagementService;
import pl.sda.partyka.validation.ValidationError;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

import static pl.sda.partyka.validation.ErrorContents.*;

public class UserManagementServiceImpl implements UserManagementService {

    private AppUserDAO dao;

    public UserManagementServiceImpl(AppUserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void register(AppUser user) {
        dao.saveUser(user);
    }

    @Override
    public void follow(String currentUserlogin, String userToFollowLogin) {
        AppUser currentUser = dao.getUserByLogin(currentUserlogin);
        AppUser userToFollow = dao.getUserByLogin(userToFollowLogin);
        dao.follow(currentUser, userToFollow);
    }

    @Override
    public void unfollow(String currentUserlogin, String userToUnFollowLogin) {
        AppUser currentUser = dao.getUserByLogin(currentUserlogin);
        AppUser userToUnFollow = dao.getUserByLogin(userToUnFollowLogin);
        dao.follow(currentUser, userToUnFollow);
    }

    @Override
    public Set<AppUser> getNotFollowedUsers(String currentUserLogin) {
        AppUser currentUser = dao.getUserByLogin(currentUserLogin);
        return dao.getNotFollowedUsers(currentUser);
    }

    @Override
    public Set<AppUser> getFollowedUsers(String currentUserLogin) {
        AppUser currentUser = dao.getUserByLogin(currentUserLogin);
        return dao.getFollowedUsers(currentUser.getId());
    }

    @Override
    public Set<AppUser> getFollowers(String currentUserLogin) {
        AppUser currentUser = dao.getUserByLogin(currentUserLogin);
        return dao.getFollowers(currentUser.getId());
    }

    @Override
    public Set<ValidationError> validateRegUser(AppUser user) {
        Set<ValidationError> errors = new HashSet<>();
        try {
            dao.getUserByLogin(user.getLogin());
            ValidationError loginError = new ValidationError
                    (LOGIN_ERROR_HEADER, LOGIN_REGISTARTION_ERROR_MESSAGE);
            errors.add(loginError);
        } catch (NoResultException e){
            //LOGIN DOESNT EXISTS
        }
        try {
            dao.getUserByEmail(user.getEmail());
            ValidationError emailError = new ValidationError
                    (EMAIL_ERROR_HEADER, EMAIL_REGISTRATION_ERROR_MESSAGE);
            errors.add(emailError);
        } catch (NoResultException e){
            //EMAIL DOESNT EXISTS
        }
        return errors;
    }

    @Override
    public Set<ValidationError> validateLogUser(AppUser user) {
        Set<ValidationError> errors = new HashSet<>();
        try {
            AppUser appUser = dao.getUserByLogin(user.getLogin());
            if (!appUser.getPassword().equals(user.getPassword())){
                ValidationError passwordError = new ValidationError
                        (PASSWORD_ERROR_HEADER, PASSWORD_LOGGING_ERROR_MESSAGE);
                errors.add(passwordError);
            }
        } catch (NoResultException e) {
            ValidationError loginError = new ValidationError
                    (LOGIN_ERROR_HEADER, LOGIN_LOGGING_ERROR_MESSAGE);
            errors.add(loginError);
        }
        return errors;
    }
}
