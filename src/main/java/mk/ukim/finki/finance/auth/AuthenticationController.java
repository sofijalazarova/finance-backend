package mk.ukim.finki.finance.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.finance.service.RefreshTokenService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        this.authenticationService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

//    @PostMapping("login")
//    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthenticationRequest authenticationRequest){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//        if(authentication.isAuthenticated()){
//            //RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticationRequest.getEmail());
//            var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
//            return JwtResponseDto.builder()
//                    .accessToken(jwtService.generateAccessToken(user))
//                    .refreshToken(jwtService.generateRefreshToken(user))
//                    .build();
//
//        } else {
//            throw new UsernameNotFoundException("invalid user request..!!");
//        }
//    }



    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> AuthenticateAndGetToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {

        AuthenticationResponse authenticationResponse = this.authenticationService.authenticate(authenticationRequest);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", authenticationResponse.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .path("/api/auth/refresh")
                    .maxAge(Duration.ofDays(7))
                    .sameSite("Strict")
                    .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok(authenticationResponse);
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing.");
        }

        try {
            String userEmail = jwtService.extractUsername(refreshToken);

            if (userEmail != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(refreshToken, userDetails)) {
                    String newAccessToken = jwtService.generateAccessToken(userDetails);

                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("accessToken", newAccessToken);

                    return ResponseEntity.ok(tokens);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token refresh failed: " + e.getMessage());
        }
    }


//    @GetMapping("google")
//    public JwtResponseDto googleLoginSuccess(HttpServletRequest request){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if(authentication == null || !authentication.isAuthenticated()){
//            throw new UsernameNotFoundException("invalid user request..!!");
//        }
//
//        User user = new User(authentication.getPrincipal());
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
//
//        return JwtResponseDto.builder()
//                .accessToken(jtwService.generateToken(user))
//                .refreshToken(refreshToken.getToken())
//                .build();
//    }



    @PostMapping("logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok("Logged out successfully...");
    }


    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user);
    }

}
