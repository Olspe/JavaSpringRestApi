package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    @Autowired
    public MessageService messageService;

    @Autowired
    public AccountService accountService;
    
    //GET Methods
    
    /**
     * gets all the messages in the database
     * @return ResponseEntity<List<Message>> containing a list of all messages in the database
     */
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.findAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }


    /**
     * gets a message from the database
     * @param message_id id of the message record in the message table
     * @return ResponseEntity<Message> containing the message accessed
     */
    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        Message result = messageService.findMessageById(message_id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * gets an account's messages from the database
     * @param account_id id of account whose messages we want
     * @return ResponseEntity<List<Message>> containing a list of messages related to the account id
     */
    @GetMapping("accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int account_id){
        List<Message> result = messageService.findMessagesByAccount(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    

    //POST Methods
    
    /**
     * adds account to the database
     * @param account Account to be added to account table
     * @return ResponseEntity<Account> containing account that was added (along with its id)
     */
    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account result = accountService.addAccount(account);
        if(result != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }


    /**
     * logs into account in database
     * @param account Account to be logged into
     * @return ResponseEntity<Account> containing account logged into
     */
    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account result = accountService.logAccount(account);
        if(result != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);

        }
            
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    /**
     * adds a message to the database
     * @param message Message object to add to message table
     * @return ResponseEntity<Message> containing message that was added (along with its id)
     */
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message result = messageService.addMessage(message);
        if(result != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    //PATCH Methods

    /**
     * updates a messsage in the database
     * @param message Message object containg message_text to update with
     * @param id id of message record to change in message table
     * @return ResponeEntity<Integer> of number of rows that were affected
     */
    @PatchMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@RequestBody Message message, @PathVariable int id){
        message.setMessage_id(id);
        int rowsUpdated = messageService.updateMessage(message);
        if(rowsUpdated == 1){
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
            
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rowsUpdated);
            
    }    
    


    //DELETE Methods

    /**
     * deletes a message in the database
     * @param message_id id of message to delete
     * @return ResponeEntity<Integer> of number of rows that were affected
     */
    @DeleteMapping("messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deletePost(@PathVariable int message_id){
        int rowsUpdated = messageService.deleteMessage(message_id);
        if(rowsUpdated == 1){
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
            
        }
        return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
            
    }    
}
