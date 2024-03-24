/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingsystem;

import static bankingsystem.Bank.clientLoad;
import static bankingsystem.Bank.clients;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author koval
 */
public class InOutFiles {
    
    
    static public void depAccountsInFile(){
        System.out.println("......Загрузка депозитных счетов клиентов банка......");

        try (BufferedReader br = new BufferedReader(new FileReader("depAccounts.txt"))) {
            String deposit=br.readLine();
            while (deposit!=null) {
                StringTokenizer st = new StringTokenizer(deposit, ":");
                String c[] = new String[8];
                int i = 0;
                while (st.hasMoreTokens()) {
                    c[i] = st.nextToken();
                    i++;
                }
                int id=Integer.parseInt(c[0]);
                int depN=Integer.parseInt(c[2]);
                int term=Integer.parseInt(c[4]);
                int percen=Integer.parseInt(c[5]);
                float inSum=Float.parseFloat(c[6]);
                float outSum=Float.parseFloat(c[7]);
                
               
                Deposit dep =new Deposit(id,c[1],depN,c[3],term,percen,inSum,outSum);
                Bank.depAccounts.add(dep);

                System.out.println(dep); 
                
                deposit= br.readLine();
                
            }
            System.out.println("Загружено депозитных счетов: "+Bank.depAccounts.size());
            br.close();
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
    }



    static public  void ClientBankInFile(){
        System.out.println("......Загрузка клиентов банка......");

        try (BufferedReader br = new BufferedReader(new FileReader("ClientsBank.txt"))) {
            String client=br.readLine();
            while (client!=null) {
                StringTokenizer st = new StringTokenizer(client, ":");
                String c[] = new String[8];
                int i = 0;
                while (st.hasMoreTokens()) {
                    c[i] = st.nextToken();
                    i++;
                }
                int id=Integer.parseInt(c[0]);
                float balance=Float.parseFloat(c[7]);
                Account curAccount =new Account(id,c[5],c[6],balance);
                Client Client = new Client(id,c[1],c[2],c[3],c[4],curAccount);
                clientLoad(Client);
                client = br.readLine();
            }
            System.out.println("Загружено клиентов: "+clients.size());
            br.close();
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }

    }
    static public void ClientsBankOutFile(){
        try (FileWriter writer = new FileWriter("ClientsBank.txt", false))
        {
            for(Client client : Bank.clients.values()){
            // запись всей строки
            writer.write(client.getID()+":"
                            +client.getName()+":"
                            +client.getSurname()+":"
                            +client.getLog()+":"
                            +client.getPass()+":"
                            +client.curAccount.getType()+":"
                            +client.curAccount.getNumAccount()+":"+
                            +client.curAccount.getBalance()+"\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    static public void depAccountsOutFile(){
                try (FileWriter writer = new FileWriter("depAccounts.txt", false))
        {
            for(Deposit d : Bank.depAccounts){
            // запись всей строки
            writer.write(d.idClient+":"
                            +d.num+":"
                            +d.depN+":"
                            +d.nameDep+":"
                            +d.term+":"
                            +d.percent+":"
                            +d.inSum+":"
                            +d.outSum+"\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    static public void operAccountOutFile(){
        try (FileWriter writer = new FileWriter("operAccounts.txt", false))
        {
            for(OperAccount o : Bank.operations){
                // запись всей строки
                writer.write(o.idClient+":"
                        +o.data+":"
                        +o.numAcc+":"
                        +o.oper+"\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    static public void operAccountInFile(){
        System.out.println("......Загрузка истории операций со счетами клиентов......");

        try (BufferedReader br = new BufferedReader(new FileReader("operAccounts.txt"))) {
            String oper=br.readLine();
            while (oper!=null) {
                StringTokenizer st = new StringTokenizer(oper, ":");
                String c[] = new String[4];
                int i = 0;
                while (st.hasMoreTokens()) {
                    c[i] = st.nextToken();
                    i++;
                }
                int id=Integer.parseInt(c[0]);


                OperAccount operAcc =new OperAccount(c[1],id,c[2],c[3]);
                Bank.operations.add(operAcc);

                System.out.println(operAcc);

                oper= br.readLine();

            }
            System.out.println("Загружено транзакций: "+Bank.operations.size());
            br.close();
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

}
