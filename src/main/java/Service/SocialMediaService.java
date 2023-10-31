package Service;

import java.util.List;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;
public class SocialMediaService {
    SocialMediaDAO socialDAO;

    //Constructor
    public SocialMediaService(){
        socialDAO = new SocialMediaDAO();
    }
    
    //GET methods
    public List<Message> getAllMessages() {
        return socialDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return socialDAO.getMessageById(id);
    }

    public List<Message> getMessagesByUser(int id) {
        return socialDAO.getMessagesByUser(id);
    }
    
    //POST methods
    public Account addAccount(Account account) {
        return socialDAO.addAccount(account);
    }

    public Account logAccount(Account account) {
        return socialDAO.logAccount(account);
    }

    public Message postMessage(Message message) {
        return socialDAO.postMessage(message);
    }

    //PATCH methods
    public Message patchMessage(Message message) {
        return socialDAO.patchMessage(message);
    }

    //DELETE methods
    public Message deleteMessage(int id) {
        return socialDAO.deleteMessage(id);
    }
}
