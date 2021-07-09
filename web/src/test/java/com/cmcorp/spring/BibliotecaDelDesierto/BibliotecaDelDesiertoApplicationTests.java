package com.cmcorp.spring.BibliotecaDelDesierto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cmcorp.spring.BibliotecaDelDesierto.model.User;
import com.cmcorp.spring.BibliotecaDelDesierto.repository.RepositorioUser;
import com.cmcorp.spring.BibliotecaDelDesierto.service.ServicioUser;

@SpringBootTest
@AutoConfigureMockMvc
class BibliotecaDelDesiertoApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	private MockMvc mvc;
	
	@Autowired
	private ServicioUser servicioUser;
	
	@MockBean
	private RepositorioUser repositorioUser;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.apply(springSecurity())
				.build();
	}
	
	
//	@Test
//	void contextLoads() throws Exception{
//		mockMvc.perform(get("/users").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType("application/json;charset=UTF-8"))
//        .andExpect(content().json("[{'id':1,'email':'manu@manu123.cl','nickname':'manuelalejandro20','password':'manu123','nombre':'Manuel','paterno':'Trigo','materno':'Montalban','direccion':'Palestina 1315','rol':'Usuario'}]"));	
//	}	
		
	@Test
	public void getUsersTest() throws Exception{
		when(repositorioUser.findAll()).
						thenReturn(Stream.of(
						new User(),
						new User(),
						new User()).collect(Collectors.toList()));
		assertEquals(3, servicioUser.listaUsuarios().size());
		
	}
	
	@Test
	public void userLoginTest() throws Exception{
		mvc.perform(formLogin("/login").user("admin01").password("admin123"))
		.andExpect(status().is2xxSuccessful());
	}	
	
	@Test
	public void userLoginFailed() throws Exception {
		mvc.perform(formLogin("/login").user("admin01").password("admin1234"))
		.andExpect(unauthenticated());
	}		
	
	@Test 
	public void getUserByEmail() {
		String correo = "admin01@admin.cl";
		when(repositorioUser.existsByEmail(correo))
			.thenReturn(true);
		assertEquals(true,repositorioUser.existsByEmail(correo));
		
		
	}
}
