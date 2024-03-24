package bankingsystem;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PersCabinet {
    String log;
    String pass;
    String readLog="";
    String readPass="";
    Scanner sc;
    int id;
    String numAcc;
    String data;

    public PersCabinet(Scanner sc) {

        this.sc = sc;
        sc.nextLine();
        System.out.print("Введите дату >:");
        this.data=sc.next();
        System.out.println("Вход в личный кабинет:");
        System.out.print("Введите логин >:");
        if (sc.hasNext()) {
            this.log = sc.next();
            sc.nextLine();
            System.out.print("Введите пароль >:");
            this.pass = sc.next();
        }

            System.out.println("...Проверка логина и пароля...");
        if(VerClient()) {
            System.out.println("...Авторизация прошла успешно...");
            System.out.println("*******************************");
            System.out.println("*       Добро пожаловать      *");
            System.out.println("*******************************");
            
            System.out.println(Bank.clients.get(id).name +" "+
                               Bank.clients.get(id).surname+" ");
            System.out.println("р/с: "+Bank.clients.get(id).curAccount.numAccount);
            System.out.println("Баланс: "+Bank.clients.get(id).curAccount.balance);
            System.out.println("*******************************");
            AccountTransactions();
            
        } else System.out.println("...Авторизация не пройдена...");
    }

public boolean VerClient(){
    String client = "";
    try (BufferedReader br = new BufferedReader(new FileReader("ClientsBank.txt"))) {

        while ((client =br.readLine())!=null) {
            //client = br.readLine();
                boolean bLog = client.contains(log);
                boolean bPass = client.contains(pass);
                if (bLog&&bPass){
                    this.id=Integer.parseInt(client.substring(0,4));
                    return true;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    return false;
    }
    public void AccountTransactions(){
        String sOper;
        Boolean run = true;
        while (run) {
            System.out.println();
            System.out.println("*******************************");
            System.out.println("*   Доступные операции:       *");
            System.out.println("*******************************");
            System.out.println("*   1 - Вклады                *");
            System.out.println("*   2 - Переводы              *");
            System.out.println("*   3 - История операций      *");
            System.out.println("*   0 - Выход                 *");
            System.out.println("*******************************");
            System.out.print(">:");
            String cmd = sc.next();
            int cmdCod =Integer.parseInt(cmd);
            System.out.println();
            float sum;
            switch (cmdCod) {
                case 1:
                    System.out.println("***************************************************************");
                    System.out.println(Bank.clients.get(id).name + " " + Bank.clients.get(id).surname + " ");
                    System.out.println("***************************************************************");
                    List<Deposit> cDep = Bank.depAccounts.stream().filter(d -> d.idClient == id).collect(Collectors.toList());
                    for (Deposit d : cDep) {
                        System.out.println("Вклад: " + d.nameDep + "  срок(мес.): " + d.term + "  проценты: " + d.percent + "  сумма вклада: " + d.inSum);
                        System.out.println("р/с: " + d.num + " Баланс: " + d.outSum);
                    }
                    System.out.println("************************************************************");
                    System.out.println("***              Выберите операцию:                      ***");
                    System.out.println("************************************************************");
                    System.out.println("***   1-Открыть вклад  *** 2-Закрыть вклад *** 3-Отмена  ***");
                    System.out.println("************************************************************");
                    System.out.print(">:");
                    cmd = sc.next();

                    if (cmd.equals("1")) {
                        Deposit.InfDep();
                        System.out.print("Выберите вклад >:");

                        cmd = sc.next();
                        int depN = Integer.parseInt(cmd);

                        System.out.print("Введите сумму >:");
                        cmd = sc.next();
                        sum = Float.parseFloat(cmd);

                        Deposit dep = new Deposit(id, depN, sum);
                        Bank.depAccounts.add(dep);

                        sOper ="+"+sum;
                        OperAccount oper = new OperAccount(this.data,this.id,dep.num,sOper);
                        Bank.operations.add(oper);

                        InOutFiles.operAccountOutFile();
                        InOutFiles.depAccountsOutFile();
                    }

                    if (cmd.equals("2")) {

                        System.out.print("Введите номер счета >:");
                        String num = sc.next();
                        //out.println("счет; "+num);
                        for (Deposit d : Bank.depAccounts) {
                            if ((d.idClient == id)&&(num.equals(d.num))) {
                                float s=d.TransferCurAccount();
                                sOper ="-"+s;
                                OperAccount dOper = new OperAccount(this.data,this.id,d.num,sOper);
                                Bank.operations.add(dOper);

                                sOper ="+"+s;
                                String n =Bank.clients.get(this.id).curAccount.numAccount;
                                OperAccount cOper = new OperAccount(this.data,this.id,n,sOper);
                                Bank.operations.add(cOper);

                            }
                        }

                        System.out.println(Bank.clients.get(id).name + " " + Bank.clients.get(id).surname + " ");
                        System.out.println("р/с: " + Bank.clients.get(id).curAccount.numAccount);
                        System.out.println("Баланс: " + Bank.clients.get(id).curAccount.balance);
                        System.out.println("************************************************************");

                        System.out.println("************************************************************");
                        List<Deposit> cDep2 = Bank.depAccounts.stream().filter(d -> d.idClient == id).collect(Collectors.toList());
                        for (Deposit d : cDep2) {
                            System.out.println("N клиента: " + d.idClient);
                            System.out.println("Вклад: " + d.nameDep + " срок(мес.): " + d.term + " проценты: " + d.percent + " сумма вклада: " + d.inSum);
                            System.out.println("р/с: " + d.num + " Баланс: " + d.outSum);
                        }
                        System.out.println("************************************************************");

                        InOutFiles.operAccountOutFile();
                        InOutFiles.depAccountsOutFile();
                        InOutFiles.ClientsBankOutFile();
                    }

                    break;

                case 2:
                    System.out.println("Ведите счет получателя:");
                    System.out.print(">:");
                    cmd = sc.next();
                    String numAccDeb=cmd;
                    this.numAcc = Bank.clients.get(id).curAccount.numAccount;
                    //проверка номера счтета
                    if (!numAccDeb.equals(numAcc)) {
                        Client client = transfer(cmd);

                        System.out.println("*********************************************************");
                        System.out.println("**** " +client.name + " " +
                                                    client.surname + " *** р/с: " +
                                                    client.curAccount.numAccount + " ****");
                        System.out.println("*********************************************************");
                        System.out.println("Проверьте информацию о получателе и введите сумму");
                        System.out.print(">:");
                        cmd = sc.next();
                        sum = Float.parseFloat(cmd);

                        //проверка баланса на счету
                        if (Bank.clients.get(this.id).curAccount.balance >sum) {

                            System.out.println("Подтвердите перевод:");
                            System.out.println("1-Подтвердить");
                            System.out.println("2-Отмена");
                            System.out.print(">:");
                            cmd = sc.next();
                            cmdCod = Integer.parseInt(cmd);
                            if (cmdCod == 1) {
                                client.curAccount.deposit(sum);
                                Bank.clients.get(id).curAccount.transfer(sum);
                                System.out.println("*********************************************************");
                                System.out.println("***              Перевод выполнен!                    ***");
                                System.out.println("*********************************************************");

                                sOper = "-" + sum;
                                OperAccount cOper = new OperAccount(this.data, this.id, numAcc, sOper);
                                Bank.operations.add(cOper);

                                sOper = "+" + sum;
                                OperAccount dOper = new OperAccount(this.data, client.getID(), client.curAccount.numAccount, sOper);
                                Bank.operations.add(dOper);

                                InOutFiles.ClientsBankOutFile();
                                InOutFiles.operAccountOutFile();
                            }

                            if (cmdCod == 2) {
                                System.out.println("*********************************************************");
                                System.out.println("***                    ОТМЕНА                         ***");
                                System.out.println("*********************************************************");
                                break;
                            }
                        } else
                            System.out.println("НЕДОСТАТОЧНО СРЕДСТВ!!! \n Для перевода доступно: " + Bank.clients.get(this.id).curAccount.balance);


                    } else System.out.println("Ошибка!Проверьте номер счета для перевода!");
                    break;
                case 3:
                    System.out.println("******************************************************************");
                    System.out.println("***                      История операций                      ***");
                    System.out.println("******************************************************************");
                    Bank.operations.stream().filter(o->o.idClient==this.id).forEach(out::println);
                    System.out.println("******************************************************************");


                    break;    

                case 0:
                    run = false;
                    break;

            }
        }
        
    
    }

    public Client transfer(String num){
        List<Client> lstClient =Bank.clients.values().stream().filter(e ->num.equals(e.curAccount.numAccount)).collect(Collectors.toList());
        return lstClient.get(0);
    }
}