/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingsystem;

import java.util.Scanner;

/**
 *
 * @author koval
 */
public class BankingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here2
        Bank bank = new Bank();
        Boolean run = true;
        Scanner sc = new Scanner(System.in);

        while (run) {
            System.out.println("*****************************");
            System.out.println("***      Клиент Банк      ***");
            System.out.println("*****************************");
            System.out.println("***    1 - Регистрация    ***");
            System.out.println("***    2 - Вход           ***");
            System.out.println("***    0 - Выход          ***");
            System.out.println("*****************************");
            System.out.print(">:");
            String cmd = sc.next();
            switch (cmd) {
                case "1":
                    RegClentBank reg = new RegClentBank(sc);
                    break;

                case "2":
                    PersCabinet ver=new PersCabinet(sc);
                    break;
                case "0":
                    run = false;
                    break;
            }
        }
    }
}
