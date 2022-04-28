package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import Utility.Utility;
import domain.Student;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

//业务层Server主要对具体业务进行实现
public class Server {
    //这里地址要填你自己创建的Excel表格地址
    private static File file = new File("E:\\Idea_Project\\ManageSystem\\src\\data\\Student_Info_Excel.xls");
    private static ArrayList<Student> list=new ArrayList<>();
    private String password;


    public static void readExcel() {
        if(!file.exists()){
            System.out.println("======文件不存在======");
        }
        InputStream is =null;
        Workbook wb =null;
        try {
            is = new FileInputStream(file);
            wb = Workbook.getWorkbook(is);//通过Workbook静态方法获取Workbook对象wb
            int numberOfSheets = wb.getNumberOfSheets();//通过wb获取excel页面的数量（有几页）
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = wb.getSheet(i);//获取索引为i的当前页面的对象
                int rows = sheet.getRows();//获取当前页面的行数
                if(rows<2){
                    System.out.println("======页面没有记录======");
                    return;
                }
//                int columns = sheet.getColumns();//获取当前页面的列数，不过由于列数确定为5，就不需要了
                for (int j = 1; j < rows; j++) {//第一行是属性名
//                    for (int k = 0; k < 5; k++) {//这里的5为列数，每一类知道之后也不用循环
                    String id = sheet.getCell(0, j).getContents();//获取位置上单元格里面的内容
                    String name = sheet.getCell(1, j).getContents();
                    String gender = sheet.getCell(2, j).getContents();
                    String course = sheet.getCell(3, j).getContents();
                    double score = Double.parseDouble(sheet.getCell(4, j).getContents());
                    add(new Student(id,name,gender,course,score));//调用add方法将该对象添加到ArrayList集合中
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======从Excel读取成功======");
    }
    public static void writeOnExcel(String filename){
        WritableWorkbook workbook =null;//由于要关闭资源，就定义在外边。防止产生异常资源无法关闭
        Label label=null;
        Student stu=null;
        try {
            //这里要填你src/data目录的地址
            File file=new File("E:\\Idea_Project\\ManageSystem\\src\\data\\",filename);
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet_one = workbook.createSheet("sheet_One", 0);
            label=new Label(0,0,"id");
            sheet_one.addCell(label);
            label=new Label(1,0,"name");
            sheet_one.addCell(label);
            label=new Label(2,0,"gender");
            sheet_one.addCell(label);
            label=new Label(3,0,"");
            sheet_one.addCell(label);
            label=new Label(4,0,"id");
            sheet_one.addCell(label);
            for (int i = 0; i < list.size(); i++) {
                stu=list.get(i);
                String[] fileds=new String[5];//用来存放每个学生的五个属性值
                fileds[0]=stu.getId();
                fileds[1] = stu.getName();
                fileds[2] = stu.getGender();
                fileds[3] = stu.getCourse();
                fileds[4]=String.valueOf(stu.getScore());
                for (int j = 0; j < 5; j++) {
                    label=new Label(j,i+1,fileds[i]);
                    sheet_one.addCell(label);
                }
            }
            workbook.write();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======写入Excel成功======");
    }
    public static void readTxt(){
        int count=0;
        if(!file.exists()){
            System.out.println("======文件不存在======");
        }
        String str;
        String[] fields;
        try {
            BufferedReader br=new BufferedReader(new FileReader("E:\\Idea_Project\\ManageSystem\\src\\data\\Student_Info_Txt.txt"));

            while((str=br.readLine())!=null){//套用读数据公式
                fields=str.split(",");
                String id=fields[0];
                String name=fields[1];
                String gender=fields[2];
                String course=fields[3];
                double score=Double.parseDouble(fields[4]);
                add(new Student(id,name,gender,course,score));
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======成功导入"+count+"个学生======");
    }
    public static void writeOnTxt(){
        BufferedWriter bw=null;
        try{
            //这里要填你src/data目录的地址
            File file=new File("E:\\Idea_Project\\ManageSystem\\src\\data\\Student_Info_Txt.txt");
            for (int i = 0; i < list.size(); i++) {
                Student stu = list.get(i);
                bw=new BufferedWriter(new FileWriter(file,true));
                bw.write(stu.toString());
                bw.newLine();
                bw.flush();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======写入txt文件成功======");
    }
    public static boolean login(String user,String key){

        InputStream ras = new Server().getClass().getClassLoader().getResourceAsStream("data/user.properties");
        Properties prop=new Properties();
        try {
            prop.load(ras);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ras.close();//资源释放
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String username = prop.getProperty("username", "null");
        String password = prop.getProperty("password", "null");

        return (user.equals(username))&&(key.equals(password));

    }
    public static boolean check(int id){
        String sId=String.valueOf(id);
        boolean flag=false;
        for (Student stu :
                list) {
            if (sId.equals(stu.getId())){
                flag=true;
                System.out.println(stu);
                break;
            }
        }
        return flag;
    }
    public static boolean check(String name){
        boolean flag=false;
        for (Student stu :
                list) {
            if (name.equals(stu.getName())){
                flag=true;
                System.out.println(stu);
                break;
            }
        }
        return flag;
    }
    public static void change(String password) {
        try {
            FileOutputStream fos = new FileOutputStream("E:\\Idea_Project\\ManageSystem\\src\\data\\user.properties");
            Properties prop=new Properties();
            prop.setProperty("username",getUsername());
            prop.setProperty("password",password);
            prop.store(fos,"");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean add(Student stu){
        if(notExist(stu)){
            list.add(stu);
            return true;
        }
        System.out.println("======学生已存在======");
        return false;
    }
    public static boolean notExist(Student stu){//判断是否已经存在于list中
        if(stu==null){
            System.out.println("======添加为空======");
            return false;
        }
        for (Student student :
                list) {
            if(stu.getId().equals(student.getId())&&stu.getCourse().equals(student.getCourse())){//这里的equals调用的是student中自己定义的equals方法，如果不满意自己定义一个也行
                return false;
            }
        }
        return true;
    }
    public  static String getUsername(){
        InputStream ras = new Server().getClass().getClassLoader().getResourceAsStream("data/user.properties");
        Properties prop=new Properties();
        try {
            prop.load(ras);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ras.close();//资源释放
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop.getProperty("username", "null");
    }
    public static int getStuNum(){
        return list.size();
    }

}
