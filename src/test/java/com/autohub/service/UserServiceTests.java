package com.autohub.service;

import com.autohub.domain.entity.UserRole;
import com.autohub.domain.enums.Gender;
import com.autohub.domain.enums.Role;
import com.autohub.domain.model.service.UserRoleServiceModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.repository.UserRepository;
import com.autohub.repository.UserRoleRepository;
import com.autohub.service.implementations.UserRoleServiceImpl;
import com.autohub.service.implementations.UserServiceImpl;
import com.autohub.service.interfaces.UserRoleService;
import com.autohub.service.interfaces.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    private UserRoleService userRoleService;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder encoder;
    private UserService userService;
    private Set<UserRoleServiceModel> authorities;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.encoder = new BCryptPasswordEncoder();
        this.userRoleService = new UserRoleServiceImpl(userRoleRepository, modelMapper);
        this.userService = new UserServiceImpl(userRepository, userRoleService, modelMapper, encoder);
        this.authorities = new LinkedHashSet<>();
        UserRoleServiceModel role1 = new UserRoleServiceModel();
        role1.setRole(Role.ROLE_ADMIN);
        role1 = this.userRoleService.save(role1);
        authorities.add(role1);
        UserRoleServiceModel role2 = new UserRoleServiceModel();
        role2.setRole(Role.ROLE_USER);
        role2 = this.userRoleService.save(role2);
        authorities.add(role2);
        UserRoleServiceModel role3 = new UserRoleServiceModel();
        role3.setRole(Role.ROLE_ROOT);
        role3 = this.userRoleService.save(role3);
        authorities.add(role3);
    }

    @Test
    public void save_WithCorrectParams_returnsServiceModel() {
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setUsername("pesho");
            setPassword("password");
            setAge(25);
            setFirstName("Pesho");
            setLastName("Petrov");
            setEmail("pesho@abv.bg");
            setGender(Gender.MALE);
            setPhoneNumber("0885885885");
            setAuthorities(authorities);
        }};
        UserServiceModel expected = this.userService.save(userServiceModel);
        UserServiceModel actual = this.modelMapper
                .map(this.userRepository.findAll().get(0), UserServiceModel.class);
        Assert.assertEquals("Save method does not function properly.", expected.getEmail(), actual.getEmail());
        Assert.assertEquals("Save method does not function properly.", expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Save method does not function properly.", expected.getPassword(), actual.getPassword());
    }

    @Test
    public void save_WithIncorrectParams_returnsNull() {
        UserServiceModel userServiceModel = new UserServiceModel();
        UserServiceModel expected = this.userService.save(userServiceModel);
        Assert.assertNull(expected);
    }

    @Test
    public void register_WithCorrectParams_returnsServiceModel() {
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setUsername("pesho");
            setPassword("password");
            setAge(25);
            setFirstName("Pesho");
            setLastName("Petrov");
            setEmail("pesho@abv.bg");
            setGender(Gender.MALE);
            setPhoneNumber("0885885885");
        }};
        UserServiceModel expected = this.userService.register(userServiceModel);
        UserServiceModel actual = this.modelMapper
                .map(this.userRepository.findAll().get(0), UserServiceModel.class);
        Assert.assertNotEquals(expected.getPassword(), "password");
        Assert.assertEquals("Save method does not function properly.", expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Save method does not function properly.", expected.getPassword(), actual.getPassword());
    }

    @Test(expected = Exception.class)
    public void register_WithIncorrectParams_throwsException() {
        UserServiceModel userServiceModel = new UserServiceModel();
        UserServiceModel expected = this.userService.register(userServiceModel);
    }

    @Test
    public void findAll_withEntities_returnsList() {
        this.userRepository.deleteAll();
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setUsername("pesho");
            setPassword("password");
            setAge(25);
            setFirstName("Pesho");
            setLastName("Petrov");
            setEmail("pesho@abv.bg");
            setGender(Gender.MALE);
            setPhoneNumber("0885885885");
            setAuthorities(authorities);
        }};
        UserServiceModel save = this.userService.save(userServiceModel);
        List<UserServiceModel> allByUserId = this.userService.findAll();
        Assert.assertEquals(1, allByUserId.size());
    }

    @Test
    public void findAll_withNoEntities_returnsEmptyList() {
        this.userRepository.deleteAll();
        List<UserServiceModel> allByUserId = this.userService.findAll();
        Assert.assertEquals(0, allByUserId.size());
    }

    @Test
    public void findById_WithCorrectId_returnsServiceModel() {
        this.userRepository.deleteAll();
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setUsername("pesho");
            setPassword("password");
            setAge(25);
            setFirstName("Pesho");
            setLastName("Petrov");
            setEmail("pesho@abv.bg");
            setGender(Gender.MALE);
            setPhoneNumber("0885885885");
            setAuthorities(authorities);
        }};
        UserServiceModel expected = this.userService.save(userServiceModel);
        UserServiceModel actual = this.userService.findById(this.userRepository.findAll().get(0).getId());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getEmail(), actual.getEmail());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getPassword(), actual.getPassword());
    }

    @Test
    public void findById_WithIncorrectId_returnsNull() {
        UserServiceModel actual = this.userService.findById("123");
        Assert.assertNull("Save method does not function properly.", actual);
    }

    @Test
    public void findByUsername_WithCorrectUsername_returnsServiceModel() {
        this.userRepository.deleteAll();
        UserServiceModel userServiceModel = new UserServiceModel() {{
            setUsername("pesho");
            setPassword("password");
            setAge(25);
            setFirstName("Pesho");
            setLastName("Petrov");
            setEmail("pesho@abv.bg");
            setGender(Gender.MALE);
            setPhoneNumber("0885885885");
            setAuthorities(authorities);
        }};
        UserServiceModel expected = this.userService.save(userServiceModel);
        UserServiceModel actual = this.userService.findByUsername(this.userRepository.findAll().get(0).getUsername());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getEmail(), actual.getEmail());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Find by Id method does not function properly.", expected.getPassword(), actual.getPassword());
    }

    @Test
    public void findByUsername_WithIncorrectUsername_throwsException() {
        UserServiceModel actual = this.userService.findByUsername("asd");
        Assert.assertNull(actual);
    }
}
