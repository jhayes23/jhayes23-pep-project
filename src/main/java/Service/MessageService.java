package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message createMessage(Message message){
        if(message != null){
            String messageText = message.getMessage_text();

            if(messageText != null && !messageText.isEmpty() 
            && messageText.length() < 255){
                if(accountDAO.getAccountById(message.getPosted_by()) != null) return messageDAO.insertNewMessage(message);
            }
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){
        return messageDAO.deleteMessageById(id);
    }
    public Message updateMessage(Message message){
        if(message != null){
            String messageText = message.getMessage_text();
            if(messageText != null && !messageText.isEmpty() 
            && messageText.length() < 255){
                return messageDAO.updateMessageById(message);
            }
        }
        return null;
    }
    public List<Message> getAllMessagesByUser(int id){
        return messageDAO.getAllMessagesByUserId(id);
    }
}
