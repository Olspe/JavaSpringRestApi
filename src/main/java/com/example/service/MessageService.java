package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
// import com.example.repository.AccountRepository;


@Service
public class MessageService {


    //Field
     MessageRepository messageRepository;


     //Field
    //  @Autowired
    //  AccountRepository accountRepository;

    //Constructor
    @Autowired 
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }


    /**
     * gets all records in message table
     * @return Returns all message records in the message table
     */
    public List<Message> findAllMessages(){
        return messageRepository.findAll();
    }


    /**
     * gets a message record by its message_id from message table
     * @param id of message record we want from message table
     * @return Returns the Message found
     */
    public Message findMessageById(int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        
        return null;
    }


    /**
     * gets all messages that are assoicated with a particular account
     * @param account_id id of account whose list messages we want 
     * @return Returns List of messages posted by the account id
     */
    public List<Message> findMessagesByAccount(int account_id){
        List<Message> messages = messageRepository.findAll();
        List<Message> accountMessages = new ArrayList<Message>();

        for(Message msg : messages){
            if(msg.getPosted_by() == account_id){
                accountMessages.add(msg);
            }
        }

        return accountMessages;
    }


    /**
     * adds a message object as a record in the message table if account already exists
     * @param message Message object to add to message table
     * @return Message object that was saved to database (contains id)
     */
    public Message addMessage(Message message){
        String msg_text = message.getMessage_text();

        if(msg_text.equals("") || msg_text.length() > 254){
            return null;
        }
        // if(!accountRepository.existsById(message.getPosted_by())){
        //     return null;
        // }

        if(messageRepository.findByposted_by(message.getPosted_by()).isEmpty()){
            return null;
        }

        //without @query
        // if(messageRepository.findMessagesByposted_by(message.getPosted_by()).isEmpty()){
        //     return null;
        // }
        return messageRepository.save(message);
    }


    /**
     * deletes a message record form the message table by its message_id
     * @param message_id id of message record to delete
     * @return Returns 1 on success and 0 on failure
     */
    public int deleteMessage(int message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        
        if(optionalMessage.isPresent()){
            messageRepository.deleteById(message_id);
            return 1;
        }
        return 0;
    }


    /**
     * updates a message's message_text in the message table
     * @param message Message object containing message_id and message_text to update
     * @return Returns 1 on success and 0 on failure
     */
    public int updateMessage(Message message){
        int message_id = message.getMessage_id();
        String msg_text = message.getMessage_text();
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        
        if(optionalMessage.isEmpty() || msg_text.equals("") || msg_text.length() > 255){
            return 0;
        }

        Message updatedMsg = optionalMessage.get();
        updatedMsg.setMessage_text(msg_text);
        messageRepository.save(updatedMsg);

        return 1;
    }
}
