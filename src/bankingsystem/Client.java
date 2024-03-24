package bankingsystem;
import java.util.Map;
import java.util.Random;

public class Client {
    int ID;
    String name;
    String surname;
    String log,pass;
    Account curAccount;
    //Map<String,Account> depAccount;



    public Client(String name, String surname, String log, String pass) {
        this.ID = GenerateId();
        this.name = name;
        this.surname = surname;
        this.log = log;
        this.pass = pass;
        this.curAccount=new Account(this.ID, "cur");
    }
    public Client( int ID,String name, String surname, String log, String pass,Account curAccount) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.log = log;
        this.pass = pass;
        this.curAccount=curAccount;
    }
    public int GenerateId(){
        Random r=new Random();
        boolean run =true;
        int id=0;
        float fId=0;
        do{
            fId = 10000*r.nextFloat();
            id=(int)fId;
            
            //id=r.nextInt(1000,10000);
            run=Bank.clients.containsKey(id);
        } while (run);

        return id;
    }

    public String toString() {
        return "ID:= "+ID+" "+
                name+" "+
                surname+" "+
                "Текущий счет: "+curAccount.numAccount+" "+
                "Баланс: "+ curAccount.balance;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
