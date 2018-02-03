package xylink.sdk;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangdecheng on 27/01/2018.
 */
public class TestDisplayName {
    public static void main(String[] args){
        String displayName = "C7AB0FC615794594BE4B1324F7C184D8";
        String userName = null;
        if(!StringUtils.isEmpty(displayName)) {
            try {
                displayName = displayName.replace(" ", "");

                //userName = new String(Hex.decodeHex(displayName.toCharArray()),"UTF16");
               // userName = new String(Base64Utils.decodeFromUrlSafeString(displayName), "utf-8");

            } catch (Exception e) {
                System.err.println("Failed to decode: " + displayName);
                userName = displayName;//this is a fallback
            }
            System.out.println(userName);
        }
    }
}
