package com.nuzhd.springemailverification.service;

import com.nuzhd.springemailverification.model.ConfirmationToken;
import com.nuzhd.springemailverification.model.MyUser;
import com.nuzhd.springemailverification.repo.MyUserRepository;
import lombok.AllArgsConstructor;
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
        boolean userExists = myUserRepository.findByEmail(user.getEmail())
                .isPresent();

        if (userExists) {

            // If all attributes are the same, create new token and send confirmation email

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

        // TODO: Send Email

        return token.getToken();
    }

    public int enableAppUser(String email) {
        return myUserRepository.enableAppUser(email);
    }
}
