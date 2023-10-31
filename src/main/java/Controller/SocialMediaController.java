package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService service;

    public SocialMediaController(){
        service = new SocialMediaService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //GET
        app.get("messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.get("accounts/{account_id}/messages", this::getMessagesByUser);

        // //POST
        app.post("register", this::registerAccount);
        app.post("login", this::logAccount);
        app.post("messages", this::postMessage);

        // //PATCH
        app.patch("messages/{message_id}", this::patchMessageById);
  
        // //DELETE
        app.delete("messages/{message_id}", this::deleteMessage);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }



    /**
     * get all messages in mesage table.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessages(Context context) {
        context.json(service.getAllMessages());
    }


    /**
     * get messages by message_id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageById(Context context) {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message msg = service.getMessageById(id);
        if(msg == null){
            context.status(200);
        }
        else{
            context.json(msg);
        }
    }

    
    /**
     * get messages by account id. Corresponds to posted_by in message table
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessagesByUser(Context context){
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> msg = service.getMessagesByUser(id);
        context.status(200);
        context.json(msg);
    }


    /**
     * add account in the account table
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccount(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = service.addAccount(account);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }


    /**
     * accesss account in the account table
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void logAccount(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedAccount = service.logAccount(account);
        if(loggedAccount!=null){
            context.json(mapper.writeValueAsString(loggedAccount));
        }else{
            context.status(401);
        }
    }


    /**
     * add message in the message table
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessage(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(context.body(), Message.class);
        Message postedMessage = service.postMessage(msg);
        if(postedMessage != null){
            context.json(mapper.writeValueAsString(postedMessage));
        }else{
            context.status(400);
        }

    }


    /**
     * updates message in the message table by message_id. Replaces nessage_text with new text
     * in the request body
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchMessageById(Context context) throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(context.body(), Message.class);
        msg.setMessage_id(id);
        Message patchedMessage = service.patchMessage(msg);
        if(patchedMessage != null){
            context.json(mapper.writeValueAsString(patchedMessage));
        }else{
            context.status(400);
        }
    }


    /**
     * deletes message in mesage table by message_id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessage(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message msg = service.deleteMessage(id);
        if(msg != null){
            context.json(msg);
        }
        else{
            context.status(200);
        }

    }
}