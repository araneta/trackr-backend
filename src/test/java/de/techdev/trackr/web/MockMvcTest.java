package de.techdev.trackr.web;

import de.techdev.trackr.TransactionalIntegrationTest;
import de.techdev.trackr.security.AuthorityMocks;
import de.techdev.trackr.security.MethodSecurityConfiguration;
import de.techdev.trackr.security.SecurityConfiguration;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.json.Json;
import javax.json.stream.JsonGeneratorFactory;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * A base class for tests that access the web mvc resources.
 *
 * @author Moritz Schulze
 */
@ContextConfiguration(classes = {MethodSecurityConfiguration.class, SecurityConfiguration.class})
public abstract class MockMvcTest extends TransactionalIntegrationTest {

    protected final String standardContentType = "application/hal+json";

    protected MockMvc mockMvc;
    protected JsonGeneratorFactory jsonGeneratorFactory;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public final void setUpMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
    }

    @Before
    public void setUpJsonGeneratorFactory() throws Exception {
        jsonGeneratorFactory = Json.createGeneratorFactory(null);
    }

    private MockHttpSession buildSession(Authentication authentication) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, new MockSecurityContext(authentication));
        return session;
    }

    /**
     * An http session for an employee.
     *
     * @param id The desired id of the employee.
     * @return The mock session object.
     */
    protected MockHttpSession employeeSession(Long id) {
        return buildSession(AuthorityMocks.employeeAuthentication(id));
    }

    /**
     * An http session for an employee with id 100.
     *
     * @return The mock session object.
     */
    protected MockHttpSession employeeSession() {
        return buildSession(AuthorityMocks.basicAuthentication());
    }

    /**
     * An http session for a supervisor.
     *
     * @return The mock session object.
     */
    protected MockHttpSession supervisorSession() {
        return buildSession(AuthorityMocks.supervisorAuthentication());
    }

    /**
     * An http session for an admin.
     *
     * @return The mock session object.
     */
    protected MockHttpSession adminSession() {
        return buildSession(AuthorityMocks.adminAuthentication());
    }

    /**
     * Mock for the security context to provide our own authentications in a MockHttpSession.
     * <p>
     * See <a href="http://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication">stackoverflow</a> for this idea.
     */
    protected class MockSecurityContext implements SecurityContext {

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
}
