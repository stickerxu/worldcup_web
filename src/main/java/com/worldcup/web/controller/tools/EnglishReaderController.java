package com.worldcup.web.controller.tools;

import com.worldcup.web.util.EnglishReaderUtil;
import com.worldcup.web.util.ParameterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/tools/engread")
public class EnglishReaderController {
    @GetMapping("")
    public String page() {
        return "tools/engread/index";
    }
    @PostMapping("/read")
    public String read(HttpServletRequest request, ModelMap modelMap) {
        String content = request.getParameter("content");
        MultipartRequest mrequest = (MultipartRequest) request;
        MultipartFile file = mrequest.getFile("file");
//        String filename = file.getOriginalFilename();
//        String suffix = filename.substring(filename.lastIndexOf("."));
        //文件类型校验
        Map<String, Integer> map = new HashMap<>();
        if (ParameterUtil.isNotBlank(content)) {
            map = EnglishReaderUtil.readContent(content);
        } else {
            try {
                map = EnglishReaderUtil.readFile(file.getInputStream());
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list,(o1, o2) -> -o1.getValue().compareTo(o2.getValue()));
        Map<Integer, List<Map.Entry<String, Integer>>> mapList = cutList(list, 20);
        modelMap.put("mapList", mapList);
        modelMap.put("listSize", list.size());
        return "tools/engread/result";
    }
    private Map<Integer, List<Map.Entry<String, Integer>>> cutList(List<Map.Entry<String, Integer>> list, int cutNumber) {
        Map<Integer, List<Map.Entry<String, Integer>>> map = new LinkedHashMap<>();
        int listSize = list.size();
        if (listSize > cutNumber) {
            int cutSize = list.size() % cutNumber == 0 ? list.size()/cutNumber : list.size()/cutNumber + 1;
            int bgSize;
            int endSize;
            for (int i = 0; i < cutSize; i++) {
                bgSize = i*cutNumber;
                endSize = bgSize+cutNumber;
                map.put(bgSize, list.subList(bgSize, endSize > listSize ? listSize : endSize));
            }
        } else {
            map.put(0, list);
        }
        return map;
    }
}
