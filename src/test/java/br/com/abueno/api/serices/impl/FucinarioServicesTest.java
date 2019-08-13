package br.com.abueno.api.serices.impl;
//package br.com.abueno.api;
//
//
//import static org.junit.Assert.assertNotNull;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.BDDMockito;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import br.com.abueno.api.entity.Funcionario;
//import br.com.abueno.api.repository.FuncionarioRepository;
//import br.com.abueno.api.services.FuncionarioServices;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("test")
//public class FucinarioServicesTest {
//	
//	@MockBean
//	FuncionarioRepository funcionarioRepository;
//	
//	@Autowired
//	private FuncionarioServices funcionarioServices;
//	
//	
//	@Before
//	public void setUp() {
//		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
//		BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario())); 
//		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
//		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
//	}
//	
//	@Test
//	public void testPerstirFuncionario() {
//		Funcionario funcionario = this.funcionarioServices.persitir(new Funcionario());
//		assertNotNull(funcionario);
//	}
//
//}
