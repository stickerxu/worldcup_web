package com.worldcup.web.controller.tools;

import com.worldcup.web.util.EncrptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@Slf4j
@RequestMapping("/tools/encryp")
public class EncryptionController {
    @GetMapping({"","/"})
    public String encryp(Model model) {
        model.addAttribute("encryp_method", "md5");
        model.addAttribute("is_salt", 0);
        return "tools/encryption/encryption";
    }

    @PostMapping("/encrypSub")
    public String encrypSub(@RequestParam("encryp_content") String encryp_content, @RequestParam("encryp_method") String encryp_method,
                            @RequestParam("is_salt") Integer is_salt, ModelMap modelMap) {
        try {
            modelMap.put("encryp_content",encryp_content);
            modelMap.put("encryp_method",encryp_method);
            modelMap.put("is_salt",is_salt);
            if (is_salt == 1) {
                String salt = EncrptionUtil.generateSalt(64);
                encryp_content = encryp_content + salt;
                modelMap.put("salt", salt);
            }
            switch (encryp_method) {
                case "md5": modelMap.put("result", EncrptionUtil.md5(encryp_content));break;
                case "sha1": modelMap.put("result", EncrptionUtil.sha1(encryp_content));break;
                case "sha2": modelMap.put("result", EncrptionUtil.sha256(encryp_content));break;
                default:break;
            }
            return "tools/encryption/encryption";
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

}
