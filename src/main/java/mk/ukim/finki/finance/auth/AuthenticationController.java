package mk.ukim.finki.finance.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.finance.repository.UserRepository;
import mk.ukim.finki.finance.service.RefreshTokenService;
import mk.ukim.finki.finance.user.User;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jtwService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(this.authenticationService.register(registerRequest));
    }

    @PostMapping("login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticationRequest.getEmail());
            var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
            return JwtResponseDto.builder()
                    .accessToken(jtwService.generateToken(user))
                    .refreshToken(refreshToken.getToken())
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
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

    @PostMapping("refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequestDto request){
        this.refreshTokenService.invalidateRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok("Refresh Token successfully deleted. Logged out successfully...");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(user);
    }

}
