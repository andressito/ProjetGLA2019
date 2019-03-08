package gl.chuchufly.restfull.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
	private int id_message;
	private String message_content;
	private int id_sender;
	private int id_receiver;
	// This default constructor is required if there are other constructors.
    public Message() {
 
    }
    public Message(int id_message, String message_content, int id_sender, int id_receiver) {
		this.id_message = id_message;
		this.message_content = message_content;
		this.id_sender = id_sender;
		this.id_receiver = id_receiver;
	}
	public int getId_message() {
		return id_message;
	}
	public void setId_message(int id_message) {
		this.id_message = id_message;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public int getId_sender() {
		return id_sender;
	}
	public void setId_sender(int id_sender) {
		this.id_sender = id_sender;
	}
	public int getId_receiver() {
		return id_receiver;
	}
	public void setId_receiver(int id_receiver) {
		this.id_receiver = id_receiver;
	}
    
}
