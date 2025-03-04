package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
            Javalin app = Javalin.create();
            app.post("/register", this::registerUserHandler);
            app.post("/login", this::loginUserHandler);
            app.post("/messages", this::postNewMessageHandler);
            app.get("/messages", this::getAllMessagesHandler);
            app.get("/messages/{message_id}", this::getMessageByIdHandler);
            app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
            app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
            app.get("/accounts/{account_id}/messages", this::getAllUserMessagesByIdHandler);
    
        return app;
    }

    /**
     * Register new user 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUserHandler(Context context)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount == null) context.status(400);
        else{
            context.json(mapper.writeValueAsString(addedAccount)).status(200);
        }
    }   
    /**
    * Gets user login
    * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
   private void loginUserHandler(Context context) throws JsonProcessingException {
       ObjectMapper mapper = new ObjectMapper();
       Account account = mapper.readValue(context.body(), Account.class);
       Account existingAccount = accountService.getAccountByUser(account);
       if(existingAccount == null) context.status(401);
       else{
        context.json(mapper.writeValueAsString(existingAccount)).status(200);
       }
   }    
   /**
   * Creates a new message.
   * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message postedMessage = messageService.createMessage(message);
        if(postedMessage == null) context.status(400);
        else{
            context.json(mapper.writeValueAsString(postedMessage)).status(200);
        }
    }    
    /**
     * Gets all messages stored
    * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages).status(200);
    }   
    /**
     * Gets a message by an id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
    
        if(message != null) context.json(message);
        context.status(200);
    }    
    /**
    * Deletes a message by message id
    * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
    
        if(message != null) context.json(message);
        context.status(200);
    
    }
    /**
    * Updates message text by message id
    * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
       
        message.setMessage_id(Integer.parseInt(context.pathParam("message_id")));

        Message updatedMessage = messageService.updateMessage(message);
    
        if(updatedMessage != null) {
            context.json(updatedMessage);
            context.status(200);
        } else{
            context.status(400);
        }
    }   
    /**
    * Gets all messages that were posted by a specific user.
    * @param context The Javalin Context object manages information about both the HTTP request and response.
    */
    private void getAllUserMessagesByIdHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByUser(accountId);
        context.json(messages).status(200);
    }

}