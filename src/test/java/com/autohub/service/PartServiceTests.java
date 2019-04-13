package com.autohub.service;

import com.autohub.domain.entity.Part;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.PartServiceModel;
import com.autohub.repository.PartRepository;
import com.autohub.service.implementations.PartServiceImpl;
import com.autohub.service.interfaces.PartService;
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
public class PartServiceTests {
    @Autowired
    private PartRepository partRepository;
    private ModelMapper modelMapper;
    private PartService partService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.partService = new PartServiceImpl(partRepository, modelMapper);
    }

    @Test
    public void save_withCorrectParameters_savesEntity() {
        PartServiceModel part = new PartServiceModel(){{
           setName("Part");
           setManufacturer("Company");
           setCarSuitableFor("Ford");
        }};
        PartServiceModel actual = this.partService.save(part);
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getManufacturer(), expected.getManufacturer());
        Assert.assertEquals(actual.getCarSuitableFor(), expected.getCarSuitableFor());
    }

    @Test
    public void save_withIncorrectParameters_throwsException() {
        PartServiceModel actual = this.partService.save(new PartServiceModel());
        Assert.assertNull(actual.getName());
    }

    @Test
    public void findById_withCorrectId_returnsEntity() {
        PartServiceModel part = new PartServiceModel(){{
            setName("Part");
            setManufacturer("Company");
            setCarSuitableFor("Ford");
        }};
        PartServiceModel actual = this.partService.save(part);
        PartServiceModel expected = this.partService.findById(this.partRepository.findAll().get(0).getId());
        Assert.assertEquals(actual.getName(), expected.getName());
        Assert.assertEquals(actual.getManufacturer(), expected.getManufacturer());
        Assert.assertEquals(actual.getCarSuitableFor(), expected.getCarSuitableFor());
    }

    @Test
    public void findById_withIncorrectId_returnsNull() {
        PartServiceModel part = new PartServiceModel(){{
            setName("Part");
            setManufacturer("Company");
            setCarSuitableFor("Ford");
        }};
        PartServiceModel actual = this.partService.save(part);
        PartServiceModel expected = this.partService.findById("123");
        Assert.assertNull(expected);
    }
}
