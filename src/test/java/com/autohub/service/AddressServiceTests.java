package com.autohub.service;

import com.autohub.domain.model.service.AddressServiceModel;
import com.autohub.repository.AddressRepository;
import com.autohub.service.implementations.AddressServiceImpl;
import com.autohub.service.interfaces.AddressService;
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
public class AddressServiceTests {
    @Autowired
    private AddressRepository addressRepository;
    private ModelMapper modelMapper;
    private AddressService addressService;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.addressService = new AddressServiceImpl(addressRepository, modelMapper);
    }

    @Test
    public void save_WithCorrectParams_returnsServiceModel() {
        AddressServiceModel addressServiceModel = new AddressServiceModel() {{
            setCity("Sofia");
            setCountry("Bulgaria");
            setProvince("Sofia");
        }};
        AddressServiceModel expected = this.addressService.save(addressServiceModel);
        AddressServiceModel actual = this.modelMapper
                .map(this.addressRepository.findAll().get(0), AddressServiceModel.class);
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getCity(), actual.getCity());
        Assert.assertEquals("Save method does not function properly.", expected.getCity(), actual.getCity());
        Assert.assertEquals("Save method does not function properly.", expected.getProvince(), actual.getProvince());
    }

    @Test
    public void carService_saveCarWithNoParams_throwsException() {
        Assert.assertNull(this.addressService.save(new AddressServiceModel()).getCity());
    }

    @Test
    public void addressService_findByIdWithCorrectId_returnsServiceModel() {
        AddressServiceModel addressServiceModel = new AddressServiceModel() {{
            setCity("Sofia");
            setCountry("Bulgaria");
            setProvince("Sofia");
        }};
        AddressServiceModel expected = this.addressService.save(addressServiceModel);
        AddressServiceModel actual = this.addressService.findById(expected.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getId(), actual.getId());
        Assert.assertEquals("Save method does not function properly.", expected.getCity(), actual.getCity());
        Assert.assertEquals("Save method does not function properly.", expected.getCity(), actual.getCity());
        Assert.assertEquals("Save method does not function properly.", expected.getProvince(), actual.getProvince());
    }

    @Test
    public void addressService_findByIdWithIncorrectId_returnsNull() {
        AddressServiceModel actual = this.addressService.findById("123");
        Assert.assertNull("Save method does not function properly.", actual);
    }
}
