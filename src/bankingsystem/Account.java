package bankingsystem;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.*;
public class Account implements AccountTransactions{
    int idClient;
    String numAccount;
    float balance=0;
    String type;

    public Account(int idClient,String type) {
        this.idClient = idClient;
        this.type=type;
        int n=0;
        this.numAccount = GenerateNumAccount(n);

    }

    public Account(int idClient, String type,String numAccount, float balance) {
        this.idClient = idClient;
        this.type=type;
        this.numAccount = numAccount;
        this.balance = balance;
    }

public String GenerateNumAccount(int n){
        
        if(type.equals("cur"))  numAccount="408038102001000"+idClient;
        if(type.equals("dep"))  numAccount="42303810500100"+n+idClient;
    return numAccount;
}
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void deposit(float sum) {
        this.balance+=sum;
    }

    @Override
    public void withdraw(float sum) {
        if (this.balance>sum) this.balance-=sum;
        else System.out.println("На счете не достаточно средств!!! \n Для перевода доступно: " +this.balance);
    }

    @Override
    public void transfer(float sum) {
        if (this.balance>sum) this.balance-=sum;
        else System.out.println("НЕДОСТАТОЧНО СРЕДСТВ!!! \n Для перевода доступно: " +this.balance);
    }
    
}
