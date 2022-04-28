package View;

import Server.Server;
import Utility.Utility;
import domain.Student;

import java.util.Scanner;

//视图View层主要用来输入数据传送数据给业务层Server
public class View {
    private Scanner sc=new Scanner(System.in);
    private boolean flag=true;

    private void exit(){
        char c = Utility.readConfirmSelection();
        if(c=='Y'){
            flag=false;
            return;
        }
        System.out.println("======退出终止，返回菜单======");
    }
    private void userLogin(){
        int num=1;
        do{
            System.out.print("请输入用户名：");
            String user = Utility.readString(10);
            System.out.print("请输入密码：");
            String key=Utility.readString(12);
            if(Server.login(user,key)){
                System.out.println("======登陆成功======");
                return;
            }else {
                if(num!=3){
                    System.out.println("======请重试======");
                }
            }
            num++;
        }while(num<4);
        System.out.println("======次数超过3次，登陆失败======");
        System.out.println("======程序退出======");
        System.exit(0);
    }
    private  void checkScore(){

        System.out.print("你想通过那种方式查询（id/name）:");
        String choice = sc.nextLine();
        if(choice.equals("id")){
            System.out.println("请输入查询学生id");
            int id = Utility.readInt();
            if(Server.check(id)){
                System.out.println("======查询成功======");
            }else {
                System.out.println("======查询失败，没有此人记录======");
            }
        }else if(choice.equals("name")){
            System.out.println("请输入查询学生name");
            String name= Utility.readString(10);
            if(Server.check(name)){
                System.out.println("======查询成功======");
            }else {
                System.out.println("======查询失败，没有此人记录======");
            }
        }
        System.out.println("======查询完成======");
    }
    private void ExcelWrite(){
        sc=new Scanner(System.in);
        System.out.print("请输入写入的文件名（不要后缀名）：");//单纯名称不要求输入后缀名
        String filename = sc.nextLine()+".xls";
        Server.writeOnExcel(filename);
    }
    private void TxtWrite(){
        Server.writeOnTxt();
        System.out.println("======成功写入"+Server.getStuNum()+"个学生======");
    }
    private void Keyboard(){
        System.out.print("输入学生id:");
        String id = Utility.readString(5);
        System.out.print("输入学生name:");
        String name = Utility.readString(10);
        System.out.print("输入学生gender:");
        String gender=Utility.readString(2);
        System.out.print("输入学生course:");
        String course = Utility.readString(10);
        System.out.print("输入学生score:");
        String scoreChar = Utility.readString(10);
        double score=Double.parseDouble(scoreChar);
        Student stu = new Student(id, name, gender, course, score);
        Server.add(stu);
    }
    private void changePassword(){
        System.out.print("请输入新密码：");
        String password = Utility.readString(12);
        Server.change(password);
        System.out.println("======修改成功======");
    }

    public  void menu(){
        userLogin();

        while(flag){
            System.out.println("======学生管理系统======");
            System.out.println("1.  从excel加载数据");
            System.out.println("2.  从文本文件加载数据");
            System.out.println("3.  从xml加载数据");
            System.out.println("4.  从json加载数据");
            System.out.println("5.  键盘输入数据");
            System.out.println("6.  成绩查询");
            System.out.println("7.  输出到excel");
            System.out.println("8.  输出到文本文件");
            System.out.println("9.  输出到xml文件");
            System.out.println("10. 输出到json文件");
            System.out.println("11. 修改密码");
            System.out.println("12. 退出");
            System.out.print("请输入你的选择：");
            String choice = Utility.readMenuSelection();
            switch (choice){
                case "1":
                    Server.readExcel();
                    break;
                case "2":
                    Server.readTxt();
                    break;
                case "3":break;
                case "4":break;
                case "5":
                    Keyboard();
                    break;
                case "6":
                    checkScore();
                    break;
                case "7":
                    ExcelWrite();
                    break;
                case "8":
                    TxtWrite();
                    break;
                case "9":break;
                case "10":break;
                case "11":
                    changePassword();
                    break;
                case "12":exit();break;
            }
        }
    }
}
