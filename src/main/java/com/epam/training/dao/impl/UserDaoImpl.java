package com.epam.training.dao.impl;

import com.epam.training.constant.TableBD;
import com.epam.training.dao.AbstractDao;
import com.epam.training.exception.DAOException;
import com.epam.training.dao.UserDao;
import com.epam.training.mapper.UserRowMapper;
import com.epam.training.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private final String ADD_USER = "insert into user_data(login, email, password, admin_access) values(?, ?, ?, ?);";
    private final String FIND_MAX_ID = "SELECT max(user_id) from user_data;";
    private final String FIND_USER_BY_EMAIL = "SELECT * FROM user_data WHERE email=?";
    private final String FIND_USER_BY_ID = "SELECT * FROM user_data WHERE user_id=?";
    private final String FIND_USER = "SELECT * FROM user_data WHERE email=? and password=?";
    private final String FIND_EMAIL_BY_ID = "SELECT email from user_data where user_id=?";
    private final String UPDATE_USER = "update user_data set login=?, email=?, password=?, admin_access=? where user_id=?";
    private final String UPDATE_ACCESS = "update user_data set admin_access=? where user_id=?;";
    private final String GET_ALL_USERS = "SELECT * FROM user_data;";
    private final String DELETE_USER = "delete from user_data where user_id=?;";
    private final String FIND_ALL_ADMIN = "select * from user_data where admin_access=true";

    public UserDaoImpl(Connection connection) {
        super(connection);
    }


    @Override
    public int create(User user) throws DAOException {
        return save(ADD_USER, user.getLogin(), user.getEmail(), user.getPassword(), user.isAdminAccess());
    }

    @Override
    public int updateData(User user) throws DAOException {
        return updateData(UPDATE_USER, user.getLogin(), user.getEmail(), user.getPassword(),
                user.isAdminAccess(), user.getUserId());
    }

    @Override
    public int updateAccess(Long id, boolean userAccess) throws DAOException {
        return updateData(UPDATE_ACCESS, userAccess, id);
    }

    @Override
    public Optional<User> getUser(Long id) throws DAOException {
        return getSingleResult(FIND_USER_BY_ID, new UserRowMapper(), id);
    }

    @Override
    public Optional<User> getUser(String email) throws DAOException {
        return getSingleResult(FIND_USER_BY_EMAIL, new UserRowMapper(), email);
    }

    @Override
    public Optional<User> getUser(User user) throws DAOException {
        return getSingleResult(FIND_USER, new UserRowMapper(), user.getEmail(), user.getPassword());
    }

    @Override
    public int delete(Long id) throws DAOException {
        return delete(DELETE_USER, id);
    }

    @Override
    public List<User> getListUsers() throws DAOException {
        return findAll(GET_ALL_USERS);
    }

    @Override
    public long getMaxId() throws DAOException {
        return findMaxId(FIND_MAX_ID);
    }

    @Override
    public String getEmailById(Long id) throws DAOException{
        return singleStringExecuteQuery(FIND_EMAIL_BY_ID, id);
    }

    @Override
    public List<User> getListAdmin() throws DAOException{
        return findAll(FIND_ALL_ADMIN);
    }

    protected String getTableName() {
        return TableBD.TABLE_USER;
    }
}
