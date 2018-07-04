package com.cup.worldcup.controller.encryption;

import com.cup.worldcup.util.EncrptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
@Slf4j
public class EncryptionController {
    @GetMapping("/encryp")
    public String encryp(Model model) {
        model.addAttribute("encryp_method", "md5");
        model.addAttribute("is_salt", 0);
        return "encryption/encryption";
    }

    @PostMapping("/encrypSub")
    public String encrypSub(@RequestParam("encryp_content") String encryp_content, @RequestParam("encryp_method") String encryp_method,
                            @RequestParam("is_salt") Integer is_salt, ModelMap modelMap) {
        try {
            modelMap.put("encryp_content",encryp_content);
            modelMap.put("encryp_method",encryp_method);
            modelMap.put("is_salt",is_salt);
            if (is_salt == 1) {
                encryp_content = encryp_content + EncrptionUtil.generateSalt(64);
            }
            switch (encryp_method) {
                case "md5": modelMap.put("result", EncrptionUtil.md5(encryp_content));break;
                case "sha1": modelMap.put("result", EncrptionUtil.sha1(encryp_content));break;
                case "sha2": modelMap.put("result", EncrptionUtil.sha256(encryp_content));break;
                default:break;
            }
            return "encryption/encryption";
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

}