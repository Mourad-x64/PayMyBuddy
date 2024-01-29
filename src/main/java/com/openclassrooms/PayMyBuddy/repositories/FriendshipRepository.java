package com.openclassrooms.PayMyBuddy.repositories;

import com.openclassrooms.PayMyBuddy.model.Friendship;
import com.openclassrooms.PayMyBuddy.model.Transaction;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> getBySrc(User user);

}
