package com.ks.app.operations;

public class CRUDStudent {

    public CRUDStudent(){

    }

    public String create_student(String jsonData){
        String apiUrl = "https://merry-allowed-rhino.ngrok-free.app/inEtudiant";
        DataRetriever dr = new DataRetriever();
        return dr.sendDataToApi(apiUrl,jsonData).toString();
    }

    public String update_student(String jsonData){
        String apiUrl = "https://merry-allowed-rhino.ngrok-free.app/modEtudiant";
        DataRetriever dr = new DataRetriever();
        return dr.sendDataToApi(apiUrl,jsonData).toString();
    }

    public String delete_student(String numE){
        String apiUrl = "https://merry-allowed-rhino.ngrok-free.app/delEtudiant/"+numE;
        DataRetriever dr = new DataRetriever();
        return dr.sendDataToApi(apiUrl,"").toString();
    }

}
