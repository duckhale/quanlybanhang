package com.example.sell.constanst;

import java.util.Random;

public class RandomData {
//    private static final String regex = "^[+84]*0{1}[93]{1}[0-9]{7,8}$";

    public String randomText(int limit) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int j = 0; j < limit; j++) {
            int value = (int) (random.nextInt((new ListConstant().max - new ListConstant().min) + 1 + new ListConstant().min));
            if (j%7==0 && code.length()>0){
                code.append(" ");
            }
            code.append(ListConstant.ALPHABET[value].toUpperCase());
        }
        return code.toString();
    }

    public String randomAddress() {
        int max = new ListConstant().maxAddress;
        int min = new ListConstant().minAddress;
        int valueAddress = new Random().nextInt((max - min) + 1 + min);
        return new ListConstant().ADDRESS[valueAddress];
    }

    public String randomPhone() {
        final String[] FIRST_NUMBER = {"3", "9"};
        StringBuilder phone = new StringBuilder("0");
////        int number = (int) Math.floor(Math.random() * (1 - 0 + 1) + 1);
        int number = (int) (new Random().nextInt(1));
        int min = new ListConstant().minNumber;
        int max = new ListConstant().maxNumber;
        phone.append(FIRST_NUMBER[number]);
        for (int i = 0; i < 9; i++) {
            int temp = new Random().nextInt((max - min + 1) + min);
            phone.append(ListConstant.NUMBER[temp]);
        }
        return phone.toString();
    }

    public int randomNumber(int min, int max) {
        int rangeMax = max;
        int rangeMin = min;
        int price = (int) Math.floor(Math.random() * (rangeMax - rangeMin + 1) + rangeMin);
        return price;
    }

    public String randomImage() {
        StringBuilder result = new StringBuilder();
        String url = "https://xixon-knight.000webhostapp.com/product-gym/";
        result.append(url);
        int numberImage = (int) Math.floor(Math.random() * 19);
        result.append(numberImage);
        result.append(".jpg");
        return result.toString();
    }

    public String randomStatusProductReturn(){
        int number = new Random().nextInt(1);
        String result = ListConstant.STATUS_PRODUCT_RETURN[number];
        return result;
    }
    public String radomStatusOrder(){
        int number = new Random().nextInt(1);
        String result = ListConstant.STATUS[number];
        return result;
    }

}
