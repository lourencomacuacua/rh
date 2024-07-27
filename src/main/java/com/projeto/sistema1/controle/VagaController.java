package com.projeto.sistema1.controle;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.sistema1.modelos.Candidato;
import com.projeto.sistema1.modelos.Cargo;
import com.projeto.sistema1.modelos.Funcionario;
import com.projeto.sistema1.modelos.Vaga;
import com.projeto.sistema1.repositorios.CandidatoRepository;
import com.projeto.sistema1.repositorios.CargoRepositorio;
//import com.projeto.sistema1.repositorios.CandidatoRepositorio;
import com.projeto.sistema1.repositorios.VagaRepository;

@Controller
public class VagaController {
	@Autowired
	private VagaRepository vagaRepositorio;
	@Autowired
	private CandidatoRepository candidatoRepositorio;
	@Autowired
	private CargoRepositorio cargoRepositorio;
	

	@GetMapping("/cadastrarVaga")
	public ModelAndView cadastrar(Vaga vaga) {
		ModelAndView mv= new ModelAndView("/administrativo/vagas/cadastro");
		mv.addObject("vaga",vaga);
		mv.addObject("listaCargos",cargoRepositorio.findAll());
		return mv;
	}
	
	@PostMapping("/salvarVaga")//post oculta os dados
	public ModelAndView salvar(Vaga vaga,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return cadastrar(vaga);//se tiver um erro terá que chamar a função de cadastrar funcionario
			
		}
		vagaRepositorio.saveAndFlush(vaga);//essa é uma função do jpa para salvar
		String mensagem = "Vaga '" + vaga.getNome() + "' salva com sucesso!";
		model.addAttribute("message", mensagem);
		return cadastrar(new Vaga());
				
	}
	
	//Lista vagas da parte do administrado
	@GetMapping("/listarVagass")
	public ModelAndView listar() {
		ModelAndView mv= new ModelAndView("/administrativo/vagas/lista");//criamos uma nova visualizaçao
		mv.addObject("listaVagas",vagaRepositorio.findAll());//listaFuncionarios é oque está lá na tela de listagem graças ao thymleaf e vamos passar todos os registros que estao la no funcionario repositoriios que gere o banco de dados e vamos pegar todos os registro do banco de dados
		
		return mv;
		
	}
	//listar vagas da parte do usuário
	@GetMapping("/listarVagas")
	public ModelAndView listarr() {
		ModelAndView mv= new ModelAndView("/administrativo/vagas/lista2");//criamos uma nova visualizaçao
		mv.addObject("listaVagass",vagaRepositorio.findAll());//listaFuncionarios é oque está lá na tela de listagem graças ao thymleaf e vamos passar todos os registros que estao la no funcionario repositoriios que gere o banco de dados e vamos pegar todos os registro do banco de dados
		
		return mv;
		
	}
	
	
	//deleta vaga
	@GetMapping("/removerVaga/{id}")//para chamar a função editar funcionario no front-end vamos isar o thymeleaf i vamos pasar o nome \editarFuncionario\{id} responsavel pelo método de editar funcionario com o parametro id
	public ModelAndView remover(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Vaga> vaga =vagaRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de funcionario para edição
		vagaRepositorio.delete(vaga.get());//vamos remover 
		return listar();//depois so vamos retornar a lista
	}
	
	@GetMapping("/editarVaga/{id}")//para chamar a função editar funcionario no front-end vamos isar o thymeleaf i vamos pasar o nome \editarFuncionario\{id} responsavel pelo método de editar funcionario com o parametro id
	public ModelAndView editar(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Vaga> vaga =vagaRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de funcionario para edição
		return cadastrar(vaga.get());
	}
	

	
	
	
	//da parte do administrador
	@GetMapping("/detalheVaga/{id}")
	public ModelAndView detalhes(@PathVariable("id") Long id) {
	    Optional<Vaga> vagaOptional = vagaRepositorio.findById(id);
	    
	    if (vagaOptional.isPresent()) {
	        Vaga vaga = vagaOptional.get();
	        ModelAndView mv = new ModelAndView("/administrativo/vagas/candidatos");
	        mv.addObject("vaga", vaga);
	        
	        Candidato candidato = new Candidato(); // Criar um novo candidato
	        mv.addObject("candidato", candidato); // Adicionar o candidato ao modelo
	        
	        Iterable<Candidato> candidatos = candidatoRepositorio.findByVaga(vaga);
	        mv.addObject("candidatos", candidatos);
	        
	        return mv;
	    } else {
	        // Lógica para lidar com o caso em que a vaga não é encontrada
	        return new ModelAndView("redirect:/paginaDeErro");
	    }
	}
	
	/*
	 * 	@PostMapping("/salvarCargo")//post oculta os dados
	public ModelAndView salvar(Cargo cargo,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return cadastrar(cargo);//se tiver um erro terá que chamar a função de cadastrar cargo
			
		}
		cargoRepositorio.saveAndFlush(cargo);//essa é uma função do jpa para salvar
		String mensagem = "Cargo '" + cargo.getNome() + "' salvo com sucesso!";
		model.addAttribute("message", mensagem);
		
		return cadastrar(new Cargo());
		
		
	}
	 * 
	 * */

	
	
	
	
	//da parte do usuário
//	@PostMapping("/salvarCandidato/{id}")
//	public ModelAndView salvarCandidato(@PathVariable("id") Long id,Candidato candidato, BindingResult result, Model model) {
//	    if (result.hasErrors()) {
//	        return new ModelAndView("redirect:/detalheVaga/" + id);
//	    }
//
//	    // Verificar se a NUIT está duplicada
//	    Candidato candidatoExistente = candidatoRepositorio.findByNuit(candidato.getNuit());
//	    if (candidatoExistente != null) {
//			String mensagem = "Você já não pode concorrer a essa vaga!";
//			model.addAttribute("message", mensagem);
//	        return new ModelAndView("redirect:/detalheVaga/" + id);
//	    }
//
//	    // Verificar se a vaga existe
//	    Optional<Vaga> vagaOptional = vagaRepositorio.findById(id);
//	    if (!vagaOptional.isPresent()) {
//			String mensagem = "Vaga não encontrada!";
//			model.addAttribute("message", mensagem);
//	        return new ModelAndView("redirect:/algumaPaginaDeErro");
//	    }
//
//	    // Associar o candidato à vaga
//	    Vaga vaga = vagaOptional.get();
//	    candidato.setVaga(vaga);
//
//	    // Salvar o candidato no banco de dados
//	    candidatoRepositorio.save(candidato);
//
//	    // Redirecionar de volta para a página de detalhes da vaga
//		String mensagem = "Candidato '" + candidato.getNomeCandidato() + "' cadastrado com sucesso!";
//		model.addAttribute("message", mensagem);
//	    return new ModelAndView("redirect:/detalheVaga/" + id);
//	}
//	
	
	@PostMapping("/salvarCandidato/{id}")
	public ModelAndView salvarCandidato(@PathVariable("id") Long id, @Valid Candidato candidato, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
	    if (result.hasErrors()) {
	        return new ModelAndView("redirect:/detalheVagaa/" + id);
	    }

	    // Verificar se a NUIT está duplicada
	    Candidato candidatoExistente = candidatoRepositorio.findByNuit(candidato.getNuit());
	    if (candidatoExistente != null) {
	        String mensagem = "Você já não pode concorrer a essa vaga!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        return new ModelAndView("redirect:/detalheVagaa/" + id);
	    }

	    // Verificar se a vaga existe
	    Optional<Vaga> vagaOptional = vagaRepositorio.findById(id);
	    if (!vagaOptional.isPresent()) {
	        String mensagem = "Vaga não encontrada!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        return new ModelAndView("redirect:/algumaPaginaDeErro");
	    }

	    // Associar o candidato à vaga
	    Vaga vaga = vagaOptional.get();
	    candidato.setVaga(vaga);

	    // Salvar o candidato no banco de dados
	    candidatoRepositorio.save(candidato);

	    // Mensagem de sucesso
	    String mensagem = "Candidato '" + candidato.getNomeCandidato() + "' cadastrado com sucesso!";
	    redirectAttributes.addFlashAttribute("message", mensagem);

	    // Retornar para a mesma página de detalhes da vaga
	    return new ModelAndView("redirect:/detalheVagaa/" + id);
	}
	
	
	//da parte do usuário
	
	@GetMapping("/detalheVagaa/{id}")
	public ModelAndView detalhess(@PathVariable("id") Long id) {
	    Optional<Vaga> vagaOptional = vagaRepositorio.findById(id);
	    
	    if (vagaOptional.isPresent()) {
	        Vaga vaga = vagaOptional.get();
	        ModelAndView mv = new ModelAndView("/administrativo/vagas/candidatos2");
	        mv.addObject("vaga", vaga);
	        
	        Candidato candidato = new Candidato(); // Criar um novo candidato
	        mv.addObject("candidato", candidato); // Adicionar o candidato ao modelo
	        
	        return mv;
	    } else {
	        // Lógica para lidar com o caso em que a vaga não é encontrada
	        return new ModelAndView("redirect:/paginaDeErro");
	    }
	}
	
	
	
	
	
	
	//remover candidato 
	@GetMapping("/removerCandidato/{id}")//para chamar a função editar funcionario no front-end vamos isar o thymeleaf i vamos pasar o nome \editarFuncionario\{id} responsavel pelo método de editar funcionario com o parametro id
	public ModelAndView removerCandidato(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Candidato> candidato =candidatoRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de funcionario para edição
		candidatoRepositorio.delete(candidato.get());//vamos remover 
		return new ModelAndView("redirect:/detalheVaga/" + id);
	}
	

}
