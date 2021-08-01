package app.service;

import app.db.ArticleRepository;
import app.db.UserRepository;
import app.model.Article;
import app.model.ArticleDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Article createArticle(Article userData, String owner) {
        Article article = new Article();
        article.setAuthor(userRepository.findByUsername(owner));
        article.setName(userData.getName());
        article.setText(userData.getText());
        article.setDeleted(false);
        articleRepository.save(article);
        return article;
    }

    public boolean isNameVacant(String name) {
        return !(articleRepository.findByName(name) != null);
    }

    public List<ArticleDTO> getAllArticleId(){
        ArrayList<Article> articles = articleRepository.findAllIdAndName();
        return ArticleDTO.articleDTOListTOGet(articles);
    }

    public Article getArticleById(long id){
        if (articleRepository.findById(id).isPresent())
            return articleRepository.findById(id).get();
        else return null;
    }

    public void updateArticle(Article userData){
        articleRepository.update(userData.getText(), userData.getName());
    }

    public void deleteArticle(long id){
        articleRepository.delete(id);
    }
}
