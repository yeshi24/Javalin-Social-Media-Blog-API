package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
   private AccountDao accountDao;

   public AccountService() {
      this.accountDao = new AccountDao();
   }

   public AccountService(AccountDao accountDao) {
      this.accountDao = accountDao;
   }
   /* This method is for adding/creating a new user account. It first checks if the user is already exist 
      by calling the getAccountByUsername()method of the AccountDao object.If the user is not exist/not registered
      in the database, it calls the insertAccount() method of the AccountDao object to save the user record in
      the database permanently otherwise it returns null */

   public Account addAccount(Account account) {
      /*Here password is checked for not empty and for not lesss than 4 charachters */

      if ((!account.getUsername().isEmpty()) && account.getPassword().length() >= 4) {
         if (this.accountDao.getAccountByUsername(account.getUsername()) == null)
            return this.accountDao.insertAccount(account);
         else
            return null;

      } 
      else
         return null;
   }
  /*
    This method is for user authentication by checking the validity of user name and password 
    name and password.  
 */
   public Account verifylogin(Account account) {
     
         return this.accountDao.getAccountByUsernameAndPassword(account);
        
   }
   
}
