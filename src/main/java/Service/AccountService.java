package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    

    public  Account addAccount(Account account){
        if(account == null || account.getUsername() == null || account.getPassword().length() < 4
        || account.getPassword() == null || account.getUsername().trim().isEmpty()) return null;

        if(accountDAO.getAccountByUser(account.getUsername()) == null){
            return accountDAO.insertNewAccount(account);
        }
       return null;
    }

    public Account getAccountByUser(Account account){
        Account userAccount = accountDAO.getAccountByUser(account.getUsername());
        if(userAccount != null){
            if(userAccount.getPassword().equals(account.getPassword())) return userAccount;
        }
        return null;
    }


}
