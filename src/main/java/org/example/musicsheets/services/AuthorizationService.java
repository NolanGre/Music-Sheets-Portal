package org.example.musicsheets.services;

import org.example.musicsheets.exceptions.InvalidUserIdException;
import org.example.musicsheets.exceptions.InvalidUserRoleException;
import org.example.musicsheets.models.UserRole;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isAdmin(UserRole userRole) {
        if (userRole == null) {
            throw new InvalidUserRoleException("UserRole cannot be null");
        }

        return UserRole.ADMIN.equals(userRole);
    }

    public boolean isOwner(Long authenticatedUserId, Long resourceOwnerId) {
        if (authenticatedUserId == null) {
            throw new InvalidUserIdException("User ID cannot be null");
        }

        return authenticatedUserId.equals(resourceOwnerId);
    }

    public boolean isAdminOrOwner(Long authenticatedUserId, Long resourceOwnerId, UserRole userRole) {
        return isAdmin(userRole) || isOwner(authenticatedUserId, resourceOwnerId);
    }
}
