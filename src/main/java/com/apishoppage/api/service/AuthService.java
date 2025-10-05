package com.apishoppage.api.service;

import com.apishoppage.api.config.jwtconfig.JwtTokenGenerator;
import com.apishoppage.api.dto.AuthResponseDto;
import com.apishoppage.api.dto.TokenType;
import com.apishoppage.api.entity.RefreshTokenEntity;
import com.apishoppage.api.repository.RefreshTokenRepo;
import com.apishoppage.api.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.apishoppage.api.entity.User;

import java.util.Arrays;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    //private static final Logger log = LoggerFactory.getLogger(AuthService.class); instead of using @Slf4j notation

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepo refreshTokenRepo;

    public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response) {
            try
            {
                /*
                Just debug code
                String logInfo = authentication.getName();
                log.info("Looking for user with email: '{}'", logInfo);
                log.info("Authentication type: {}", authentication.getClass().getSimpleName());
                */

                var user = userRepository.findByUserName(authentication.getName())
                        .orElseThrow(()->{
                            log.error("[AuthService:userSignInAuth] User :{} not found",authentication.getName());
                            return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND ");
                        });


                String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
                String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
                //Let's save the refreshToken as well
                saveUserRefreshToken(user,refreshToken);
                creatRefreshTokenCookie(response,refreshToken);


            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",user.getUserName());
            return  AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(user.getUserName())
                    .tokenType(TokenType.Bearer)
                    .build();


        }catch (Exception e){
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
        }
    }


    private void saveUserRefreshToken(User user, String refreshToken) {
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    private Cookie creatRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60 );
        response.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }


    public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {

        if(!authorizationHeader.startsWith(TokenType.Bearer.name())){
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please verify your token type");
        }

        final String refreshToken = authorizationHeader.substring(7);

        //Find refreshToken from database and should not be revoked : Same thing can be done through filter.
        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(tokens-> !tokens.isRevoked())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Refresh token revoked"));

        User user = refreshTokenEntity.getUser();

        //Now create the Authentication object
        Authentication authentication =  createAuthenticationObject(user);

        //Use the authentication object to generate new accessToken as the Authentication object that we will have may not contain correct role.
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return  AuthResponseDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .userName(user.getUserName())
                .tokenType(TokenType.Bearer)
                .build();
    }

    private static Authentication createAuthenticationObject(User user) {
        // Extract user details from UserDetailsEntity
        String username = user.getEmail();
        String password = user.getPassword();
        String roles = user.getRoles();

        // Extract authorities from roles (comma-separated)
        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
                .map(role -> (GrantedAuthority) role::trim)
                .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }

}
