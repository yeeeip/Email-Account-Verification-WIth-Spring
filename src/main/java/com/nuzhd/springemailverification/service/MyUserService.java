package com.nuzhd.springemailverification.service;

import com.nuzhd.springemailverification.model.ConfirmationToken;
import com.nuzhd.springemailverification.model.MyUser;
import com.nuzhd.springemailverification.repo.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MyUserService implements UserDetailsService {

    private final MyUserRepository myUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return myUserRepository.findByEmail(email).
                orElseThrow(() ->
                        new UsernameNotFoundException("User not found!"));
    }

    public String signUpUser(MyUser user) {
        MyUser foundUser = myUserRepository.findByEmail(user.getEmail())
                .orElse(null);


        if (foundUser != null) {
            if (foundUser.getFirstName().equals(user.getFirstName()) &&
                    foundUser.getLastName().equals(user.getLastName()) &&
                    foundUser.getEmail().equals(user.getEmail())) {

                ConfirmationToken userToken = tokenService.findByUserId(foundUser.getId())
                        .orElseThrow(() -> new IllegalStateException("User doesn't exist!"));

                userToken.setExpiresAt(LocalDateTime.now()
                        .plusMinutes(15L));

                String encodedPassword = passwordEncoder.encode(user.getPassword());
                foundUser.setPassword(encodedPassword);

                myUserRepository.save(foundUser);
                tokenService.saveToken(userToken);

                return userToken.getToken();
            }

            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        myUserRepository.save(user);

        ConfirmationToken token = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        tokenService.saveToken(token);

        return token.getToken();
    }

    @Async
    public int enableAppUser(String email) {
        return myUserRepository.enableAppUser(email);
    }
}
