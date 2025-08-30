package com.apishoppage.api.service;

import com.apishoppage.api.config.jwtconfig.JwtTokenGenerator;
import com.apishoppage.api.dto.AuthResponseDto;
import com.apishoppage.api.dto.TokenType;
import com.apishoppage.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    //private static final Logger log = LoggerFactory.getLogger(AuthService.class); instead of using @Slf4j notation

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication) {
            try
            {
                /*
                Just debug code
                String logInfo = authentication.getName();
                log.info("Looking for user with email: '{}'", logInfo);
                log.info("Authentication type: {}", authentication.getClass().getSimpleName());
                */

                var userEntity = userRepository.findByUserName(authentication.getName())
                        .orElseThrow(()->{
                            log.error("[AuthService:userSignInAuth] User :{} not found",authentication.getName());
                            return new ResponseStatusException(HttpStatus.NOT_FOUND,"USER NOT FOUND ");
                        });


            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

            log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",userEntity.getUserName());
            return  AuthResponseDto.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(userEntity.getUserName())
                    .tokenType(TokenType.Bearer)
                    .build();


        }catch (Exception e){
            log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Please Try Again");
        }
    }
}
