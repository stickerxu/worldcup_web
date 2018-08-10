package com.cup.worldcup.controller.adm;

import com.cup.worldcup.Constants;
import com.cup.worldcup.entity.Article;
import com.cup.worldcup.entity.OperateResult;
import com.cup.worldcup.service.ArticleService;
import com.cup.worldcup.util.ResponsePageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@Slf4j
@RequestMapping("/adm/article")
public class AdmArticleController {
    @Autowired
    private ArticleService articleService;

    @Value("${web.article.path.dev}")
    private String webArticlePath;

    @GetMapping("/add")
    public String add() {
        return "adm/article/add";
    }
    @PostMapping("/addSub")
    public String addSub(Article article, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        //校验文件类型
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if (!suffix.equals(Constants.FILE_SUFFIX_HTML)) {
            return ResponsePageUtil.errorPage("文件类型有误! 请选择html格式文件", modelMap);
        }
        try {
            Integer maxArticleId = articleService.getMaxArticleIdByType(article.getType());
            StringBuilder builder = new StringBuilder();
            builder.append("article_").append(article.getType()).append("_").append(++maxArticleId)
                    .append(suffix);
            //上传
            Files.copy(file.getInputStream(), Paths.get(webArticlePath, builder.toString()), StandardCopyOption.REPLACE_EXISTING);
            //入库
            article.setFile_name(builder.toString());
            articleService.saveArticle(article);
            log.info("添加文章：{}",article.getFile_name());
            //返回结果
            OperateResult operateResult = new OperateResult("文章添加成功","文章已在首页中显示！");
            modelMap.put("operateResult",operateResult);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "error";
        }
        return "success";
    }
}
