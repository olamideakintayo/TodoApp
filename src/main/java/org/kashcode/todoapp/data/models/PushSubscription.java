package org.kashcode.todoapp.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "push_subscriptions")
public class PushSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String endpoint;

    @Column(nullable = false, length = 255)
    private String publicKey;

    @Column(nullable = false, length = 255)
    private String authSecret;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
