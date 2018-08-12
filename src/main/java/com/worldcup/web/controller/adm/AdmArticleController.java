package com.worldcup.web.controller.adm;

import com.worldcup.web.Constants;
import com.worldcup.web.entity.Article;
import com.worldcup.web.entity.OperateResult;
import com.worldcup.web.service.ArticleService;
import com.worldcup.web.util.ParameterUtil;
import com.worldcup.web.util.ResponsePageUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/adm/article")
public class AdmArticleController {
    @Autowired
    private ArticleService articleService;

    @Value("${web.article.path.mac}")
    private String webArticlePath;

    @RequestMapping({"", "/"})
    public String list(HttpServletRequest request, ModelMap modelMap) {
        Integer type = ParameterUtil.parseInteger(request.getParameter("type"));
        String title = request.getParameter("title");
        Integer page = ParameterUtil.parseInteger(request.getParameter("page"));
        Integer defType = 0;
        if (type != null) {
            defType = type;
        }
        Article article = new Article();
        //todo 分页
        article.setBgSize(0);
        article.setCtSize(20);
        article.setType(defType);
        modelMap.put("type", defType);
        if (ParameterUtil.isNotBlank(title)) {
            article.setTitle(title);
            modelMap.put("title", title);
        }
        List<Article> articles = articleService.listArticleByCriteria(article);
        modelMap.put("resultList", articles);
        return "adm/article/list";
    }

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
            Path dirPath = Paths.get(webArticlePath, String.valueOf(article.getType()));
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Files.copy(file.getInputStream(), Paths.get(dirPath.toString(), builder.toString()), StandardCopyOption.REPLACE_EXISTING);
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
