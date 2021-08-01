package app.service;

import app.db.ArticleRepository;
import app.db.HistoryRepository;
import app.db.UserRepository;
import app.model.Article;
import app.model.History;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public HistoryService(HistoryRepository historyRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }


    public History createRecord(Article article, String userName, String action) {
        History history = new History(new Date(),
                articleRepository.findByName(article.getName()),
                userRepository.findByUsername(userName),
                action);
        historyRepository.save(history);
        return history;
    }
}
