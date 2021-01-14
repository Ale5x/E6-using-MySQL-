package com.epam.training.dao;

import com.epam.training.exception.DAOException;
import com.epam.training.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    public int create(User user) throws DAOException;

    public int updateData(User user) throws DAOException;

    public int updateAccess(Long id, boolean userAccess) throws DAOException;

    public Optional<User> getUser(Long id) throws DAOException;

    public Optional<User> getUser(String email) throws DAOException;

    public Optional<User> getUser(User user) throws DAOException;

    public int delete(Long id) throws DAOException;

    public List<User> getListUsers() throws DAOException;

    public long getMaxId() throws DAOException;

    public String getEmailById(Long id) throws DAOException;

    public List<User> getListAdmin() throws DAOException;
}
