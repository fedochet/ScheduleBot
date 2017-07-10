package org.anstreth.schedulebot.schedulebotservice.user;

import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreationService {
    private final UserGroupManager userGroupManager;

    @Autowired
    public UserCreationService(UserGroupManager userGroupManager) {
        this.userGroupManager = userGroupManager;
    }

    public User createNewUser(UserRequest userRequest) {
        return userGroupManager.saveUserWithoutGroup(userRequest.getUserId());
    }

}
