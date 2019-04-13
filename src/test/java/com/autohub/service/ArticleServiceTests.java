package com.autohub.service;

import com.autohub.domain.enums.ArticleType;
import com.autohub.domain.model.service.ArticleServiceModel;
import com.autohub.repository.ArticleRepository;
import com.autohub.service.implementations.ArticleServiceImpl;
import com.autohub.service.interfaces.ArticleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ArticleServiceTests {
    @Autowired
    private ArticleRepository articleRepository;
    private ModelMapper modelMapper;
    private ArticleService articleService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.articleService = new ArticleServiceImpl(articleRepository, modelMapper);
    }

    @Test
    public void articleService_saveCarWithCorrectParams_returnsServiceModel() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel expected = this.articleService.save(articleServiceModel);
        ArticleServiceModel actual = this.modelMapper
                .map(this.articleRepository.findAll().get(0), ArticleServiceModel.class);
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getTitle(), actual.getTitle());
        Assert.assertEquals("Save method does not function properly.", expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals("Save method does not function properly.", expected.getContent(), actual.getContent());
        Assert.assertEquals("Save method does not function properly.", expected.getType(), actual.getType());
    }

    @Test
    public void articleService_saveArticleWithNoParams_returnsNull() {
        ArticleServiceModel articleServiceModel = this.articleService.save(new ArticleServiceModel());
        Assert.assertNull(articleServiceModel.getContent());
    }

    @Test
    public void articleService_findAllByType_returnsOnlyGivenType() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel articleServiceModel2 = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.TIPS_AND_TRICKS);
        }};
        this.articleService.save(articleServiceModel);
        this.articleService.save(articleServiceModel2);
        int numberOfArticles = this.articleRepository.findAllByType(ArticleType.NEWS).size();
        Assert.assertEquals(1, numberOfArticles);
    }

    @Test
    public void articleService_findAllByTypeWithNoArticles_returnsEmptyList() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        this.articleService.save(articleServiceModel);
        int numberOfArticles = this.articleRepository.findAllByType(ArticleType.SPORTS).size();
        Assert.assertEquals(0, numberOfArticles);
    }

    @Test
    public void articleService_deleteById_deletesWithCorrectId() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        this.articleService.save(articleServiceModel);
        this.articleService.deleteById(this.articleRepository.findAll().get(0).getId());
        Assert.assertEquals(0, this.articleRepository.count());
    }

    @Test(expected = Exception.class)
    public void articleService_deleteByIdWithNonExistentId_throwsException() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel articleServiceModel1 = this.articleService.save(articleServiceModel);
        this.articleService.deleteById("123");
    }

    @Test
    public void articleService_findById_returnsCorrectModel() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel expected = this.articleService.save(articleServiceModel);
        ArticleServiceModel actual = this.articleService.findById(this.articleRepository.findAll().get(0).getId());
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getTitle(), actual.getTitle());
        Assert.assertEquals("Save method does not function properly.", expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals("Save method does not function properly.", expected.getContent(), actual.getContent());
        Assert.assertEquals("Save method does not function properly.", expected.getType(), actual.getType());    }

    @Test
    public void articleService_findByIdWithNonExistentId_returnsNull() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel expectes = this.articleService.save(articleServiceModel);
        ArticleServiceModel actual = this.articleService.findById("123");
        Assert.assertNull(actual);
    }

    @Test
    public void articleService_updateWithCorrectParameters_returnsCorrectModel() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel saved = this.articleService.save(articleServiceModel);
        ArticleServiceModel newModel = new ArticleServiceModel() {{
            setTitle("New");
            setContent("New Content");
            setType(ArticleType.NEWS);
        }};
        newModel.setId(saved.getId());
        ArticleServiceModel expected = this.articleService.update(newModel);
        ArticleServiceModel actual = this.articleService.findById(newModel.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getTitle(), actual.getTitle());
        Assert.assertEquals("Save method does not function properly.", expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals("Save method does not function properly.", expected.getContent(), actual.getContent());
        Assert.assertEquals("Save method does not function properly.", expected.getType(), actual.getType());
    }

    @Test
    public void articleService_updateWithNoParameters_returnsNull() {
        ArticleServiceModel articleServiceModel = new ArticleServiceModel() {{
            setTitle("Article");
            setContent("Content");
            setType(ArticleType.NEWS);
        }};
        ArticleServiceModel saved = this.articleService.save(articleServiceModel);
        ArticleServiceModel newModel = new ArticleServiceModel();
        newModel.setId(saved.getId());
        ArticleServiceModel expected = this.articleService.update(newModel);
        ArticleServiceModel actual = this.articleService.findById(newModel.getId());
        Assert.assertNull(actual.getContent());
    }
}
