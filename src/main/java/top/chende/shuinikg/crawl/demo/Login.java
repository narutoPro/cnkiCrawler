package top.chende.shuinikg.crawl.demo;

/**
 * @author: chende
 * @date: 2019/12/2 23:25
 * @description:
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


    public class Login {

//String userName;
//String passWord;

        public Login(){

        }

        public void setUserName(WebDriver driver,String userName){

            WebElement username = driver.findElement(By.id("loginname"));//定位用户名输入框
             username.sendKeys(userName);//输入用户名root
        }
        public void setPassWord(WebDriver driver,String passWord){

            WebElement password = driver.findElement(By.id("nloginpwd"));//定位密码输入框
            password.sendKeys(passWord);//输入密码root
        }

        /**
         *能够完成登陆
         * @param driver
         */
        public void clickLogin(WebDriver driver){

            WebElement loginbtn =  driver.findElement(By.id("userLogin"));
                    //driver.findElement(By.xpath("//input[@value='登 录']"));//定位登录按钮，xpath相对路径
//xpath绝对路径
 //("html/body/div[1]/div/div/form/label[4]/input[@value='登 录']"));//("html/body/div[1]/div/div/form/label[4]/input[1]"));//
            loginbtn.click();//点击登录按钮
        }
        public void clickCancel(WebDriver driver){

            WebElement loginbtn = driver.findElement(By.id("userLogin"));
                    //driver.findElement(By.xpath("//input[@value='取 消']"));//定位登录按钮，xpath相对路径
            loginbtn.click();//点击登录按钮
        }
    }



