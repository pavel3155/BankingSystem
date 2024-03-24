package bankingsystem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RegClentBank {
    Scanner sc;
    String name;
    String surname;
    String log;
    String pass;

    public  RegClentBank(Scanner sc) {
        this.sc = sc;
        sc.nextLine();
        System.out.println("Регистрация:");
        System.out.println("Введите Имя:");
        if (sc.hasNextLine()) {
            name = sc.nextLine();
            System.out.println("Введите Фамилию:");
            surname = sc.nextLine();
            System.out.println("Введите логин:");
            log = sc.nextLine();
            System.out.println("Введите пароль:");
            pass = sc.nextLine();
        }
        clientAdd();
    }
    public void clientAdd(){
        Client newClient=new Client(name,surname,log,pass);
        Bank.clientAdd(newClient);
        try (FileWriter writer = new FileWriter("ClientsBank.txt", true))
        {
            // запись всей строки
            writer.write(newClient.getID()+":"
                            +newClient.getName()+":"
                            +newClient.getSurname()+":"
                            +log+":"+pass+":"
                            +newClient.curAccount.getType()+":"
                            +newClient.curAccount.getNumAccount()+":"+
                            +newClient.curAccount.getBalance()+"\n");
        }

        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
    }
}