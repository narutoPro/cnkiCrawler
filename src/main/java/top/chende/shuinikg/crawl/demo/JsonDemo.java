package top.chende.shuinikg.crawl.demo;


import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @author: chende
 * @date: 2020/7/10 05:54
 * @description:
 * todo jason写入文件
 */
public class JsonDemo {
    public static void main(String[] args) {
        Gson gson=new Gson();
        Account account = new Account("00001", "Freeman", "13000000000");
        System.out.println(gson.toJson(account));

        ArrayList<Account> accountList = new ArrayList<Account>();
        accountList.add(account);
        accountList.add(account);
        System.out.println(gson.toJson(accountList));




    }
}
 class Account {

    private String uid;
    private String userName;
    private String password;
    private String telNumber;

    public Account(String uid, String userName, String telNumber) {
        this.uid = uid;
        this.userName = userName;
        this.telNumber = telNumber;
    }

    @Override
    public String toString() {
        return "Account [uid=" + uid + ", userName=" + userName + ", password=" + password + ", telNumber=" + telNumber
                + "]";
    }
}
