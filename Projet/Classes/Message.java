public class Message{

   int id_message;
   String message_content;
   int id_sender;
   int id_receiver;
   
   
   public int getMessageID(){
      return this.id_message;
   }
   
   public void setMessageContent(String new_message_content){
      this.message_content = new_message_content;
   }
   
   public String getMessageContent(){
      return this.message_content;
   }
   
   public int getReceiver(){
      return this.id_receiver;
   }
   
   public void setReceiver(int id_user){
      this.id_receiver = id_user;
   }
   
   public int getSender(){
      return this.id_sender;
   }
   
   public void setSender(int id_user){
      this.id_sender = id_user;
   }
   
}