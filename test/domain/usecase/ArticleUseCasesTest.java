package domain.usecase;

import domain.model.Article;
import domain.repository.ArticleRepository;
import domain.repository.UserRepository;
import domain.validator.ArticleValidator;
import domain.validator.IdValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleUseCasesTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserRepository userRepository;

    private ArticleValidator articleValidator;
    private final IdValidator idValidator = new IdValidator();

    @BeforeEach
    void setUp() {
        articleValidator = new ArticleValidator(userRepository);
    }

    @Test
    void addArticlePersistsValidArticle() {
        when(userRepository.existsById(7)).thenReturn(true);
        Article article = article();

        new AddArticleUseCase(articleRepository, articleValidator).execute(article);

        verify(articleRepository).addArticle(article);
    }

    @Test
    void addArticleDoesNotPersistWhenAuthorIsMissing() {
        when(userRepository.existsById(7)).thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> new AddArticleUseCase(articleRepository, articleValidator).execute(article())
        );
        verify(articleRepository, never()).addArticle(any());
    }

    @Test
    void editArticleUpdatesFieldsAndSaves() {
        when(userRepository.existsById(7)).thenReturn(true);
        Article existing = article();
        when(articleRepository.getArticleById(1)).thenReturn(existing);

        new EditArticleUseCase(articleRepository, articleValidator, idValidator)
                .execute(1, "New title", "Updated content", Article.Status.MODERATING);

        assertEquals("New title", existing.getTitle());
        assertEquals("Updated content", existing.getContent());
        assertEquals(Article.Status.MODERATING, existing.getStatus());
        verify(articleRepository).editArticle(existing);
    }

    @Test
    void editArticleRejectsInvalidId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new EditArticleUseCase(articleRepository, articleValidator, idValidator)
                        .execute(0, "New title", "Updated content", Article.Status.PENDING)
        );
        verify(articleRepository, never()).getArticleById(0);
        verify(articleRepository, never()).editArticle(any());
    }

    @Test
    void editArticleDoesNotSaveWhenValidationFails() {
        when(userRepository.existsById(7)).thenReturn(true);
        when(articleRepository.getArticleById(1)).thenReturn(article());

        assertThrows(
                IllegalArgumentException.class,
                () -> new EditArticleUseCase(articleRepository, articleValidator, idValidator)
                        .execute(1, "ok", "short", Article.Status.PENDING)
        );
        verify(articleRepository, never()).editArticle(any());
    }

    @Test
    void getArticleByIdReturnsRepositoryResult() {
        Article existing = article();
        when(articleRepository.getArticleById(1)).thenReturn(existing);

        Article result = new GetArticleByIdUseCase(articleRepository, idValidator).execute(1);

        assertSame(existing, result);
    }

    @Test
    void deleteArticleDeletesAfterIdCheck() {
        new DeleteArticleUseCase(articleRepository, idValidator).execute(4);

        verify(articleRepository).deleteArticle(4);
    }

    @Test
    void getAndDeleteRejectInvalidId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GetArticleByIdUseCase(articleRepository, idValidator).execute(-1)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new DeleteArticleUseCase(articleRepository, idValidator).execute(0)
        );
        verify(articleRepository, never()).getArticleById(-1);
        verify(articleRepository, never()).deleteArticle(0);
    }

    private static Article article() {
        return new Article(1, 7, "Title", "Long enough", Article.Status.PENDING, null);
    }
}
