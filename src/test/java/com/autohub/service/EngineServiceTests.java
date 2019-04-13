package com.autohub.service;

import com.autohub.domain.entity.Engine;
import com.autohub.domain.enums.FuelType;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.EngineServiceModel;
import com.autohub.repository.EngineRepository;
import com.autohub.service.implementations.EngineServiceImpl;
import com.autohub.service.interfaces.EngineService;
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

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EngineServiceTests {
    @Autowired
    private EngineRepository engineRepository;
    private ModelMapper modelMapper;
    private EngineService engineService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.engineService = new EngineServiceImpl(engineRepository, modelMapper);
    }

    @Test
    public void save_withCorrectParameters_returnsModel() {
        EngineServiceModel engine = new EngineServiceModel() {{
            setVolume(new BigDecimal(2.5));
            setModification("i");
            setHorsepower(200L);
            setFuelType(FuelType.CNG);
        }};
        EngineServiceModel actual = this.engineService.save(engine);
        EngineServiceModel expected = this.modelMapper.map(this.engineRepository.findAll().get(0), EngineServiceModel.class);
        Assert.assertEquals(actual.getHorsepower(), expected.getHorsepower());
        Assert.assertEquals(actual.getModification(), expected.getModification());
        Assert.assertEquals(actual.getVolume(), expected.getVolume());
        Assert.assertEquals(actual.getFuelType(), expected.getFuelType());
    }

    @Test
    public void save_withIncorrectParameters_returnsNull() {
        EngineServiceModel engine = new EngineServiceModel();
        EngineServiceModel actual = this.engineService.save(engine);
        Assert.assertNull(actual.getModification());
    }

    @Test
    public void findById_withCorrectId_returnsEntity() {
        EngineServiceModel engine = new EngineServiceModel() {{
            setVolume(new BigDecimal(2.5));
            setModification("i");
            setHorsepower(200L);
            setFuelType(FuelType.CNG);
        }};
        EngineServiceModel actual = this.engineService.save(engine);
        EngineServiceModel expected = this.engineService.findById(this.engineRepository.findAll().get(0).getId());
        Assert.assertEquals(actual.getHorsepower(), expected.getHorsepower());
        Assert.assertEquals(actual.getModification(), expected.getModification());
        Assert.assertEquals(actual.getVolume(), expected.getVolume());
        Assert.assertEquals(actual.getFuelType(), expected.getFuelType());
    }

    @Test
    public void findById_withIncorrectId_returnsNull() {
        EngineServiceModel engine = new EngineServiceModel() {{
            setVolume(new BigDecimal(2.5));
            setModification("i");
            setHorsepower(200L);
            setFuelType(FuelType.CNG);
        }};
        EngineServiceModel actual = this.engineService.save(engine);
        EngineServiceModel expected = this.engineService.findById("123");
        Assert.assertNull(expected);
    }
}
