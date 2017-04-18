/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年3月29日 
 * @date(最后修改日期)：2017年3月29日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.mock.web.DelegatingServletInputStream;

public class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper{

    /**
     * @param request
     */
    
    private static final int BUFFER_START_POSITION = 0;

    private static final int CHAR_BUFFER_LENGTH = 1024;
    
    private final String body;
    
    public RepeatedlyReadRequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            
        }
            
        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                char[] charBuffer = new char[CHAR_BUFFER_LENGTH];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, BUFFER_START_POSITION, bytesRead);
                }
            } catch (IOException e) {
               
            }
        } else {
            stringBuilder.append("");
        }
        body = stringBuilder.toString();
        // TODO Auto-generated constructor stub
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new DelegatingServletInputStream(byteArrayInputStream);
    }

}
