package concurrent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdecheng on 11/9/17.
 */
public class POBuilder {

    static class Field{
        String fieldName;
        String classFieldName;
        String fieldType;
    }

    static class Table{
        String tableName;
        String className;
        List<Field> fieldList = new ArrayList<>();
    }

    static List<Table> tableList = new ArrayList<>();

    static void build() throws  Exception{
        FileReader fileReader = new FileReader("./dbupgrade/upgrade_sql/10128.sql");

        BufferedReader br = new BufferedReader(fileReader);

        String str = null;

        while((str = br.readLine()) != null) {

            Table table = null;

            if(str.toLowerCase().contains("table")){

                table = new Table();
                tableList.add(table);

                String tableName = str.substring(0,str.length()-1).trim();

                tableName = tableName.substring(tableName.lastIndexOf(" ")+1);

                if(tableName.contains("`")){
                    tableName = tableName.substring(1,tableName.length()-1);
                }

            }else if(!str.trim().toLowerCase().contains("primary")){
                Field field = new Field();

                table.fieldList.add(field);
                String[] fieldString = str.trim().split(" ");
                field.fieldName = fieldString[0].contains("`")?fieldString[0].substring(1,fieldString[0].length()-1):fieldString[0];
                field.fieldType = getFieldType(fieldString[1]);
            }

        }

        generatePO();

        br.close();
        fileReader.close();
    }

   static  List getTest(){
        return new ArrayList();
    }
//
//    static  void connectRedis(){
//        Jedis jedis = new Jedis("10.170.191.64", 6379);
//        jedis.auth("wow!nemo");
//    }

    public static void main(String[] args) throws Exception {



    }

    private static void generatePO()  throws  Exception{
        for(Table table :tableList){
            String[] nameStrs = table.tableName.split("_");
            String className = "";
            for(int i=1;i<nameStrs.length;i++){
                className += nameStrs[i].substring(0,1).toUpperCase()+nameStrs[i].substring(1);
            }
            table.className = className;
            System.out.println(className);
            for(Field field:table.fieldList){

            }
//            FileWriter writer = new FileWriter("./po");
//            BufferedWriter bw = new BufferedWriter(writer);
//            bw.write(sb.toString());
//
//            bw.close();
//            writer.close();
        }

    }

    private static String getFieldType(String s) {
        if(s.trim().toLowerCase().startsWith("varchar")){
            return "String";
        }
        if(s.trim().toLowerCase().startsWith("bigint")){
            return "Long";
        }

        return null;
    }
}
