package mk.ukim.finki.finance.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.finance.repository.ConnectedAccountRepository;
import mk.ukim.finki.finance.repository.UserRepository;
import mk.ukim.finki.finance.service.RefreshTokenService;
import mk.ukim.finki.finance.user.User;
import mk.ukim.finki.finance.user.UserConnectedAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ConnectedAccountRepository connectedAccountRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public Oauth2LoginSuccessHandler(ConnectedAccountRepository connectedAccountRepository, UserRepository userRepository, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.connectedAccountRepository = connectedAccountRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String provider = authenticationToken.getAuthorizedClientRegistrationId();
        String providerId = authentication.getName();
        String email = authenticationToken.getPrincipal().getAttribute("email");


        Optional<UserConnectedAccount> connectedAccount = connectedAccountRepository.findByProviderAndProviderId(provider, providerId);

        if(connectedAccount.isPresent()) {
            authenticateUser(connectedAccount.get().getUser(), response);

            response.sendRedirect("http://localhost:3000/dashboard");
            return;
        }

        User existingUser = userRepository.findByEmail(email).orElse(null);
        if(existingUser != null) {

            UserConnectedAccount newConnectedAccount = new UserConnectedAccount(provider, providerId, existingUser);
            existingUser.addConnectedAccount(newConnectedAccount);
            existingUser = userRepository.save(existingUser);
            connectedAccountRepository.save(newConnectedAccount);
            authenticateUser(existingUser, response);

        }else {
            User newUser = createUserFromOauth2User(authenticationToken);
            authenticateUser(newUser, response);

        }

        response.sendRedirect("http://localhost:3000/dashboard");
    }

    private void authenticateUser(User user, HttpServletResponse response) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private User createUserFromOauth2User(OAuth2AuthenticationToken authentication) {
        User user = new User(authentication.getPrincipal());
        System.out.println(user.getEmail());
        String provider = authentication.getAuthorizedClientRegistrationId();

        String providerId = authentication.getName();

        UserConnectedAccount userConnectedAccount = new UserConnectedAccount(provider, providerId, user);
        user.addConnectedAccount(userConnectedAccount);
        user = userRepository.save(user);
        connectedAccountRepository.save(userConnectedAccount);
        return user;
    }

}
