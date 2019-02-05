package g.t.saml.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class IDPInMemoryUserDetailsManager implements UserDetailsManager {

    private final Map<String, UserDetails> users = new HashMap<>();


    public IDPInMemoryUserDetailsManager(Collection<IDPUserDetails> users) {
        for (UserDetails user : users) {
            createUser(user);
        }
    }

    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "user already  exists");

        users.put(user.getUsername().toLowerCase(), user);
    }

    public void deleteUser(String username) {
        users.remove(username.toLowerCase());
    }

    public void updateUser(UserDetails user) {

    }

    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails user = users.get(username.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }
}
