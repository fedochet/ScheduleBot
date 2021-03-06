package org.anstreth.schedulebot.model;

import lombok.Data;

@Data
public class User {
    private final long id;
    private final UserState state;

    public User(long id, UserState state) {
        this.id = id;
        this.state = state;
    }

    public User(long id) {
        this(id, UserState.NO_GROUP);
    }
}
