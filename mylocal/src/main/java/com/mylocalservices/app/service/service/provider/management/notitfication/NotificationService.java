package com.mylocalservices.app.service.service.provider.management.notitfication;

import com.mylocalservices.app.entity.auth.User;

public interface NotificationService {
    void notifyUser(User user, String message);
}
