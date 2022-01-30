package com.api.api.controllers;
import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;


@RestController
public class Main {

    private static final String SECRET_KEY = "12345667890";
    private static final String SALTVALUE = "1234567890";


    @GetMapping("/")
    public String home()
    {
        return "Api Working Fine";
    }


   //@PostMapping("/{api_version}/Accounts/{account_sid}/Messages.json")
   @RequestMapping(value = "/{api_version}/Accounts/{account_sid}/Messages.json", method = RequestMethod.POST)
     public String Message(
            @PathVariable("api_version") String api_version,
            @PathVariable("account_sid") String account_sid,
            @RequestBody String body
            ) {

//        HashMap<String,String> auth = this.getCredentials();
//        System.out.println(auth.get("username"));


         HashMap <String, String> data = new HashMap<>();
         String[] arr = body.split("&");
         for(int i=0; i < arr.length; i++)
         {
              String[] inarr = arr[i].split("=");
              try {
                  data.put(inarr[0], URLDecoder.decode(inarr[1], "UTF-8"));
              }catch(Exception e)
              {
                  System.out.println(e.toString());
              }
         }

         Date curr_date = new Date(System.currentTimeMillis());

         String date = curr_date.toString();
         String[] darr = date.split(" ");

         String final_time_zone = this.getCurrentTimezoneOffset();

         String final_date = darr[0]+", "+darr[2]+" "+darr[1]+" "+darr[5]+" "+darr[3]+" "+final_time_zone;

         JSONObject json = new JSONObject();
         json.put("account_sid","AC"+account_sid);
         json.put("api_version",api_version);
         json.put("body",data.get("Body"));
         json.put("date_created",final_date);
         json.put("date_sent",final_date);
         json.put("date_updated",final_date);
         json.put("direction","outbound-api");
         json.put("error_code",null);
         json.put("error_message",null);
         json.put("from",data.get("From"));
         json.put("messaging_service_sid","MG"+this.encrypt(account_sid));
         json.put("num_media","0");
         json.put("num_segments","0");
         json.put("price",null);
         json.put("price_unit",null);
         json.put("sid","SM"+account_sid);
         json.put("status","sent");

         JSONObject subresource_uris = new JSONObject();
         subresource_uris.put("media","/"+api_version+"/Accounts/AC"+account_sid+"/Messages/SM"+account_sid+"/Media.json");
         json.appendField("subresource_uris",subresource_uris);
         json.put("to",data.get("To"));
         json.put("uri","/"+api_version+"/Accounts/AC"+account_sid+"/Messages/SM"+account_sid+"/Media.json");







       return json.toJSONString();

    }

    public static String getCurrentTimezoneOffset() {

        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        String offset = String.format("%02d%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

        return offset;
    }

    public static String encrypt(String strToEncrypt)
    {
        try
        {

            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);

            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e)
        {
            System.out.println("Error occured during encryption: " + e.toString());
        }
        return null;
    }


    private HashMap<String,String> getCredentials() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        String password = ((UserDetails)principal).getPassword();
        HashMap<String,String> data = new HashMap<>();
        data.put("username",username);
        data.put("password",password);
        return data;
    }

}
