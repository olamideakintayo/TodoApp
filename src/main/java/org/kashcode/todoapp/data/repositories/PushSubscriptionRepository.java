package org.kashcode.todoapp.data.repositories;

import org.kashcode.todoapp.data.models.PushSubscription;
import org.kashcode.todoapp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushSubscriptionRepository extends JpaRepository<PushSubscription, Long> {


    List<PushSubscription> findByUser(User user);


    PushSubscription findByEndpoint(String endpoint);
}
