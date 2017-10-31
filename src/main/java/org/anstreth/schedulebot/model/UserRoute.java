package org.anstreth.schedulebot.model;

/**
 * Enum to represent "screen" on which user is present.
 *
 * @author Roman Golyshev
 */
public enum UserRoute {
    /**
     * Initial state in which user is required to send group's name.
     */
    GROUP_SEARCH,

    /**
     * Basic state in which user can ask for schedule and enter the menu.
     */
    HOME,

    /**
     * Settings state, in which user can reset his group or return back to HOME state.
     */
    MENU
}
