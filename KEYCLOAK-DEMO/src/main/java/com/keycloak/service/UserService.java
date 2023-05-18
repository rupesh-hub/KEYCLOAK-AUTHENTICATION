package com.keycloak.service;

import com.keycloak.mapper.UserMapper;
import com.keycloak.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserMapper userMapper;
    private final EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return findAll(null, null);
    }

    @Override
    public List<User> findAll(int start, int max) {
        return findAll((Integer) start, (Integer) max);
    }

    private List<User> findAll(Integer start, Integer max) {
//        TypedQuery<User> query = entityManager.createNamedQuery("searchForUser", User.class);
//
//        if(start != null) {
//            query.setFirstResult(start);
//        }
//        if(max != null) {
//            query.setMaxResults(max);
//        }
//        query.setParameter("search", "%");
//        List<User> users =  query.getResultList();
        return null;
    }

    @Override
    public User getUserByUsernameMapper(String username) {
        User user = userMapper.findByUserName(username);
        if (user != null)
            user.setEmail(userMapper.getEmail(user.getPisEmployeeCode()));
        return user;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
//        TypedQuery<User> query = entityManager.createNamedQuery("getUserByUsername", User.class);
//        query.setParameter("username", username);
//        return query.getResultList().stream().findFirst();
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return this.getUserByUsername(email);
    }

    @Override
    public List<User> searchForUserByUsernameOrEmail(String searchString) {
        return searchForUserByUsernameOrEmail(searchString, null, null);
    }

    @Override
    public List<User> searchForUserByUsernameOrEmail(String searchString, int start, int max) {
        return searchForUserByUsernameOrEmail(searchString, (Integer) start, (Integer) max);
    }

    private List<User> searchForUserByUsernameOrEmail(String searchString, Integer start, Integer max) {
//        TypedQuery<User> query = entityManager.createNamedQuery("searchForUser", User.class);
//        query.setParameter("search", "%" + searchString + "%");
//        if(start != null) {
//            query.setFirstResult(start);
//        }
//        if(max != null) {
//            query.setMaxResults(max);
//        }
//        return query.getResultList();

        return null;
    }

    @Override
    public User getUserById(String id) {
        return entityManager.find(User.class, UUID.fromString(id));
    }

    @Override
    public User createUser(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        return user;
    }

    @Override
    public void deleteUser(String username) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(this.getUserByUsername(username));
        transaction.commit();
    }

    @Override
    public void close() {
        this.entityManager.close();
    }

    @Override
    public User updateUser(User userEntity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(userEntity);
        transaction.commit();
        return userEntity;
    }

    @Override
    public void updatePassword(String encodePassword, String externalId) {
        User user = this.getUserByUsername(externalId).get();
        user.setPassword(encodePassword);
        this.updateUser(user);
    }

    @Override
    public int size() {
        // return entityManager.createNamedQuery("getUserCount", Integer.class).getSingleResult();
        return 0;
    }


}
