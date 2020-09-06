package pl.sda.partyka.model.dao.impl;

import pl.sda.partyka.model.AppUser;
import pl.sda.partyka.model.dao.AbstractDAO;
import pl.sda.partyka.model.dao.AppUserDAO;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;

public class MySQLAppUserDAOImpl extends AbstractDAO implements AppUserDAO {
    @Override
    public HashSet<AppUser> getAll() {
        TypedQuery<AppUser> query = entityManager.createQuery
                ("select u from AppUser u", AppUser.class);
        return new HashSet<AppUser>(query.getResultList());
    }

    @Override
    public void saveUser(AppUser user) {
        hibernateUtil.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        deleteRelationForFollowers(id);
        hibernateUtil.delete(AppUser.class, id);
    }

    @Override
    public AppUser getUserByEmail(String email) throws NoResultException {
        TypedQuery<AppUser> query = entityManager.createQuery
                ("select u from AppUser u where u.email = :email", AppUser.class);
        return query.setParameter("email", email).getSingleResult();
    }

    @Override
    public AppUser getUserByLogin(String login) throws NoResultException {
        TypedQuery<AppUser> query = entityManager.createQuery
                ("select u from AppUser u where u.login = :login", AppUser.class);
        return query.setParameter("login", login).getSingleResult();
    }

    @Override
    public HashSet<AppUser> getFollowedUsers(Long userId) {
        Query query = entityManager.createQuery
                ("select followedByUser from AppUser u where u.id = :userId");
        return new HashSet<AppUser>(query.
                setParameter("userId", userId).getResultList());
    }

    @Override
    public HashSet<AppUser> getNotFollowedUsers(AppUser user) {
        TypedQuery<AppUser> query = entityManager.createQuery
                ("select u from AppUser u where u.login != :login", AppUser.class);
        query.setParameter("login", user.getLogin());
        List<AppUser> users = query.getResultList();
        users.removeAll(getFollowedUsers(user.getId()));
        return new HashSet<AppUser>(users);
    }

    @Override
    public HashSet<AppUser> getFollowers(Long userId) {
        Query query = entityManager.createQuery
                ("select followers from AppUser u where u.id = :userId");
        return new HashSet<AppUser>(query.
                setParameter("userId", userId).getResultList());
    }

    @Override
    public void follow(AppUser currentUser, AppUser userToFollow) {
        currentUser.getFollowedByUser().add(userToFollow);
        saveUser(currentUser);
    }

    @Override
    public void unfollow(AppUser currentUser, AppUser userToUnFollow) {
        currentUser.getFollowedByUser().remove(userToUnFollow);
        saveUser(currentUser);
    }

    private void deleteRelationForFollowers(Long id) {
        AppUser userToDelete = entityManager.find(AppUser.class, id);
        HashSet<AppUser> followers = getFollowers(id);
        for (AppUser follower : followers) {
            follower.getFollowedByUser().remove(userToDelete);
            saveUser(follower);
        }
    }
}
