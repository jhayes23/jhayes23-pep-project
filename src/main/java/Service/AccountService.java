package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    static AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    

    public static Account addAccount(Account account){
        if(account.getUsername() == null || account.getPassword().length() < 4) return null;
        if(accountDAO.getAccountByUser(account.getUsername()) == null){
            return accountDAO.insertNewAccount(account);
        }
       return null;
    }


}
