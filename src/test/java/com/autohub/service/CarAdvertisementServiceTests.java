package com.autohub.service;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import com.autohub.domain.model.service.AddressServiceModel;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.EngineServiceModel;
import com.autohub.repository.AddressRepository;
import com.autohub.repository.CarAdvertisementRepository;
import com.autohub.repository.CarRepository;
import com.autohub.repository.EngineRepository;
import com.autohub.service.implementations.AddressServiceImpl;
import com.autohub.service.implementations.CarAdvertisementServiceImpl;
import com.autohub.service.implementations.CarServiceImpl;
import com.autohub.service.implementations.EngineServiceImpl;
import com.autohub.service.interfaces.AddressService;
import com.autohub.service.interfaces.CarAdvertisementService;
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
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarAdvertisementServiceTests {
    @Autowired
    private CarAdvertisementRepository carAdvertisementRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EngineRepository engineRepository;
    private CarAdvertisementService carAdvertisementService;
    private CarService carService;
    private EngineService engineService;
    private AddressService addressService;
    private ModelMapper modelMapper;
    private CarAdvertisementServiceModel carAdvertisement;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.engineService = new EngineServiceImpl(engineRepository, modelMapper);
        this.addressService = new AddressServiceImpl(addressRepository, modelMapper);
        this.carService = new CarServiceImpl(carRepository, engineService, modelMapper);
        this.carAdvertisementService = new CarAdvertisementServiceImpl(this.carAdvertisementRepository, this.carService, this.engineService, this.addressService, this.modelMapper);
        EngineServiceModel engine = new EngineServiceModel() {{
            setFuelType(FuelType.CNG);
            setHorsepower(25L);
            setModification("i");
            setVolume(new BigDecimal(2.5));
        }};
        AddressServiceModel address = new AddressServiceModel() {{
            setCity("Sofia");
            setCountry("Bulgaria");
            setProvince("Sofia");
        }};
        CarServiceModel car = new CarServiceModel() {{
            setEngine(engine);
            setColor("red");
            setMake("Ford");
            setModel("Fiesta");
            setMileage(200000L);
            setProductionDate(new Date());
            setType(CarType.COMPACT);
        }};
        this.carAdvertisement = new CarAdvertisementServiceModel() {{
            setCar(car);
            setAddress(address);
            setDescription("description");
            setPrice(new BigDecimal(25000.00));
        }};
    }

    @Test
    public void save_withCorrectParameters_returnsModel() {
        CarAdvertisementServiceModel save = this.carAdvertisementService.save(this.carAdvertisement);
        CarAdvertisementServiceModel expected = this.modelMapper.map(this.carAdvertisementRepository.findAll().get(0), CarAdvertisementServiceModel.class);
        Assert.assertEquals(save.getCar().getMake(), expected.getCar().getMake());
        Assert.assertEquals(save.getCar().getEngine().getModification(), expected.getCar().getEngine().getModification());
        Assert.assertEquals(save.getAddress().getCity(), expected.getAddress().getCity());
        Assert.assertEquals(save.getDescription(), expected.getDescription());
    }

    @Test
    public void save_withIncorrectParameters_returnsNull() {
        this.carAdvertisementRepository.deleteAll();
        CarAdvertisementServiceModel save = this.carAdvertisementService.save(new CarAdvertisementServiceModel());
        Assert.assertNull(save);
    }

    @Test
    public void findById_withCorrectId_returnsModel(){
        this.carAdvertisementRepository.deleteAll();
        CarAdvertisementServiceModel save = this.carAdvertisementService.save(this.carAdvertisement);
        CarAdvertisementServiceModel actual = this.carAdvertisementService.findById(this.carAdvertisementRepository.findAll().get(0).getId());
        Assert.assertEquals(save.getCar().getMake(), actual.getCar().getMake());
        Assert.assertEquals(save.getCar().getEngine().getModification(), actual.getCar().getEngine().getModification());
        Assert.assertEquals(save.getAddress().getCity(), actual.getAddress().getCity());
        Assert.assertEquals(save.getDescription(), actual.getDescription());
    }

    @Test()
    public void findAllByUserId(){
        List<CarAdvertisementServiceModel> allByUserId = this.carAdvertisementService.findAll();
        Assert.assertEquals(0, allByUserId.size());
    }

    @Test()
    public void findAll(){
        this.carAdvertisementRepository.deleteAll();
        CarAdvertisementServiceModel save = this.carAdvertisementService.save(this.carAdvertisement);
        List<CarAdvertisementServiceModel> allByUserId = this.carAdvertisementService.findAll();
        Assert.assertEquals(1, allByUserId.size());
    }

    @Test
    public void changeStatus_withCorrectParameters_changesTheStatus(){
        this.carAdvertisementRepository.deleteAll();
        this.carAdvertisementService.save(this.carAdvertisement);
        this.carAdvertisementService.changeAdvertisementStatus(this.carAdvertisementRepository.findAll().get(0).getId(), AdvertisementStatus.DECLINED);
        Assert.assertEquals(AdvertisementStatus.DECLINED, this.carAdvertisementRepository.findAll().get(0).getStatus());
    }

    @Test
    public void deleteById_withCorrectId_deletesWithCorrectId() {
        this.carAdvertisementRepository.deleteAll();
        this.carAdvertisementService.save(this.carAdvertisement);
        this.carAdvertisementService.deleteById(this.carAdvertisementRepository.findAll().get(0).getId());
        Assert.assertEquals(0, this.carAdvertisementRepository.count());
    }

    @Test(expected = Exception.class)
    public void deleteById_withCorrectId_throwsException() {
        this.carAdvertisementRepository.deleteAll();
        this.carAdvertisementService.save(this.carAdvertisement);
        this.carAdvertisementService.deleteById("123");
    }
}
