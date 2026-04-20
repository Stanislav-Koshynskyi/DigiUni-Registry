package service;

import entity.Role;
import entity.User;
import exception.UserDeletedException;
import exception.WrongPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import security.LocalAuthService;
import security.PasswordCoder;
import security.SessionInfo;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocalAuthServiceTest {
    @Mock
    private PasswordCoder passwordCoder;
    @Mock
    private ServiceUserInterface serviceUserInterface;
    @Mock
    private SessionInfo sessionInfo;
    @InjectMocks
    private LocalAuthService localAuthService;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(Role.USER, "asd", "asd");
    }

    @Test
    void successfulLoginTest() {
        when(serviceUserInterface.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(passwordCoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        localAuthService.login(user.getLogin(), user.getPassword());
        verify(sessionInfo).login(user);
    }

    @Test
    void loginWithIncorrectPasswordTest() {
        when(serviceUserInterface.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(passwordCoder.matches(user.getPassword(), user.getPassword())).thenReturn(false);
        Assertions.assertThrows(WrongPasswordException.class, () -> localAuthService.login(user.getLogin(), user.getPassword()));
        verify(sessionInfo, never()).login(any());
    }

    @Test
    void loginIfUserDeletedTest() {
        user.setRole(Role.DELETED);
        when(serviceUserInterface.findUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(passwordCoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        Assertions.assertThrows(UserDeletedException.class, () -> localAuthService.login(user.getLogin(), user.getPassword()));
        verify(sessionInfo, never()).login(any());
    }
}