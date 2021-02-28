package ua.Nazar.Rep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.Nazar.Rep.domain.Role;
import ua.Nazar.Rep.domain.User;
import ua.Nazar.Rep.repos.UserRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;

    /**
     * @param userRepo
     */
    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private MailSender mailSender;

    /**
     * @param mailSender
     */
    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }


    private PasswordEncoder passwordEncoder;

    /**
     * @param passwordEncoder
     */
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${hostUEL}")
    private String hostURL;

    /**
     * @param username
     * @return User by username
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    /**
     * @param user
     * @return is user not exist in database and added successfully
     */
    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        sendFullMessage(user, user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return true;
    }

    /**
     * @param user
     */
    private void sendActivationMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n\n" +
                            "Welcome to our Weather monitoring team.\n\n" +
                            "Please, visit next link for your account activation: \n" +
                            "%s/activate/%s",
                    user.getUsername(),
                    hostURL,
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    /**
     * @param user
     * @param oldUsername
     * @param password
     */
    private void sendDataMessage(User user, String oldUsername, String password) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n\n" +
                            "Your account data was changed:\n\n" +
                            "Username:\n%s\n" +
                            "Password:\n%s\n",
                    oldUsername,
                    user.getUsername(),
                    password
            );

            mailSender.send(user.getEmail(), "Data Changed", message);
        }
    }

    /**
     * @param user
     * @param password
     */
    private void sendFullMessage(User user, String password) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n\n" +
                            "Welcome to our Weather monitoring team.\n" +
                            "Your account data is:\n\n" +
                            "Username:\n%s\n" +
                            "Password:\n%s\n\n" +
                            "Please, visit next link for your account activation: \n" +
                            "%s/activate/%s",
                    user.getUsername(),
                    user.getUsername(),
                    password,
                    hostURL,
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    /**
     * @param code
     * @return user!=null and successfully antivated
     */
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);

        return true;
    }

    /**
     * @return List of all users
     */
    public List<User> findAll() {
        return userRepo.findAll();
    }

    /**
     * @param user
     * @param username
     * @param password
     * @param email
     * @param form
     */
    public void saveUser(User user,
                         String username,
                         String password,
                         String email,
                         Map<String, String> form
    ) {

        String userEmail = user.getEmail();
        String userUsername = user.getUsername();

        boolean isEmailChanged =
                ((email != null && !email.equals(userEmail)) ||
                        (userEmail != null && !userEmail.equals(email))
                ) && !StringUtils.isEmpty(email);

        boolean isUsernameChanged =
                (username != null && !username.equals((userUsername)) ||
                        userUsername != null && !userUsername.equals(username)
                ) && !StringUtils.isEmpty(username);
        if (!StringUtils.isEmpty(username)) {
            user.setUsername(username);
        }

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        if (isUsernameChanged || !StringUtils.isEmpty(password)) {
            if (isEmailChanged) {
                sendFullMessage(user, password);
            } else {
                sendDataMessage(user, userUsername, password);
            }
        }

    }

    /**
     * @param user
     * @param password
     * @param email
     */
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged =
                ((email != null && !email.equals(userEmail)) ||
                        (userEmail != null && !userEmail.equals(email))
                ) && !StringUtils.isEmpty(email);

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);
        if (isEmailChanged) {
            if (!StringUtils.isEmpty(password)) {
                sendFullMessage(user, password);
            } else {
                sendActivationMessage(user);
            }
        } else if (!StringUtils.isEmpty(password)) {
            sendDataMessage(user, user.getUsername(), password);
        }
    }
}
