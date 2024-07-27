package com.projeto.sistema1.controle;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema1.modelos.Cargo;
import com.projeto.sistema1.modelos.Departamento;
import com.projeto.sistema1.repositorios.CargoRepositorio;
import com.projeto.sistema1.repositorios.DepartamentoRepositorio;


@Controller 
public class CargoControle {
	@Autowired
	private CargoRepositorio cargoRepositorio;
	@Autowired
	private DepartamentoRepositorio departamentoRepositorio;
	
		
	@GetMapping("/cadastroCargo") //para nós obtemos o mateamento(Quando a pessoa executar isso nós vamos executar um método(chamada da função ou seja sempre que a pessoa chama cadastra estato ela quer cadastrar o  cargo)
	public ModelAndView cadastrar(Cargo cargo) {//vai ser responsável por criar a nossa view ou seja a nossa visualização(uma vez que nós estamos utilizado o timelieaf
		ModelAndView mv= new ModelAndView("/administrativo/cargos/cadastro");//esse é o caminho de pastas até chagar o nosso arquivo cadastro.html
		mv.addObject("cargo",cargo);
		mv.addObject("listaDepartamentos",departamentoRepositorio.findAll());
		return  mv;
		
	}

	
	@GetMapping("/listarCargo")
	public ModelAndView listar() {
		ModelAndView mv= new ModelAndView("/administrativo/cargos/lista");//criamos uma nova visualizaçao
		mv.addObject("listaCargos",cargoRepositorio.findAll());//listaCargos é oque está lá na tela de listagem graças ao thymleaf e vamos passar todos os registros que estao la no cargo repositoriios que gere o banco de dados e vamos pegar todos os registro do banco de dados
		return mv;
		
	}
	

	
	@GetMapping("/editarCargo/{id}")//para chamar a função editar cargo no front-end vamos isar o thymeleaf i vamos pasar o nome \editarCargo\{id} responsavel pelo método de editar cargo com o parametro id
	public ModelAndView editar(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Cargo> cargo =cargoRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de cargo para edição
		return cadastrar(cargo.get());
	}
	
	@GetMapping("/removerCargo/{id}")//para chamar a função editar cargo no front-end vamos isar o thymeleaf i vamos pasar o nome \editarCargo\{id} responsavel pelo método de editar cargo com o parametro id
	public ModelAndView remover(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Cargo> cargo =cargoRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de cargo para edição
		cargoRepositorio.delete(cargo.get());//vamos remover 
		return listar();//depois so vamos retornar a lista
	}
	
	
	@PostMapping("/salvarCargo")//post oculta os dados
	public ModelAndView salvar(Cargo cargo,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return cadastrar(cargo);//se tiver um erro terá que chamar a função de cadastrar cargo
			
		}
		cargoRepositorio.saveAndFlush(cargo);//essa é uma função do jpa para salvar
		String mensagem = "Cargo '" + cargo.getNome() + "' salvo com sucesso!";
		model.addAttribute("message", mensagem);
		
		return cadastrar(new Cargo());
		
		
	}
	

	
}
