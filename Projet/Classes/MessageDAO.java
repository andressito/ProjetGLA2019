import java.util.ArrayList;

public interface MessageDAO{

   void addMessage();
   void deleteMessage(int id_message);
   ArrayList<Message> getUserMessage(int id_user);
   Message getMessageDetails(int id_message);

}