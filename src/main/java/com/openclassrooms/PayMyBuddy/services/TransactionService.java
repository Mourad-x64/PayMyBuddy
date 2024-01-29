package com.openclassrooms.PayMyBuddy.services;

import com.openclassrooms.PayMyBuddy.model.Fee;
import com.openclassrooms.PayMyBuddy.model.Transaction;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserService userService){
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Transactional
    public Transaction doTransaction(TransactionDto transactionDto, String userMail){

        User friend = null;
        User user = null;
        Transaction transaction = new Transaction();
        float amount = transactionDto.getAmount();
        float commission = 0;
        float totalAmount = 0;

        Optional<User> optFriend = userService.findByEmail(transactionDto.getEmail());
        if(optFriend.isPresent()){
            friend = optFriend.get();
        }

        Optional<User> optUser = userService.findByEmail(userMail);
        if(optUser.isPresent()){
            user = optUser.get();
        }

        totalAmount = amount+(amount * Fee.FEE_RATE)/100;
        commission = (amount * Fee.FEE_RATE)/100;

        //update user balance
        user.setBalance(Math.round((user.getBalance()-totalAmount) * 100) / 100);
        userService.save(user);
        //update friend balance
        friend.setBalance(Math.round((friend.getBalance()+totalAmount) * 100) / 100);
        userService.save(friend);

        transaction.setAmount(amount);
        transaction.setCommission(commission);
        transaction.setFrom(user);
        transaction.setTo(friend);
        transaction.setDateCreated(new Date().toString());
        transaction.setDescription(transactionDto.getDescription());

        return transactionRepository.save(transaction);

    }


}
