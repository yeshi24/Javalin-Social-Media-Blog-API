package Service;
import DAO.MessageDao;
import Model.Message;
import DAO.AccountDao;
import java.util.List;

public class MessageService 
{
  private MessageDao messageDao;
  private AccountDao accountDao;
   
  public MessageService()
  {
      this.messageDao= new MessageDao();
      this.accountDao= new AccountDao();
  }
   
  public MessageService(MessageDao messageDao)
   {
      this.messageDao=messageDao;
   }

  public MessageService(AccountDao accountDao)
   {
      this.accountDao=accountDao;
   }

  public MessageService(MessageDao messageDao,AccountDao accountDao)
    {
      this.messageDao=messageDao;
      this.accountDao=accountDao;
    }

 /*
 This method is used for creating new message. It checks data validity before calling the 
 inserMessage() of the MessageDao class object and return the result(Message object if successfull 
 otherwise null);
*/
  public Message addMessage(Message message)
    {
   
      Message messageAdded=null;
      try
        {
          //Checking the value of the text message for not empty and for not over 255 its length
          if(message.getMessage_text()!=null && message.getMessage_text()!="" && !message.getMessage_text().isEmpty())
           { 
              if(message.getMessage_text().length()<=255)
               {
                 if( this.accountDao.isUserExist(message.posted_by))
                   {
                      messageAdded=messageDao.insertMessage(message);
                      return messageAdded;     
                   }
                }  
            }  

        }
      catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;
   }

/*
 This method is to retrieve all available messages. It does nothing , 
 just calling the getAllMessages() method of MessageDao class object and return the result(list of messages 
 if records are found otherwise null).
*/
  public List<Message> getAllMessages()
   {
     return this.messageDao.getAllMessages();
    
   }

/*This method is to retrieve a single message from message table filtering by its primary key(message_id).
 It does nothing except calling the getMessageByMessageId() method of MessageDao object and return the 
 result(Message object if message exist, null otherwise)
*/
  public Message getMessageByMessageId(int messageId)
   {
     return messageDao.getMessageByMessageId(messageId);
   }

/*
  This method is to retrieve all  messages available for specified user. It does nothing except calling the 
  getMessageByAccountId() method of the Messagedao class object and return result(list of messages 
  if meessages found, null otherwise) 
*/
  public List<Message> getMessageByAccountId(int accountId)
   {
     return messageDao.getMessageByAccountId(accountId);
   }

/* This method is for deleting a message for specified message id. If number of rows affected are greater than
   zero(if the messaage to be deleted is existed), the deleted message objet is returned otherwise null is 
   returned.
 */
  public Message deleteMessage(int messageId)
   {
      return this.messageDao.getMessageByMessageId(messageId);
      
   }

/* This method is to update a message for specified message id and if number of rows affected are greater than
   zero(if the messaage to be updated is existed), the updated message objet is returned, otherwise null is 
   returned.
*/
  public Message updateMessage(String message, int messageId)
    {
       //Checking the value of the text message for not empty and for less or equal of its length
       if(message!=null && message!="" && message.length()<=255)
        {
           if( this.messageDao.isMessageIdExist(messageId))
            {
               return messageDao.updateMessage(message,messageId);
            }     
       }
   
    return null;
       
   }

}
