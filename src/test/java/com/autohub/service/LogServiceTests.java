package com.autohub.service;

import com.autohub.domain.enums.LogType;
import com.autohub.domain.model.service.LogServiceModel;
import com.autohub.repository.LogRepository;
import com.autohub.service.implementations.LogServiceImpl;
import com.autohub.service.interfaces.LogService;
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

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LogServiceTests {
    @Autowired
    private LogRepository logRepository;
    private LogService logService;
    private ModelMapper modelMapper;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
        this.logService = new LogServiceImpl(this.logRepository, this.modelMapper);
    }

    @Test
    public void save_WithCorrectParams_returnsServiceModel() {
        LogServiceModel logServiceModel = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        LogServiceModel expected = this.logService.save(logServiceModel);
        LogServiceModel actual = this.modelMapper
                .map(this.logRepository.findAll().get(0), LogServiceModel.class);
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getMessage(), actual.getMessage());
        Assert.assertEquals("Save method does not function properly.", expected.getDate(), actual.getDate());
        Assert.assertEquals("Save method does not function properly.", expected.getType(), actual.getType());
    }

    @Test
    public void save_WithNoParams_returnsNull() {
        LogServiceModel logServiceModel = this.logService.save(new LogServiceModel());
        Assert.assertNull(logServiceModel.getMessage());
    }

    @Test
    public void findAllById_returnsCorrectModel() {
        LogServiceModel expected = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        this.logService.save(expected);
        LogServiceModel actual = this.logService.findById(this.logRepository.findAll().get(0).getId());
        Assert.assertEquals("Save method does not function properly.", expected.getMessage(), actual.getMessage());
        Assert.assertEquals("Save method does not function properly.", expected.getDate(), actual.getDate());
        Assert.assertEquals("Save method does not function properly.", expected.getType(), actual.getType());
    }

    @Test
    public void findAllById_WithIncorrectId_returnsEmptyList() {
        LogServiceModel expected = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        this.logService.save(expected);
        LogServiceModel actual = this.logService.findById("123");
        Assert.assertNull(actual);
    }

    @Test
    public void deleteById_deletesWithCorrectId() {
        LogServiceModel expected = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        this.logService.save(expected);
        this.logService.deleteById(this.logRepository.findAll().get(0).getId());
        Assert.assertEquals(0, this.logRepository.count());
    }

    @Test(expected = Exception.class)
    public void articleService_deleteByIdWithNonExistentId_throwsException() {
        LogServiceModel expected = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        this.logService.save(expected);
        this.logService.deleteById("123");
        Assert.assertEquals(1, this.logRepository.count());
    }

    @Test
    public void findAll_withEntities_returnsList() {
        LogServiceModel expected = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        this.logService.save(expected);
        LogServiceModel logServiceModel = new LogServiceModel() {{
            setType(LogType.INFO);
            setDate(LocalDateTime.now());
            setMessage("message");
        }};
        this.logService.save(logServiceModel);
        Assert.assertEquals(2, this.logService.findAll().size());
    }

    @Test
    public void findAll_withoutEntities_returnsEmptyList() {
        this.logRepository.deleteAll();
        Assert.assertEquals(0, this.logService.findAll().size());
    }
}
