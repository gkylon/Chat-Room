package Robot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import Robot.HttpUtils;
import com.google.gson.Gson;

/**
 * The Class QkyRobotServiceImpl.
 */
public class QkyRobotServiceImpl implements RobotService {

    /** The Constant apiTpl. */
    private static final String apiTpl = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=%s";
    
    /** gson³£Á¿ */
    private static  final Gson gson = new Gson();
    
    /**
     * 
     *
     * @param msg the msg
     * @return the response
     */
    @Override
    public Response qa(String msg) {
        
    	
        String api = null;
        try {
            api = String.format(apiTpl, URLEncoder.encode(msg,"UTF-8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String result =  HttpUtils.request(api);

        

        Response response = gson.fromJson(result,Response.class);

        return response;
    }
}
