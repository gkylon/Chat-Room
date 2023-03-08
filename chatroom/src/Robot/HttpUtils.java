package Robot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * The Class HttpUtils.
 * 
 * @author GUO
 */
public class HttpUtils {

    /**
     * Request����
     * @param ����api
     * @return ����string
     */
    public static String request(String api){

        HttpURLConnection connection = null;
        
        int responseCode = 0;
        
        try{
            URL url = new URL(api);
            //��ȡ��Ӧ�����Ӷ���
            connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
        }catch (Exception e){
            e.printStackTrace();//����쳣��Ϣ
        }

        if(200 <= responseCode && responseCode<=299){
            try(InputStream inputStream = connection.getInputStream();
                BufferedReader in =  new BufferedReader(new InputStreamReader(inputStream));
            		)
            {
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = in.readLine())!= null){
                    response.append(currentLine);
                }
                String result = response.toString();
                return result;
            }catch (Exception e){
                e.printStackTrace();//����쳣��Ϣ
            }
        }
        return null;

    }

}
