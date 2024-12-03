package org.thuvien.utils;

import org.thuvien.models.Member;

public class SessionManager {
    private static Member currentUser;

    private SessionManager() {
    }

    public static void setCurrentUser(Member user) {
        currentUser = user;
    }

    public static Member getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
