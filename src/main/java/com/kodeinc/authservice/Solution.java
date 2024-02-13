package com.kodeinc.authservice;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-26
 * @Email moverr@gmail.com
 */
 import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/*
 * Create TransactionException, DigitalWallet, and DigitalWalletTransaction classes here.
 */
class TransactionException extends RuntimeException{

    private String errorCode;
    public TransactionException(String errorMessage, String errorCode){
        super(errorMessage);
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return errorCode;
    }
}

class DigitalWallet{
    private String walletId;
    private String userName;
    private String userAccessCode;

    private int walletBalance;

    public DigitalWallet(String walletId,String userName){
        this.walletId = walletId;
        this.userName = userName;

    }

    public DigitalWallet(String walletId,String userName,String userAccessCode){
        this.walletId = walletId;
        this.userName = userName;
        this.userAccessCode = userAccessCode;
    }

    public String getWalletId(){
        return walletId;
    }

    public String getUsername(){
        return userName;
    }

    public String getUserAccessToken(){
        return userAccessCode;
    }

    public int  getWalletBalance(){
        return walletBalance;
    }

    public void setWalletBalance(int newWalletBalance){
        walletBalance = newWalletBalance;
    }

}

class DigitalWalletTransaction{

    public DigitalWalletTransaction(){

    }

    public void addMoney(DigitalWallet digitalWallet, int amount){
        //todo: validate user has access token
        String accessToken = digitalWallet.getUserAccessToken();
        if(accessToken.isEmpty()){
            throw new TransactionException("User not authorized","USER_NOT_AUTHORIZED");
        }

        //todo: validate amount is invalid
        if(amount <= 0){
            throw new TransactionException("Amount should be greater than zero","INVALID_AMOUNT");
        }

        int availableBalance = digitalWallet.getWalletBalance();
        int finalAvailableBalance = availableBalance + amount;
        digitalWallet.setWalletBalance(finalAvailableBalance);

    }

    public void payMoney(DigitalWallet digitalWallet,int amount){

        //todo: validate user has access token
        String accessToken = digitalWallet.getUserAccessToken();
        if(accessToken.isEmpty()){
            throw new TransactionException("User not authorized","USER_NOT_AUTHORIZED");
        }

        //todo: validate amount is invalid
        if(amount <= 0){
            throw new TransactionException("Amount should be greater than zero","INVALID_AMOUNT");
        }

        //todo: validate balance ..
        int availableBalance = digitalWallet.getWalletBalance();
        if(availableBalance < amount){
            throw new TransactionException("Insufficient balance","INSUFFICIENT_BALANCE");
        }

        int finalAvailableBalance = availableBalance - amount;
        digitalWallet.setWalletBalance(finalAvailableBalance);

    }
}


public class Solution {
    private static final Scanner INPUT_READER = new Scanner(System.in);
    private static final DigitalWalletTransaction DIGITAL_WALLET_TRANSACTION = new DigitalWalletTransaction();

    private static final Map<String, DigitalWallet> DIGITAL_WALLETS = new HashMap<>();

    public static void main(String[] args) {
        int numberOfWallets = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfWallets-- > 0) {
            String[] wallet = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet;

            if (wallet.length == 2) {
                digitalWallet = new DigitalWallet("qwrty", "mover");
            } else {
                digitalWallet = new DigitalWallet("qwerty", "mover", "qwerty");
            }

            DIGITAL_WALLETS.put("qwrty", digitalWallet);
        }

        int numberOfTransactions = 1;Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfTransactions-- > 0) {
            String[] transaction = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet = DIGITAL_WALLETS.get(transaction[0]);

            if (transaction[1].equals("add")) {
                try {
                    DIGITAL_WALLET_TRANSACTION.addMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully credited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            } else {
                try {
                    DIGITAL_WALLET_TRANSACTION.payMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully debited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            }
        }

        System.out.println();

        List<String> digitalWalletIds = new ArrayList<>();
        digitalWalletIds.addAll(DIGITAL_WALLETS.keySet());

        Collections.sort(digitalWalletIds);
        for (String digitalWalletId: digitalWalletIds) {
            DigitalWallet digitalWallet = DIGITAL_WALLETS.get(digitalWalletId);
            System.out.println(digitalWallet.getWalletId()
                    + " " + digitalWallet.getUsername()
                    + " " + digitalWallet.getWalletBalance());
        }
    }
}
