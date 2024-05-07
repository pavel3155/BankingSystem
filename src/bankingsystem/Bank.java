package bankingsystem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class Bank {
    
    static  Map<Integer, Client> clients = new HashMap<Integer, Client>();
    static List <Deposit> depAccounts = new ArrayList<>();
    static List<OperAccount> operations = new ArrayList<>();

    public Bank() {
        InOutFiles.loadClientsFromFile();
        InOutFiles.loadDepositAccountsFromFile();
        InOutFiles.loadHistoryTransactionsAccountsFromFile();
    }
    static  public void clientAdd(Client client){
        Integer key=client.getID();
        Client addClient = clients.putIfAbsent(key,client);
        if (addClient==null) System.out.println("Клиент: "+ client +" добавлен");
        else System.out.println("Клиент: "+ addClient +" уже есть в списке");
        
    }
    static  public void clientLoad(Client client){
        Integer key=client.getID();
        Client loadClient = clients.putIfAbsent(key,client);
        if (loadClient==null) System.out.println("Клиент: "+ client +" загружен");
        else System.out.println("Клиент: "+ loadClient +" не загружен (уже есть в списке)");
    }
    public Integer GetId(){
        Random r=new Random();
         float fId = 10000*r.nextFloat();
         Integer id=(int)fId;
            
        //Integer id=r.nextInt(1000,10000); - нет в jdk15

    return id;
    }

}
