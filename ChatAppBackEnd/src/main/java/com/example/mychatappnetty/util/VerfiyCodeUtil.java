package com.example.mychatappnetty.util;

public class VerfiyCodeUtil {


    /**
     * generate 4-digit random number;
     * @return
     */
    public static int generateVCode(){
        return  (int)((Math.random()*9+1)*1000);
    }


    /**
     * Encode verify code in the format of "code#time "
     * @param code
     * @return
     */
    public static String encodeVCode(int code){
        return code + "#" + TimeUtil.getTime();
    }

    /**
     * Decode
     * @param encodeString
     * @return code: String[0], time:String[1];
     */
    public static String[] decodeVcode(String encodeString){
        return encodeString.split("#");
    }

    /*public static void main(String[] args) {
        System.out.println(VerfiyCodeUtil.generateVCode());
        String str  = VerfiyCodeUtil.encodeVCode(4434);
        System.out.println(str);
    }*/
}
