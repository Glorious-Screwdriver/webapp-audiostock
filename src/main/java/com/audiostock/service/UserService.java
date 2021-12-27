package com.audiostock.service;

import com.audiostock.entities.Status;
import com.audiostock.entities.Track;
import com.audiostock.entities.User;
import com.audiostock.repos.StatusRepo;
import com.audiostock.repos.UserRepo;
import com.audiostock.service.exceptions.UserNotFoundException;
import com.audiostock.service.exceptions.UsernameIsAlreadyTakenException;
import com.audiostock.service.exceptions.PasswordsDoNotMatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepo userRepo;
    StatusRepo statusRepo;
    PasswordEncoder encoder;

    public UserService(UserRepo userRepo, StatusRepo statusRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.statusRepo = statusRepo;
        this.encoder = encoder;
    }

    // Authorisation

    public void register(String username, String password, String repeat)
            throws UsernameIsAlreadyTakenException, PasswordsDoNotMatchException {

        Optional<User> userWithSameName = userRepo.findByLogin(username);
        if (userWithSameName.isPresent()) {
            throw new UsernameIsAlreadyTakenException(username);
        }

        if (!password.equals(repeat)) {
            throw new PasswordsDoNotMatchException();
        }

        final Status status = statusRepo.findById(1L).orElseThrow();
        userRepo.save(new User(username, encoder.encode(password), status));
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

    // Consumer side

    public void addTrackToCart(User user, Track track) {
        user.getCart().add(track);
    }

    public boolean removeTrackFromCart(User user, Track track) {
        if (!user.getCart().contains(track)) {
            System.out.println(user.getLogin() + " doesn't have track " + track.getName() + " in the cart");
            return false;
        }

        user.getCart().remove(track);
        return true;
    }

    public boolean checkout(User user) {
        final Set<Track> cart = user.getCart();
        Long totalPrice = 0L;

        for (Track track : cart) {
            if (user.getPurchased().contains(track)) {
                System.out.println(user.getLogin() + " already has this track purchased");
                return false;
            }
            totalPrice += track.getPrice();
        }

        if (user.getBalance() < totalPrice) {
            System.out.println(user.getLogin() + " doesn't have enough money");
            return false;
        }

        for (Track track : cart) {
            user.getPurchased().add(track);
        }
        user.setBalance(user.getBalance() - totalPrice);

        return true;
    }

    public Long totalCartPrice(User user) {
        return user.getCart().stream()
                .mapToLong(Track::getPrice)
                .sum();
    }

    public void makeDeposit(User user, Long amount) {
        user.setBalance(user.getBalance() + amount);
    }

    public boolean addTrackToFavorite(User user, Track track) {
        if (user.getFavorites().contains(track)) {
            System.out.println(user.getLogin() + " already has this track in favorites");
            return false;
        }

        user.getFavorites().add(track);
        return true;
    }

    public boolean removeTrackFromFavorites(User user, Track track) {
        if (!user.getFavorites().contains(track)) {
            System.out.println(user.getLogin() + " already doesn't have this track in favorites");
            return false;
        }

        user.getFavorites().remove(track);
        return true;
    }

    // Author side

    public void addTrack(User user, Track track) {
        user.getReleases().add(track);
    }

    public boolean removeTrack(User user, Track track) {
        if (!user.getReleases().contains(track)) {
            System.out.println(user.getLogin() + " doesn't have such track");
            return false;
        }

        user.getReleases().remove(track);
        return true;
    }

    public boolean withdrawFunds(User user, Long amount) {
        if (user.getBalance() < amount) {
            System.out.println(user.getLogin() + " doesn't have enough money to withdraw");
            return false;
        }

        user.setBalance(user.getBalance() - amount);
        return true;
    }

    public boolean changeProfileInfo(
            User user,
            String firstname,
            String middlename,
            String lastname,
            String biography
    ) {
        if (firstname.length() > 30 || middlename.length() > 30 || lastname.length() > 30) {
            System.out.println("Name must not have more than 30 characters");
            return false;
        }

        if (biography.length() > 100) {
            System.out.println("Biography must not have more than 100 characters");
            return false;
        }

        user.setFirstname(firstname);
        user.setMiddlename(middlename);
        user.setLastname(lastname);
        user.setBiography(biography);

        return true;
    }

    public boolean changeProfileAvatar(User user, Blob avatar) {
        user.setAvatar(avatar);
        return true;
    }

    // Status manipulations

    public void updateStatus(User user, Status status) {
        user.setStatus(status);
    }

    public boolean ban(User user) {
        if (user.isBanned()) {
            System.out.println(user.getLogin() + " is already banned");
            return false;
        }

        user.setBanned(true);
        return true;
    }

    public boolean unban(User user) {
        if (!user.isBanned()) {
            System.out.println(user.getLogin() + " is already unbanned");
            return false;
        }

        user.setBanned(false);
        return true;
    }

}
