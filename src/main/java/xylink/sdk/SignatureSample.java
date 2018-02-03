package xylink.sdk;

/**
 * Created by wangdecheng on 2018/1/2.
 */
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.*;

public class SignatureSample {

    private String requestUriPrefix = "http://localhost:8080/api/rest/external/v1/";

    protected String computeStringToSign(String requestPath, Map<String, String> reqParams, String reqJsonEntity, String reqMethod) throws Exception {
        //1. request method
        StringBuffer strToSign = new StringBuffer(reqMethod);
        strToSign.append("\n");
        //2. request path
        strToSign.append(requestPath.substring(requestUriPrefix.length()));
        strToSign.append("\n");
        //3. sorted request param and value
        List<String> params = new ArrayList<>(reqParams.keySet());
        Collections.sort(params);
        for (String param : params) {
            strToSign.append(param);
            strToSign.append("=");
            try {
                strToSign.append(URLEncoder.encode(reqParams.get(param), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
            strToSign.append("&");
        }
        strToSign.deleteCharAt(strToSign.length() - 1);
        strToSign.append("\n");
        //4. request entity
        byte[] reqEntity = reqJsonEntity.getBytes("utf-8");
        if (reqEntity.length == 0) {
            byte[] entity = DigestUtils.sha256("");
            strToSign.append(Base64.encodeBase64String(entity));
        } else {
            byte[] data = null;
            if (reqEntity.length <= 100) {
                data = reqEntity;
            } else {
                data = Arrays.copyOf(reqEntity, 100);
            }
            byte[] entity = DigestUtils.sha256(data);
            strToSign.append(Base64.encodeBase64String(entity));
        }

        String ret = strToSign.toString();
        System.out.println(ret);
        System.out.println("------------------");
        return ret;
    }

    private String calculateHMAC(String data, String key) throws SignatureException {
        try {
            SecretKeySpec e = new SecretKeySpec(key.getBytes("UTF8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(e);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF8"));
            String result = Base64.encodeBase64String(rawHmac);
            return result;
        } catch (Exception var6) {
            throw new SignatureException("Failed to generate HMAC : " + var6.getMessage());
        }
    }

    public String computeSignature(String jsonEntity, String method, String token, String reqPath) {
        try {
            Map<String, String> reqParams = new HashMap<>();
            int idx = reqPath.indexOf("?");
            String[] params = reqPath.substring(idx + 1).split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                reqParams.put(pair[0], pair[1]);
            }
            reqPath = reqPath.substring(0, idx);
            String strToSign = computeStringToSign(reqPath, reqParams, jsonEntity, method);
            String mySignature = calculateHMAC(strToSign, token);
            mySignature = mySignature.replace(" ", "+");
           // return mySignature;
            return URLEncoder.encode(mySignature, "utf-8");
        } catch (Exception e) {
            return null;
        }
    }

    static void  meetingStatus(){
        String url = "http://localhost:8080/api/rest/external/v1/conferenceControl/meetings/status?enterpriseId=f31e1a72c2186269f69ac4513a6b44687b9dddb7";
        // String url="http://localhost:8080/api/rest/external/v1/conferenceControl/912345678915/online/count?exceptName=111&enterpriseId=f31e1a72c2186269f69ac4513a6b44687b9dddb7";
        String token = "71e42646265ec40d85b50842ed6dd76853618a9487a914fdb9097197dad6b609";
        //String jsonEntity = "{\"meetings\":\"912345678915,913811550578,913088888888,913581735469\"}";
        String jsonEntity = "[\"912345678915\",\"913811550578\"]";
        System.out.println(url+"&signature="+new SignatureSample().computeSignature(jsonEntity, "POST", token, url));
        System.out.println(jsonEntity);
    }
    static void onlineCount(){
         String url="http://localhost:8081/api/rest/external/v1/conferenceControl/912345678915/online/count?exceptName=HN-HYBRID-DEV-NE2005&enterpriseId=f31e1a72c2186269f69ac4513a6b44687b9dddb7";
        String token = "71e42646265ec40d85b50842ed6dd76853618a9487a914fdb9097197dad6b609";
        //String jsonEntity = "{\"meetings\":\"912345678915,913811550578,913088888888,913581735469\"}";
        System.out.println(new SignatureSample().computeSignature("", "GET", token, url));
    }
    static void currentMeetingDetail(){
        String url = "http://localhost:8080/api/rest/external/v1/conferenceControl/currentMeeting/detail?enterpriseId=fd6c3449651e5eb25c80c38bde20be0aa5e9ab33&needQuality=true";
        String token = "ba7868558b63eb2eea22a072c422397cfec7d1f58eaa301fa86ffa15b9929a1e";
        //String jsonEntity = "{\"meetings\":\"912345678915,913811550578,913088888888,913581735469\"}";
        System.out.println(new SignatureSample().computeSignature("", "GET", token, url));
    }
    static void nemoPutDisplayName(){
        String url = "http://localhost:8080/api/rest/external/v1/buffet/nemos?enterpriseId=b9cf2b4357546d4bbaa7f36e334bc650dd916fe2&nemoNumber=145858&displayName=wangdecheng";
        String token = "442943b23151687a1dc43243cd4465e1e69529acb8ea84f04411261fc94f09bd";
        //String jsonEntity = "{\"meetings\":\"912345678915,913811550578,913088888888,913581735469\"}";
        System.out.println(new SignatureSample().computeSignature("", "DELETE", token, url));
    }
    public static void main(String[] args) throws Exception {
        meetingStatus();
        //onlineCount();
        //currentMeetingDetail();
        //nemoPutDisplayName();
    }
}