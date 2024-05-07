package bankingsystem;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
            System.out.println("*******************************************************");
            Date curDate = new Date();
            DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
            String date=df.format(curDate);
            out.println("Системная дата: "+date);
            System.out.println("*******************************************************");
            System.out.println("*                     Добро пожаловать                *");
            System.out.println("*******************************************************");
            outInf_theClient(this.id);//выводим информацию по клиену
            System.out.println("*******************************************************");
            outInf_curAccounts(this.id);//выводим информацию по текущему(корреспондентскому) счету
            System.out.println("*******************************************************");
            outInf_depAccounts(this.id);//выводим информацию по депозитным счетам счету
            System.out.println("*******************************************************");
            AccountTransactions();
        } else System.out.println("...Авторизация не пройдена...");
    }
    /*
     * метод выводит текст команды,
     * считывает и возвращает введенный код команды
     */
    public String getCmd(String txtCmd){
        System.out.print(txtCmd);
        return  sc.next();
    }

    /*
     * метод считывает построчно файл ClientsBank.txt
     * ищет в каждой строке значения log и pass
     * при положительном результате метод возвращает true,
     * в переменную id записывается ID клиента (часть строки с 0 по 4 символ)
     */
    public boolean VerClient(){
        String client = "";
        try (BufferedReader br = new BufferedReader(new FileReader("ClientsBank.txt"))) {
            while ((client =br.readLine())!=null) {
                    boolean bLog = client.contains(log);//ищем в строке log клиента
                    boolean bPass = client.contains(pass);//ищем в строке pass клиента
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
    /*
     * метод выводит на экран меню "Доступные операции"
     * в персональном кабинете клиента
     */
    public void MenuAccTransactions(){
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
    }
    /*
     * метод выводит информацию по клинету
     */
    public  void outInf_theClient(int id){
        System.out.println(Bank.clients.get(id).name +" "+Bank.clients.get(id).surname+" ");
    }

    /*
     * метод выводит разделитель
     */
    public void outSeparator(){
        System.out.println("************************************************************");
    }
    /*
     * метод выводит информацию по текущему(корреспондентскому) счету клинета
     */
    public  void outInf_curAccounts(int id){
        System.out.println("Корреспондентский счет:");
        System.out.println("р/с: "+Bank.clients.get(id).curAccount.numAccount);
        System.out.println("Баланс: "+Bank.clients.get(id).curAccount.balance);
    }

    /*
     * метод выводит информацию по вкладам клинета
     */
    public  void outInf_depAccounts(int id){
        List<Deposit> cDep = Bank.depAccounts.stream().filter(d -> d.idClient == id).collect(Collectors.toList());
        for (Deposit d : cDep) {
            System.out.println("Вклад: " + d.nameDep + "  срок(мес.): " + d.term + "  проценты: " + d.percent + "  сумма вклада: " + d.inSum);
            System.out.println("р/с: " + d.num + " Баланс: " + d.outSum);
        }
    }
    /*
     * метод выводит на экран меню "Подвердите перевод:"
     * суммы на счет клиенту
     */
    public void  menuConfirmTransfer(){
        System.out.println("Подтвердите перевод:");
        System.out.println("1-Подтвердить");
        System.out.println("2-Отмена");
    }

    /*
     * метод выводит на экран меню "Выберите операцию:"
     * с вкладами клиента
     */
    public  void menuDepTransactions(){
        System.out.println("************************************************************");
        System.out.println("***              Выберите операцию:                      ***");
        System.out.println("************************************************************");
        System.out.println("***   1-Открыть вклад  *** 2-Закрыть вклад *** 3-Отмена  ***");
        System.out.println("************************************************************");
        System.out.print(">:");
    }

    public void addListOperations(int idClient,String numAcc,String sOper){
        OperAccount oper = new OperAccount(this.data,idClient,numAcc,sOper);
        Bank.operations.add(oper);
    }
    /*
     * метод создает(открывает) депозитный счет -(вклад -NDeposit) на сумму (sum)
     * добавляет этот счет в список депозитных считов и записывает выполненную
     * операцию в список операций
     */
    public void open_depAccount(int NDeposit, float sum ){
        Deposit dep = new Deposit(id, NDeposit, sum);
        Bank.depAccounts.add(dep);
        addListOperations(this.id,dep.num,"+"+sum);
    }
    /*
     * метод закрывает депозитный счет (переводит все денежные средства со счете num
     * на тенкущий (корреспондентский) счет клиента и записывает выполенные операции
     * со счетами в список операций
     *
     */
    public void close_depAccount(String num){
        String sOper;
        // получаем из списка депозитных счетов, счет-d, который соответствует номеру счета - num,
        // введенного текущим клиентом банка с id
        for (Deposit d : Bank.depAccounts) {
            if ((d.idClient == id)&&(num.equals(d.num))) {
                float s=d.TransferCurAccount();//перевод всех денежных средств с депозита на текущий счет
                //добавляем в список операций операцию  с депозитным счетом
                addListOperations(this.id,d.num,"-"+s);
                //получаем текущий(корреспондентсякий) номер счета клиента банка
                String n =Bank.clients.get(this.id).curAccount.numAccount;
                //добавляем в список операций операцию с текущим (корреспондентским) счетом
                addListOperations(this.id,n,"+"+s);
            }
        }
    }
    /*
     * метод обеспечиваеи ввод необходимой информаци для открытия вклада,
     * вызывает метод создающий депозитный счет, сохраняет историю операций
     */
    public void openDeposits(){
        Deposit.InfDep(); //выводим на экран информацию по доступным вкладам
        String cmd=getCmd("Выберите вклад >:");
        int NDeposit = Integer.parseInt(cmd);//номер вклада, который клиент хочет открыть
        cmd=getCmd("Введите сумму >:");
        float sum = Float.parseFloat(cmd);//сумма, которую клиент хочет положить на вклад
        open_depAccount(NDeposit,sum);//создаем депозитный счет
        InOutFiles.operAccountOutFile();//сохраняем в историю оперций в файле
        InOutFiles.depAccountsOutFile();//сохраняем в депозитный счет в файле
    }
    /*
     * метод обеспечиваеи ввод необходимой информаци для закрытия вклада,
     * вызывает метод закрывающий депозитный счет, сохраняет историю операций
     */
    public void closeDeposits(){
        String num = getCmd("Введите номер счета >:");
        close_depAccount(num);
        outInf_theClient(this.id);
        outInf_curAccounts(this.id);
        outSeparator();
        outInf_depAccounts(this.id);
        outSeparator();
        InOutFiles.operAccountOutFile();
        InOutFiles.depAccountsOutFile();
        InOutFiles.ClientsBankOutFile();
    }

    /*
     * метод обеспечивает выбор операции с владами,
     * запускает на выполнение метод открывающий/закрывающий вклад
     */
    public void TransactionsDeposits(){
       outInf_theClient(this.id);//выводим информацию по клиенту
       outInf_depAccounts(this.id);//выводим информацию по вкладам клиента
       menuDepTransactions();//выводим меню доступных операций с вкладами
       String cmd=getCmd("");
       //Открыть вклад
       if (cmd.equals("1")) {
           openDeposits();
           System.out.println("*********************************************************");
           System.out.println("***                 Вклад открыт!                    ***");
           System.out.println("*********************************************************");
       }
       //Закрыть вклад
       if (cmd.equals("2")) {
           closeDeposits();
           System.out.println("************************************************************");
           System.out.println("*** Вклад закрыт! Все деньги переведены на текущий счет! ***");
           System.out.println("************************************************************");
       }
   }
    /*
     * метод выполняет перевод со счета на счет,
     * сохраняет историю операций
     */
    public  void transferAccount(int recIDClient,float sum) {
       Bank.clients.get(recIDClient).curAccount.deposit(sum);
       Bank.clients.get(id).curAccount.transfer(sum);
       System.out.println("*********************************************************");
       System.out.println("***              Перевод выполнен!                    ***");
       System.out.println("*********************************************************");
       addListOperations(this.id,numAcc,"-" + sum);
       addListOperations(recIDClient,numAcc,"+" + sum);
       InOutFiles.ClientsBankOutFile();
       InOutFiles.operAccountOutFile();
    }

    /*
     * метод обеспечивает ввод инфформации для перевода денег на счет клиену банка
     * проверяет счета на совадение, баланс на счету,  реализовывает подтверждения или отмену перевода
     * запускает метод выполняющий перевод или отменяет операцию
     */
    public void TransactionsTransfer(){
        String cmd =  getCmd("Ведите счет получателя >:");
        String numAccDeb=cmd;
        this.numAcc = Bank.clients.get(id).curAccount.numAccount;
        //проверка номера счтета
        if (!numAccDeb.equals(numAcc)) {
            int recIDClient =getIDClient(cmd);
            System.out.println("Проверьте информацию о получателе:");
            outInf_theClient(recIDClient);
            outInf_curAccounts(recIDClient);
            cmd = getCmd("Ведите сумму для перевода >:");
            float sum = Float.parseFloat(cmd);
            //проверка баланса на счету
            if (Bank.clients.get(this.id).curAccount.balance >sum) {
                menuConfirmTransfer();//меню для подтверждения операции перевода
                cmd = getCmd(">:");
                int cmdCod = Integer.parseInt(cmd);
                //перевод
                if (cmdCod == 1) {
                    transferAccount(recIDClient,sum);//перевод
                }
                //отмена
                if (cmdCod == 2){
                    System.out.println("*********************************************************");
                    System.out.println("***                    ОТМЕНА                         ***");
                    System.out.println("*********************************************************");
                }
            } else//если недостаточно средств на счету
                System.out.println("НЕДОСТАТОЧНО СРЕДСТВ!!! \n Для перевода доступно: " + Bank.clients.get(this.id).curAccount.balance);

        } else //если номера счетов совпадают
            System.out.println("Ошибка!Проверьте номер счета для перевода!");
    }
    /*
     * метод выводит историю операций со счетами клиента
     */
    public  void  TransactionsAccountHistory(){
        System.out.println("******************************************************************");
        System.out.println("***                      История операций                      ***");
        System.out.println("******************************************************************");
        Bank.operations.stream().filter(o->o.idClient==this.id).forEach(out::println);
        System.out.println("******************************************************************");
    }
    public void AccountTransactions(){
        boolean run = true;
        this.data=getCmd("Введите дату выполняемой операции >:");
        while (run) {
            MenuAccTransactions();//выводим меню доступных операций для клиента
            String cmd=getCmd("");
            int cmdCod =Integer.parseInt(cmd);//код выбранной клиентом оперции
            System.out.println();
            switch (cmdCod) {
                case 1://Вклады
                    TransactionsDeposits();//операции с вкладами
                    break;
                case 2://Переводы
                    TransactionsTransfer();
                    break;
                case 3://История операций
                    TransactionsAccountHistory();
                    break;
                case 0://Выход
                    run = false;
                    break;
            }
        }
    }

    public int getIDClient(String num){
        List<Client> lstClient =Bank.clients.values().stream().filter(e ->num.equals(e.curAccount.numAccount)).collect(Collectors.toList());
        return lstClient.get(0).getID();
    }
}