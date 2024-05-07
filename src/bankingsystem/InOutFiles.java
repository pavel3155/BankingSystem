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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author koval
 */
public class InOutFiles {
    /**
     * Загрузка из файла клиентов банка
     */
    static public  void loadClientsFromFile(){
        System.out.println("......Загрузка клиентов банка......");
        List<String[]> lstClient = readingFile("ClientsBank.txt");
        for (String[] c: lstClient){
            int id=Integer.parseInt(c[0]);
            float balance=Float.parseFloat(c[7]);
            Account curAccount =new Account(id,c[5],c[6],balance);
            Client Client = new Client(id,c[1],c[2],c[3],c[4],curAccount);
            clientLoad(Client);
        }
        System.out.println("Загружено клиентов: "+clients.size());
    }
    /**
     * Загрузка из файла истории операций
     */
    static public  void loadHistoryTransactionsAccountsFromFile(){
        System.out.println("......Загрузка истории операций со счетами клиентов......");
        List<String[]> lstDepositAccounts = readingFile("operAccounts.txt");
        for (String[] c: lstDepositAccounts){

            int id=Integer.parseInt(c[0]);
            String data = c[1];
            String num =c[2];
            String oper = c[3];
            OperAccount operAcc =new OperAccount(data,id,num,oper);
            Bank.operations.add(operAcc);
            System.out.println(operAcc);
        }
        System.out.println("Загружено транзакций: "+Bank.operations.size());
    }
    /**
     * Загрузка из файла депозитных счетов
     */
    static public  void loadDepositAccountsFromFile(){
        System.out.println("......Загрузка депозитных счетов клиентов банка......");
        List<String[]> lstDepositAccounts = readingFile("depAccounts.txt");
        for (String[] c: lstDepositAccounts){
            int id=Integer.parseInt(c[0]);
            String num =c[1];
            int depN=Integer.parseInt(c[2]);
            String nameDep=c[3];
            int term=Integer.parseInt(c[4]);
            int percen=Integer.parseInt(c[5]);
            float inSum=Float.parseFloat(c[6]);
            float outSum=Float.parseFloat(c[7]);
            Deposit dep =new Deposit(id,num,depN,nameDep,term,percen,inSum,outSum);
            Bank.depAccounts.add(dep);
            System.out.println(dep);
        }
        System.out.println("Загружено депозитных счетов: "+Bank.depAccounts.size());
    }

    /**
     * метод разбивает строку на подстроки,
     * возвращает массив подстрок
     */
    static String[] GetArrString(StringTokenizer st){
        int stCount=st.countTokens();
        String str[] = new String[stCount];
        int i = 0;
        while (st.hasMoreTokens()) {
            str[i] = st.nextToken();
            i++;
        }
        return  str;
    }
    /**
     * метод считывает файл,
     * загружает в список полученный массив значений
     *
     */
    static List<String[]> readingFile (String fName){
        List<String[]> lst = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fName))) {
            String read=br.readLine();
            while (read!=null) {
                StringTokenizer st = new StringTokenizer(read, ":");
                lst.add(GetArrString(st));
                read=br.readLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return lst;
    }
    /**
     * Запись в файл клиентов банка
     */
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
    /**
     * Запись в файл депозитных счетов
     */
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
    /**
     * Запись в файл выполненных операций с клиентскими счетами
     */
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
}
