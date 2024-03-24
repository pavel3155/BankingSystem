/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingsystem;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author koval
 */
public class Deposit {
    int idClient;//id клиента
    String num;//номер счета
    int depN;//номер вклада(название)
    String nameDep;//
    int term;//срок
    int percent;//проценты
    float inSum;//сумма вклада
    float outSum;//сумма вклада с процентами
    //float balance;

    public Deposit(int idClient, int depN, float inSum) {

        this.idClient = idClient;
        this.inSum = inSum;
        this.depN=depN;
        this.num=GenerateNum();
        
        if (depN==1){
            this.nameDep="Доходный-6";
            this.term = 6;
            this.percent= 16;
            outSum=inSum+inSum*percent/100/2;  //начисление процентов
        }

        if (depN==2){
            this.nameDep="Доходный-12";
            this.term = 12;
            this.percent = 11;
            outSum=inSum+inSum*percent/100;  //начисление процентов
        }

    }

    public Deposit(int idClient, String num, int depN, String nameDep, int term, int percent, float inSum, float outSum) {
        this.idClient = idClient;
        this.num = num;
        this.depN = depN;
        this.nameDep = nameDep;
        this.term = term;
        this.percent = percent;
        this.inSum = inSum;
        this.outSum = outSum;
    }
    
    public String GenerateNum(){
       int n=Bank.depAccounts.size();
       return "42303810500100"+n+this.idClient;
       
    }
    
    static public void InfDep(){
        
        System.out.println("***********************************************************");
        System.out.println("*               ВКЛАДЫ для физических лиц                 *");   
        System.out.println("***********************************************************");
        System.out.println("* N *   Вклад      *    Срок   *   Сумма      * Проценты  *"); 
        System.out.println("***********************************************************");
        System.out.println("* 1 * Доходный-6   *   6 мес.  * 1000-1000000 *    16     *"); 
        System.out.println("* 2 * Доходный-12  *  12 мес.  * 1000-1000000 *    11     *"); 
        System.out.println("***********************************************************");  
        
    }
    //перевод на текущий счет клиента
    public float TransferCurAccount(){
        float sum=outSum;
        Bank.clients.get(this.idClient).curAccount.deposit(this.outSum);
        this.inSum=0;
        this.outSum=0;
        return sum;
    }
    public String toString() {
        
        return "ID:= "+this.idClient+" "+
                "N счета: "+this.num+
                " : "+this.nameDep +
                " : "+this.inSum +
                " : "+this.outSum;
                
    }            
            
    public float getInSum() {
        return inSum;
    }

    public void setInSum(float inSum) {
        this.inSum = inSum;
    }

    public float getOutSum() {
        return outSum;
    }

    public void setOutSum(float outSum) {
        this.outSum = outSum;
    }
    
    
    
}
