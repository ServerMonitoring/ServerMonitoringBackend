package com.example.back.config.security.components;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class NodeAuthentication implements Authentication {
    private final String nodeId;
    private boolean authenticated = true;
    private final Collection<? extends GrantedAuthority> authorities;

    public NodeAuthentication(String nodeId, Collection<? extends GrantedAuthority> authorities) {
        this.nodeId = nodeId;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return nodeId;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return nodeId;
    }
}
