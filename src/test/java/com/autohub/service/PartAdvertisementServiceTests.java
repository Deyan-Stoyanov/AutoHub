package com.autohub.service;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.AddressServiceModel;
import com.autohub.domain.model.service.PartAdvertisementServiceModel;
import com.autohub.domain.model.service.PartServiceModel;
import com.autohub.repository.AddressRepository;
import com.autohub.repository.CarAdvertisementRepository;
import com.autohub.repository.PartAdvertisementRepository;
import com.autohub.repository.PartRepository;
import com.autohub.service.implementations.AddressServiceImpl;
import com.autohub.service.implementations.PartAdvertisementServiceImpl;
import com.autohub.service.implementations.PartServiceImpl;
import com.autohub.service.interfaces.AddressService;
import com.autohub.service.interfaces.PartAdvertisementService;
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

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartAdvertisementServiceTests {
    @Autowired
    private PartAdvertisementRepository partAdvertisementRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private AddressRepository addressRepository;
    private AddressService addressService;
    private PartService partService;
    private ModelMapper modelMapper;
    private PartAdvertisementService partAdvertisementService;
    private PartAdvertisementServiceModel partAdvertisementServiceModel;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.addressService = new AddressServiceImpl(addressRepository, modelMapper);
        this.partService = new PartServiceImpl(partRepository, modelMapper);
        this.partAdvertisementService = new PartAdvertisementServiceImpl(partAdvertisementRepository, partService, addressService, modelMapper);
        AddressServiceModel address = new AddressServiceModel() {{
            setCity("Sofia");
            setCountry("Bulgaria");
            setProvince("Sofia");
        }};
        PartServiceModel part = new PartServiceModel() {{
            setName("Part");
            setManufacturer("Company");
            setCarSuitableFor("Ford");
        }};
        this.partAdvertisementServiceModel = new PartAdvertisementServiceModel() {{
            setDescription("description");
            setPrice(new BigDecimal(25000.00));
            setAddress(address);
            setPart(part);
        }};
    }

    @Test
    public void save_withCorrectParameters_returnsModel() {
        PartAdvertisementServiceModel save = this.partAdvertisementService.save(this.partAdvertisementServiceModel);
        PartAdvertisementServiceModel expected = this.modelMapper.map(this.partAdvertisementRepository.findAll().get(0), PartAdvertisementServiceModel.class);
        Assert.assertEquals(save.getPart().getName(), expected.getPart().getName());
        Assert.assertEquals(save.getAddress().getCity(), expected.getAddress().getCity());
        Assert.assertEquals(save.getDescription(), expected.getDescription());
    }

    @Test
    public void save_withIncorrectParameters_returnsNull() {
        this.partAdvertisementRepository.deleteAll();
        PartAdvertisementServiceModel save = this.partAdvertisementService.save(new PartAdvertisementServiceModel());
        Assert.assertNull(save);
    }

    @Test
    public void findById_withCorrectId_returnsModel() {
        this.partAdvertisementRepository.deleteAll();
        PartAdvertisementServiceModel save = this.partAdvertisementService.save(this.partAdvertisementServiceModel);
        PartAdvertisementServiceModel actual = this.partAdvertisementService.findById(this.partAdvertisementRepository.findAll().get(0).getId());
        Assert.assertEquals(save.getPart().getName(), actual.getPart().getName());
        Assert.assertEquals(save.getAddress().getCity(), actual.getAddress().getCity());
        Assert.assertEquals(save.getDescription(), actual.getDescription());
    }

    @Test()
    public void findAllByUserId() {
        List<PartAdvertisementServiceModel> allByUserId = this.partAdvertisementService.findAll();
        Assert.assertEquals(0, allByUserId.size());
    }

    @Test()
    public void findAll() {
        this.partAdvertisementRepository.deleteAll();
        PartAdvertisementServiceModel save = this.partAdvertisementService.save(this.partAdvertisementServiceModel);
        List<PartAdvertisementServiceModel> allByUserId = this.partAdvertisementService.findAll();
        Assert.assertEquals(1, allByUserId.size());
    }

    @Test
    public void changeStatus_withCorrectParameters_changesTheStatus() {
        this.partAdvertisementRepository.deleteAll();
        this.partAdvertisementService.save(this.partAdvertisementServiceModel);
        this.partAdvertisementService.changeAdvertisementStatus(this.partAdvertisementRepository.findAll().get(0).getId(), AdvertisementStatus.DECLINED);
        Assert.assertEquals(AdvertisementStatus.DECLINED, this.partAdvertisementRepository.findAll().get(0).getStatus());
    }

    @Test
    public void deleteById_withCorrectId_deletesWithCorrectId() {
        this.partAdvertisementRepository.deleteAll();
        this.partAdvertisementService.save(this.partAdvertisementServiceModel);
        this.partAdvertisementService.deleteById(this.partAdvertisementRepository.findAll().get(0).getId());
        Assert.assertEquals(0, this.partAdvertisementRepository.count());
    }

    @Test(expected = Exception.class)
    public void deleteById_withCorrectId_throwsException() {
        this.partAdvertisementRepository.deleteAll();
        this.partAdvertisementService.save(this.partAdvertisementServiceModel);
        this.partAdvertisementService.deleteById("123");
    }
}
