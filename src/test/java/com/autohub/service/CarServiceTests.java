package com.autohub.service;

import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.EngineServiceModel;
import com.autohub.repository.CarRepository;
import com.autohub.repository.EngineRepository;
import com.autohub.service.implementations.CarServiceImpl;
import com.autohub.service.implementations.EngineServiceImpl;
import com.autohub.service.interfaces.CarService;
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
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private EngineRepository engineRepository;
    private EngineService engineService;
    private ModelMapper modelMapper;
    private CarService carService;
    private CarServiceModel car;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.engineService = new EngineServiceImpl(engineRepository, modelMapper);
        this.carService = new CarServiceImpl(carRepository, engineService, modelMapper);
        EngineServiceModel engine = new EngineServiceModel() {{
            setFuelType(FuelType.CNG);
            setHorsepower(25L);
            setModification("i");
            setVolume(new BigDecimal(2.5));
        }};
        engine = this.engineService.save(engine);
        EngineServiceModel finalEngine = engine;
        car = new CarServiceModel() {{
            setEngine(finalEngine);
            setColor("red");
            setMake("Ford");
            setModel("Fiesta");
            setMileage(200000L);
            setProductionDate(new Date());
            setType(CarType.COMPACT);
        }};
    }

    @Test
    public void save_withCorrectParameters_savesEntity() {
        CarServiceModel actual = this.carService.save(car);
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        Assert.assertEquals(actual.getMake(), expected.getMake());
        Assert.assertEquals(actual.getColor(), expected.getColor());
        Assert.assertEquals(actual.getProductionDate(), expected.getProductionDate());
        Assert.assertEquals(actual.getMileage(), expected.getMileage());
        Assert.assertEquals(actual.getType(), expected.getType());
        Assert.assertEquals(actual.getEngine().getHorsepower(), expected.getEngine().getHorsepower());
    }

    @Test(expected = Exception.class)
    public void save_withIncorrectParameters_throwsException() {
        CarServiceModel actual = this.carService.save(new CarServiceModel());
        Assert.assertNull(actual.getMake());
    }

    @Test
    public void findById_withCorrectId_returnsEntity() {
        CarServiceModel actual = this.carService.save(car);
        CarServiceModel expected = this.carService.findById(this.carRepository.findAll().get(0).getId());
        Assert.assertEquals(actual.getMake(), expected.getMake());
        Assert.assertEquals(actual.getColor(), expected.getColor());
        Assert.assertEquals(actual.getProductionDate(), expected.getProductionDate());
        Assert.assertEquals(actual.getMileage(), expected.getMileage());
        Assert.assertEquals(actual.getType(), expected.getType());
        Assert.assertEquals(actual.getEngine().getHorsepower(), expected.getEngine().getHorsepower());
    }

    @Test
    public void findById_withIncorrectId_returnsNull() {
        CarServiceModel actual = this.carService.save(car);
        CarServiceModel expected = this.carService.findById("123");
        Assert.assertNull(expected);
    }
}
