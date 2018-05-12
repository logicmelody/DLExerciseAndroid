package com.dl.dlexerciseandroid.utils;

import com.facebook.AccessToken;
import com.facebook.Profile;

import java.util.Set;

/**
 * Created by logicmelody on 2016/4/8.
 */
public class FbUtils {

    public static final class Permission {
        public static final String PUBLIC_PROFILE = "public_profile";
        public static final String USER_FRIENDS = "user_friends";
        public static final String EMAIL = "email";
    }

    public static boolean hasPermission(String permission) {
        Set<String> permissionSet = AccessToken.getCurrentAccessToken().getPermissions();

        return permissionSet.contains(permission);
    }

    /**
     * Build URI of revoking permission
     *
     * @param permission
     * @return /{user-id}/permissions/{permission-name}
     */
    public static String getRevokingPermissionUri(String permission) {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(Profile.getCurrentProfile().getId()).append("/permissions/").append(permission);

        return sb.toString();
    }
}