package server.auth;

import java.util.*;

public class AuthorizationService {
    private final Map<UserRole, Map<ServicePoint, List<HTTPMethod>>> permissions;

    public AuthorizationService() {
        permissions = new HashMap<>();
        //Customer Permissions
        permissions.put(UserRole.CUSTOMER, new HashMap<>());
        permissions.get(UserRole.CUSTOMER).put(
                ServicePoint.PRODUCT,
                new ArrayList<>(List.of(HTTPMethod.GET))
        );
        permissions.get(UserRole.CUSTOMER).put(
                ServicePoint.CART,
                new ArrayList<>(List.of(HTTPMethod.GET, HTTPMethod.PUT, HTTPMethod.DELETE))
        );
        //Admin Permissions
        permissions.put(UserRole.ADMIN, new HashMap<>());
        permissions.get(UserRole.ADMIN).put(
                ServicePoint.PRODUCT,
                new ArrayList<>(List.of(HTTPMethod.GET, HTTPMethod.PUT, HTTPMethod.DELETE))
        );
        permissions.get(UserRole.ADMIN).put(
                ServicePoint.USER,
                new ArrayList<>(List.of(HTTPMethod.GET, HTTPMethod.PUT, HTTPMethod.DELETE))
        );
    }

    public boolean isAuthorized(UserRole role, ServicePoint resource, HTTPMethod method) {
        Map<ServicePoint, List<HTTPMethod>> policyForRole = permissions.get(role);
        if (policyForRole != null && policyForRole.containsKey(resource)) {
            return policyForRole.get(resource).contains(method);
        }
        return false;
    }
}
