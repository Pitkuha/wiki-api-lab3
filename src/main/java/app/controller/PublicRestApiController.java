package app.controller;

import app.db.UserRepository;
import app.model.Article;
import app.model.ArticleDTO;
import app.model.User;
import app.service.ArticleService;
import app.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicRestApiController {
    private UserRepository userRepository;

    final static String queueName = "message_queue";

    @Autowired
    private ArticleService articleService;
    @Autowired
    private HistoryService historyService;

    public PublicRestApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Available to all authenticate users
    @GetMapping("test")
    public String test1(){

        System.out.println("Done");
        return "API test";
    }

    @PutMapping(value = "/createArticle", produces = "application/json;")
    public void createArticle(@RequestBody Article request, Principal principal, HttpServletResponse response) throws IOException {
        System.out.println(principal.getName());
        if (articleService.isNameVacant(request.getName())) {
            articleService.createArticle(request, principal.getName());
            historyService.createRecord(request, principal.getName(), "Создание статьи");
        } else {
            response.sendError(418, "Статья уже существует");
        }
        System.out.println("Создание статьи");
    }

    @GetMapping("/getAllArticles")
    public List<ArticleDTO> getAllArticle(){
        return articleService.getAllArticleId();
    }

    @GetMapping("/getArticle")
    public ArticleDTO getArticle(@RequestParam long id, HttpServletResponse response) throws IOException {
        Article article = articleService.getArticleById(id);
        if (article != null) {
            if (article.isDeleted()) {
                response.sendError(418, "Несуществующая статья");
                return null;
            } else {
                return ArticleDTO.articleToDTO(article);
            }
        } else {
            response.sendError(418, "Несуществующая статья");
            return null;
        }
    }

    //Available to managers
    @GetMapping("management/reports")
    public String reports(){
        return "Some report data";
    }

    @PostMapping(value = "management/updateArticle", produces = "application/json;")
    public void updateArticle(@RequestBody Article request, Principal principal, HttpServletResponse response) throws IOException {
        if (!articleService.isNameVacant(request.getName())) {
            articleService.updateArticle(request);
            historyService.createRecord(request, principal.getName(), "Редактирование");
        } else {
            response.sendError(418, "Такой статьи не существует.");
        }
    }


    //Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public List<User> users(){
        return this.userRepository.findAll();
    }

    @DeleteMapping(value = "admin/deleteArticle")
    @Transactional
    public void deleteArticle(@RequestParam long id, HttpServletResponse response, Principal principal){
        historyService.createRecord(articleService.getArticleById(id), principal.getName(), "Удаление");
        articleService.deleteArticle(id);
    }


}
