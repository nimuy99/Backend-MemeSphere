package com.memesphere.global.jwt;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoggedInUserStore {
    private final Set<Long> loggedInUsers = ConcurrentHashMap.newKeySet();

    public void addUser(Long userId) {
        loggedInUsers.add(userId);
    }

    public void removeUser(Long userId) {
        loggedInUsers.remove(userId);
    }

    public boolean isUserLoggedIn(Long userId) {
        return loggedInUsers.contains(userId);
    }

    public Set<Long> getLoggedInUsers() {
        return loggedInUsers;
    }
}
