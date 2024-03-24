package bankingsystem;

public class OperAccount {
    String data;
    int idClient;
    String numAcc;
    String oper;

    public OperAccount(String data, int idClient, String numAcc, String oper) {
        this.data = data;
        this.idClient = idClient;
        this.numAcc = numAcc;
        this.oper = oper;
    }


    public String toString() {

        return "ID:"+this.idClient+" "+
                "Дата: "+this.data+" "+
                "N счета: "+this.numAcc +
                " : "+this.oper;

    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIdCient() {
        return idClient;
    }

    public void setIdCient(int idCient) {
        this.idClient = idCient;
    }

    public String getNumAcc() {
        return numAcc;
    }

    public void setNumAcc(String numAcc) {
        this.numAcc = numAcc;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

}
