package com.example.mobilegamev;

public class Coin {
    private static Coin single_instance = null;
    private int coin;

    private Coin(){}

    public static Coin getInstance(){
        if(single_instance == null){
            single_instance = new Coin();
        }

        return single_instance;
    }

    public void setCoin(int c){
        coin = c;
    }

    //덧셈, 뺄셈 공용
    public void addCoin(int c){
        coin += c;
    }
    
    public int getCoin(){
        return coin;
    }


}
