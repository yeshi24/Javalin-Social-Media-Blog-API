package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Service.AccountService;
import Model.Account;
import Service.MessageService;
import Model.Message;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private AccountService accountService;
    private MessageService messageService;


public SocialMediaController()
    {
       this.accountService=new AccountService(); 
       this.messageService= new MessageService();
    }


public Javalin startAPI() {
        Javalin app = Javalin.create();
        /*to test on the client like postman use the respective url given below*/
        
        //POST http://localhost:8080/register 
        app.post("/register", this::postCreateAccountHandler);
        
        //POST http://localhost:8080/login
        app.post("/login", this::getLoginHandler);
        
        //POST http://localhost:8080/messages
        app.post("/messages", this::postCreateMessageHandler);
       
        //GET http://localhost:8080/messages
        app.get("/messages", this::getAllMessagesHandler);

        //GET http://localhost:8080/messages/{message_id}
        app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);

        //DELETE http://localhost:8080/messages/{message_id}
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        //PATCH http://localhost:8080/mesages/{message_id}
        app.patch("/messages/{message_id}", this::updateMesageHandler);

        //GET http://localhost:8080/accounts/{account_id/messages}
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountId);
        return app;
    }

/**
     
 * @param context The Javalin Context object manages information about both the HTTP request and response.
 */

/*This method is a handler for /login endpont of POST method*/
   private void getLoginHandler(Context context) throws JsonProcessingException
   {
        ObjectMapper mapper= new ObjectMapper();
        Account account=mapper.readValue(context.body(),Account.class);
        Account accountLogged= accountService.verifylogin(account);
        if(accountLogged!=null)
        {
            context.json(accountLogged);
            context.status(200);
        }else 
        {
            context.status(401);
        }
            
    }

/*This method is a handler for /register endpont of POST method*/
   private void postCreateAccountHandler(Context context) throws JsonProcessingException
   {
        ObjectMapper mapper = new ObjectMapper();
        Account account= mapper.readValue(context.body(),Account.class);
        Account addedAccount=accountService.addAccount(account);
       
        if(addedAccount!=null)
         {
            context.json(addedAccount);
            context.status(200);
         }
         else{
            context.status(400);
         }
    }

/*This method is a handler for /messages endpont of POST method*/
   private void postCreateMessageHandler(Context context)throws JsonProcessingException
   {
        try
        {
            ObjectMapper mapper= new ObjectMapper();
            Message message = mapper.readValue(context.body(),Message.class);
            Message addedMessage= messageService.addMessage(message);
            
            if(addedMessage!=null)
            {
                System.out.println(addedMessage);
                context.json(addedMessage);
                context.status(200);
            }
            else
               context.status(400);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }      
          
    }

/*This method is a handler for /messages endpont of GET method*/    
   private void  getAllMessagesHandler(Context context)
   {
       try
       {
            List<Message> messages= this.messageService.getAllMessages();  
            context.json(messages);
            context.status(200);
        }
       catch(Exception e)
       {
            e.printStackTrace();
        }      
   }

 /*This method is a handler for /messages/{message_id} endpont of GET method*/    
  private void getMessageByMessageIdHandler(Context context)
    {
        int messageId=Integer.valueOf(context.pathParam("message_id"));
        Message message=this.messageService.getMessageByMessageId(messageId);
        if(message!=null)
         {
            context.json(message);
            context.status(200);
         }
        else
            context.status(200);
    }

/*This method is a handler for /messages/{mesage_id} endpont of DELETE method*/ 
  private void deleteMessageHandler(Context context)
   {
       int messageId= Integer.valueOf(context.pathParam("message_id"));
       Message message=this.messageService.deleteMessage(messageId);
      
       if(message!=null)
        {  
            context.json(message);
            context.status(200);
        }
        else
            context.status(200);
          
    }

/*This method is a handler for /messages/{mesage_id} endpont of PATCH method*/ 
   private void updateMesageHandler(Context context) throws JsonProcessingException
   {
       ObjectMapper mapper= new ObjectMapper();
       Message message=mapper.readValue(context.body(),Message.class);
       int messageId=Integer.valueOf(context.pathParam("message_id"));
       try
       {
            Message messageUpdated=this.messageService.updateMessage(message.getMessage_text(),messageId);
          if(messageUpdated!=null)
            {
               context.json(messageUpdated);
               context.status(200);
            }
          else
               context.status(400);
       }
       catch(Exception e)
       {
        e.printStackTrace();
       }  
        
    }
/*This method is a handler for /accounts/{account_id}messages/ endpont of GET method*/ 
   private void getMessagesByAccountId(Context context)
   {
      int accountId=Integer.valueOf(context.pathParam("account_id"));
      List<Message> messages=this.messageService.getMessageByAccountId(accountId);
      context.json(messages);
      context.status(200);
    
    }
}