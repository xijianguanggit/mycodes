package json;

import nio.NioReader;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangdecheng on 2017/12/28.
 */
public class ObjectToJson {

    public static void main(String[] args){

        try {
            NioReader reader = new NioReader("data/Object.txt");
            String line;

            StringBuilder sb = new StringBuilder();
            while ((line = reader.getNextLine()) != null) {
                //System.out.println(line);
                line = line.trim();
                if(StringUtils.isBlank(line)){
                    continue;
                }
                String[] cols = line.split(" ");

                sb.append("\""+cols[2].substring(0,cols[2].length()-1)+"\":");
                if(cols[1].equals("String")){
                    sb.append("\"\"");
                }
                sb.append(",\n");
            }
            System.out.println("{");
            System.out.println(sb.subSequence(0,sb.length()-2));
            System.out.println("}");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
