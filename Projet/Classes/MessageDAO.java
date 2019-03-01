public interface MessageDAO{

   void addMessage();
   void deleteMessage(int id_message);
   List<Message> getUserMessage(int id_user);
   Message getMessageDetails(int id_message);

}