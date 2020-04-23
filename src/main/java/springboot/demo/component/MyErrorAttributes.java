package springboot.demo.component;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        Integer code = (Integer) webRequest.getAttribute("code", 0);
        String errormessage = (String) webRequest.getAttribute("errormessage", 0);
        map.put("code",code);
        map.put("errormessage",errormessage);
        return map;
    }
}
