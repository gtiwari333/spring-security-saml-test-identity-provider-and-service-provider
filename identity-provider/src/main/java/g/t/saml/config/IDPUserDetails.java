package g.t.saml.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.saml.saml2.attribute.Attribute;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class IDPUserDetails implements UserDetails {


    private List<Attribute> samlAttributesToSendToSP;
    private String username;
    private String role;
    private List<Authority> authorities;


    public IDPUserDetails(String username, String role, List<Attribute> attributes) {
        this.username = username;
        this.authorities = Collections.singletonList(new Authority(role));
        this.samlAttributesToSendToSP = attributes;
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "{noop}pass"; // no password encoder
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

class Authority implements GrantedAuthority {
    private String authority;

    Authority(String name) {
        this.authority = name;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}