package com.navoshgaran.socialnetwork.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_sequence",
            allocationSize = 1)
    private Long id;

    @NotBlank(message = "First Name is Null")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is Null")
    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email Is Null")
    @Email(message = "Invalid Email")
    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username Is Null")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password Is Empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!#*@$%&]).{8,20}$",
            message = "Wrong Pattern For Password")
    private String password;

    @Column(nullable = false)
    private LocalDateTime registerDate;

    private LocalDateTime loginDate;

    @ManyToMany(
            mappedBy = "following",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    private Set<User> followers = new HashSet<>();

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(name="friendship",
            joinColumns={@JoinColumn(name="follower_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "follower_id"})}
    )
    private Set<User> following = new HashSet<>();

    @ManyToMany(
            mappedBy = "requestReceiver",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    private Set<User> requestSender = new HashSet<>();

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(name="requests",
            joinColumns={@JoinColumn(name="sender_id")},
            inverseJoinColumns={@JoinColumn(name="receiver_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"sender_id", "receiver_id"})}
    )
    private Set<User> requestReceiver = new HashSet<>();

    public void removeFollower(User user){
        followers.remove(user);
        user.getFollowing().remove(this);
    }

    public void sendRequest(User user){
        requestReceiver.add(user);
        user.getRequestSender().add(this);
    }

    public void unSendRequest(User user){
        requestReceiver.remove(user);
        user.getRequestSender().remove(this);
    }

    public void removeFollowing(User user){
        following.remove(user);
        user.getFollowers().remove(this);
    }

    public void confirmRequest(User user){
        requestSender.remove(user);
        user.getRequestReceiver().remove(this);
        followers.add(user);
        user.getFollowing().add(this);
    }

    public void rejectRequest(User user){
        requestSender.remove(user);
        user.getRequestReceiver().remove(this);
    }

}
