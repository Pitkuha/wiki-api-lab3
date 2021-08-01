package app.db;

import app.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByName(String name);

    @Query("select a from Article a where a.deleted = false")
    ArrayList<Article> findAllIdAndName();

    @Transactional
    @Modifying
    @Query("update Article a set a.text = :data where a.name = :name")
    void update(@Param("data") String data, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update Article a set a.deleted = true where a.articleId = :id")
    void delete(@Param("id") long id);
}
