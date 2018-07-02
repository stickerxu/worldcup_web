package com.cup.worldcup.controller;

import com.cup.worldcup.util.EncrptionUtil;
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
import java.util.Map;

@Controller
public class EncryptionController {
    private final Logger logger = LoggerFactory.getLogger(EncryptionController.class);
    @GetMapping("/encryp")
    public String encryp(Model model) {
        model.addAttribute("encryp_method", "md5");
        return "encryp";
    }

    @PostMapping("/encrypSub")
    public String encrypSub(@RequestParam("encryp_content") String encryp_content, @RequestParam("encryp_method") String encryp_method, ModelMap modelMap) {
        try {
            switch (encryp_method) {
                case "md5": modelMap.put("result", EncrptionUtil.md5(encryp_content));break;
                case "sha1": modelMap.put("result", EncrptionUtil.sha1(encryp_content));break;
                case "sha2": modelMap.put("result", EncrptionUtil.sha256(encryp_content));break;
                default:break;
            }
            modelMap.put("encryp_content",encryp_content);
            modelMap.put("encryp_method",encryp_method);
            return "encryp";
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
