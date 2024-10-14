package dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role 
{
    STAFF(Set.of(Permission.STAFF_READ)),
    MANAGER(Set.of(
        Permission.MANAGER_READ,
        Permission.MANAGER_CREATE,
        Permission.MANAGER_UPDATE,
        Permission.MANAGER_DELETE
    ));
    
    private final Set<Permission> permisisons;
    
    Role(Set<Permission> permissions)
    {
        this.permisisons = permissions;
    }

    public List<String> getPermission()
    {
        return permisisons.stream()
               .map(Permission::getPermission)
               .collect(Collectors.toList());
    }
}
