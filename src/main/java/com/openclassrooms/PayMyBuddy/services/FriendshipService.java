package com.openclassrooms.PayMyBuddy.services;

import com.openclassrooms.PayMyBuddy.model.Friendship;

import com.openclassrooms.PayMyBuddy.repositories.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }


    public void deleteFriendship(Long id) {
        friendshipRepository.deleteById(id);
    }

    public Friendship save(Friendship friendship){

        return friendshipRepository.save(friendship);
    }


}
