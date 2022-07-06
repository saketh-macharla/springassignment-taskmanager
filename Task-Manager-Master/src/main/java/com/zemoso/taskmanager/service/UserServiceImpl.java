package com.zemoso.taskmanager.service;

import com.zemoso.taskmanager.entity.Roles;
import com.zemoso.taskmanager.repository.UserRepository;
import com.zemoso.taskmanager.entity.Task;
import com.zemoso.taskmanager.entity.Users;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService {

    private Logger myLogger = Logger.getLogger(getClass().getName());
    @Autowired
    UserRepository userRepository;

    @Override
    public Users createUser(Users user) {
        return userRepository.save(user);
    }


    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String username) {
        Optional<Users> user =  userRepository.findById(username);
        Users temp =null;
        if(user.isPresent()) {
            temp = user.get();
            if(temp.getTasksOwned()!= null && temp.getTasksOwned().size()>0){
                temp.getTasksOwned().forEach(task -> {
                    task.setOwner(null);
                    task.setCompleted(false);
                });
            }
            userRepository.deleteById(username);
            myLogger.info("Deleted User: "+ username);
        }
    }

    @Override
    public List<Task> findAllTasks(String username) {
        Optional<Users> user= userRepository.findById(username);
        Users tempUser = null;
        if(user.isPresent()) {
            tempUser = user.get();
            myLogger.info( tempUser.getTasksOwned().toString());
            return tempUser.getTasksOwned();
        }
        return null;
    }

    @Override
    public Users  getUserByUsername(String username) {
        Optional<Users> user= userRepository.findById(username);
        myLogger.info(user.toString());
        if(user.isPresent())
            return user.get();
        return null;
    }

    @Override
    public List<Task> getTasksInProgress(Users user) {

        return user.getTasksOwned().stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }
    @Override
    public List<Task> getTasksCompleted(Users user) {

        return user.getTasksOwned().stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAdmin(Users user) {
        String roleName = "ROLE_ADMIN";
        return user.getRoles().stream().map(Roles::getAuthority).allMatch(roleName::equals);
    }
}
