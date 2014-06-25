package de.techdev.trackr.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Moritz Schulze
 */
@Configuration
public class OAuth2ServerConfiguration {

    public static final String TRACKR_RESOURCE_ID = "techdev-services";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(TRACKR_RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers("/api/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/**").access("#oauth2.hasScope('read')");
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory().withClient("trackr-page")
                .resourceIds(TRACKR_RESOURCE_ID)
                .authorizedGrantTypes("authorization_code", "implicit") //TODO: what to set here?
                .authorities("ROLE_CLIENT")
                .scopes("read", "write");
        }
    }
}
