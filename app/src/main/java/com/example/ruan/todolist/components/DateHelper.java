package com.example.ruan.todolist.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static boolean dateIsValid(String data){
        try {
            //SimpleDateFormat é usada para trabalhar com formatação de datas
            //neste caso meu formatador irá trabalhar com o formato "dd/MM/yyyy"
            //dd = dia, MM = mes, yyyy = ano
            //o "M" dessa String é maiusculo porque "m" minusculo se n me engano é minutos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //a mágica desse método acontece aqui, pois o setLenient() é usado para setar
            //sua escolha sobre datas estranhas, quando eu seto para "false" estou dizendo
            //que não aceito datas falsas como 31/02/2016
            sdf.setLenient(false);
            //aqui eu tento converter a String em um objeto do tipo date, se funcionar
            //sua data é valida
            sdf.parse(data);
            //se nada deu errado retorna true (verdadeiro)
            return true;
        } catch (ParseException ex) {
            //se algum passo dentro do "try" der errado quer dizer que sua data é falsa, então,
            //retorna falso
            return false;
        }
    }

    public static Date convertStringPTBRToData(String stringDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToStringPTBR(Date date){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        return dateFormat.format(date);
    }

    public static String convertDateToStringPTBRWithDivisor(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
