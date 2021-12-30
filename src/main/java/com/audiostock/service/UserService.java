package com.audiostock.service;

import com.audiostock.entities.PaymentInfo;
import com.audiostock.entities.Status;
import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.StatusRepo;
import com.audiostock.repos.UserRepo;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.util.ChangeProfileInfoReport;
import com.audiostock.service.util.CheckoutFailureReason;
import com.audiostock.service.util.CheckoutReport;
import com.audiostock.service.util.RegisterReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final StatusRepo statusRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepo userRepo, StatusRepo statusRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.statusRepo = statusRepo;
        this.encoder = encoder;
    }

    // Authorisation

    public RegisterReport register(String username, String password, String repeat) {
        if (username.length() < 4) {
            return new RegisterReport(false, "Username must have at least 4 symbols");
        }

        if (password.length() < 6) {
            return new RegisterReport(false, "Password must have at least 6 symbols");
        }

        if (!password.equals(repeat)) {
            return new RegisterReport(false, "Passwords don't match");
        }

        Optional<User> userWithSameName = userRepo.findByLogin(username);
        if (userWithSameName.isPresent()) {
            return new RegisterReport(false, "Login is already taken");
        }

        final Status status = statusRepo.findById(1L)
                .orElseThrow(() -> new IllegalStateException("There are no statuses in db"));
        userRepo.save(new User(username, encoder.encode(password), status));

        return new RegisterReport(true);
    }

    // Representation

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepo.findByLogin(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    /**
     * Вывод всех авторов, сортируя по имени пользователя (логину)
     */
    public List<User> getAuthorsSortedByUsername(int page, int size) {
        return userRepo.findAll(PageRequest.of(page, size).withSort(Sort.by("login"))).getContent();
    }

    /**
     * Поиск авторов по имени пользователя (логину). Проверяет, содержится ли строка в имени автора
     * @param username Никнейм автора
     */
    public List<User> findAuthorsByUsername(String username, int page, int size) {
        Page<User> allAuthors = userRepo.findAll(PageRequest.of(page, size).withSort(Sort.by("login")));
        return allAuthors.get()
                .filter((user) -> user.getLogin().contains(username))
                .collect(Collectors.toList());
    }

    public List<Track> getReleasesSortedByName(User user) {
        return makeASortedList(user.getReleases());
    }

    public List<Track> getCartSortedByName(User user) {
        return makeASortedList(user.getCart());
    }

    public List<Track> getFavoriteSortedByName(User user) {
        return makeASortedList(user.getFavorites());
    }

    private List<Track> makeASortedList(Set<Track> set) {
        return set.stream()
                .sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()))
                .collect(Collectors.toList());
    }

    public List<User> getAllModerators() {
        return userRepo.findAllByStatusName("MODERATOR");
    }

    // Consumer side

    public boolean addTrackToCart(User user, Track track) {
        if (user.getCart().contains(track)) {
            System.out.println(user.getLogin() + " already has track " + track.getName() + " in the cart");
            return false;
        }

        user.getCart().add(track);
        userRepo.save(user);
        return true;
    }

    public boolean removeTrackFromCart(User user, Track track) {
        if (!user.getCart().contains(track)) {
            System.out.println(user.getLogin() + " doesn't have track " + track.getName() + " in the cart");
            return false;
        }

        user.getCart().remove(track);
        userRepo.save(user);
        return true;
    }

    public CheckoutReport checkout(User user) {
        final Set<Track> cart = user.getCart();
        Long totalPrice = 0L;

        for (Track track : cart) {
            if (user.getPurchased().contains(track)) {
                return new CheckoutReport(
                        false,
                        CheckoutFailureReason.TRACK_IS_ALREADY_PURCHASED,
                        user.getLogin() + " already has track " + track.getName() + " purchased"
                );
            }
            totalPrice += track.getPrice();
        }

        if (user.getBalance() < totalPrice) {
            return new CheckoutReport(
                    false,
                    CheckoutFailureReason.NOT_ENOUGH_MONEY,
                    user.getLogin() + " doesn't have enough money"
            );
        }

        for (Track track : cart) {
            user.getPurchased().add(track);
        }
        user.setBalance(user.getBalance() - totalPrice);

        userRepo.save(user);
        return new CheckoutReport(true);
    }

    public Long totalCartPrice(User user) {
        return user.getCart().stream()
                .mapToLong(Track::getPrice)
                .sum();
    }

    public void savePaymentMethod(User user, PaymentInfo paymentInfo) {
        user.setPaymentInfo(paymentInfo);
        userRepo.save(user);
    }

    public void makeDeposit(User user, Long amount) {
        user.setBalance(user.getBalance() + amount);
        userRepo.save(user);
    }

    public boolean addTrackToFavorite(User user, Track track) {
        if (user.getFavorites().contains(track)) {
            System.out.println(user.getLogin() + " already has track " + track.getName() + " in favorites");
            return false;
        }

        user.getFavorites().add(track);
        userRepo.save(user);
        return true;
    }

    public boolean removeTrackFromFavorites(User user, Track track) {
        if (!user.getFavorites().contains(track)) {
            System.out.println(user.getLogin() + " already doesn't have this track in favorites");
            return false;
        }

        user.getFavorites().remove(track);
        userRepo.save(user);
        return true;
    }

    // Author side

    public void addTrack(User user, Track track) {
        user.getReleases().add(track);
        userRepo.save(user);
    }

    public boolean removeTrack(User user, Track track) {
        if (!user.getReleases().contains(track)) {
            System.out.println(user.getLogin() + " doesn't have such track");
            return false;
        }

        user.getReleases().remove(track);
        userRepo.save(user);
        return true;
    }

    public boolean withdrawFunds(User user, Long amount) {
        if (user.getBalance() < amount) {
            System.out.println(user.getLogin() + " doesn't have enough money to withdraw");
            return false;
        }

        user.setBalance(user.getBalance() - amount);
        userRepo.save(user);
        return true;
    }

    public ChangeProfileInfoReport changeProfileInfo(
            User user,
            String firstname,
            String lastname,
            String middlename,
            String biography
    ) {
        if (firstname.length() > 30 || middlename.length() > 30 || lastname.length() > 30) {
            return new ChangeProfileInfoReport(
                    false,
                    "Name must not have more than 30 characters"
            );
        }

        if (biography.length() > 100) {
            return new ChangeProfileInfoReport(
                    false,
                    "Biography must not have more than 100 characters"
            );
        }

        user.setFirstname(firstname);
        user.setMiddlename(middlename);
        user.setLastname(lastname);
        user.setBiography(biography);

        userRepo.save(user);
        return new ChangeProfileInfoReport(true);
    }

    public ChangeProfileInfoReport changePassword(
            User user,
            String oldPassword,
            String newPassword,
            String newPasswordAgain
    ) {
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return new ChangeProfileInfoReport(false, "Wrong old password");
        }

        if (newPassword.length() < 6) {
            return new ChangeProfileInfoReport(false, "Password must have at least 6 symbols");
        }

        if (!newPassword.equals(newPasswordAgain)) {
            return new ChangeProfileInfoReport(false, "Passwords don't match");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
        return new ChangeProfileInfoReport(true);
    }

    public boolean changeProfileAvatar(User user, MultipartFile avatar) {
        try {
            user.setAvatar(avatar.getBytes());
            userRepo.save(user);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Status manipulations

    public void updateStatus(User user, Status status) {
        user.setStatus(status);
        userRepo.save(user);
    }

    public boolean ban(User user) {
        if (user.isBanned()) {
            System.out.println(user.getLogin() + " is already banned");
            return false;
        }

        user.setBanned(true);
        userRepo.save(user);
        return true;
    }

    public boolean unban(User user) {
        if (!user.isBanned()) {
            System.out.println(user.getLogin() + " is already unbanned");
            return false;
        }

        user.setBanned(false);
        userRepo.save(user);
        return true;
    }

}
