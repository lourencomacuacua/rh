package com.projeto.sistema1.controle;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.sistema1.dto.UserDto;
import com.projeto.sistema1.modelos.Departamento;
import com.projeto.sistema1.modelos.User;
import com.projeto.sistema1.repositorios.DepartamentoRepositorio;


@Controller //para identificar que 'e um controle da arquitetura mvc
public class DepartamentoControle {
	@Autowired// para fazer a conexão com aquele repositório de departamento para nós salvarmos os dados
	private DepartamentoRepositorio departamentoRepositorio;
	
		
	@GetMapping("/cadastroDepartamento") //para nós obtemos o mateamento(Quando a pessoa executar isso nós vamos executar um método(chamada da função ou seja sempre que a pessoa chama cadastra estato ela quer cadastrar o  departamento)
	public ModelAndView cadastrar(Departamento departamento) {//vai ser responsável por criar a nossa view ou seja a nossa visualização(uma vez que nós estamos utilizado o timelieaf
		ModelAndView mv= new ModelAndView("/administrativo/departamentos/cadastro");//esse é o caminho de pastas até chagar o nosso arquivo cadastro.html
		mv.addObject("departamento",departamento);

		return  mv;
		
	}

	
	@GetMapping("/listarDepartamento")
	public ModelAndView listar() {
		ModelAndView mv= new ModelAndView("/administrativo/departamentos/lista");//criamos uma nova visualizaçao
		mv.addObject("listaDepartamentos",departamentoRepositorio.findAll());//listaDepartamentos é oque está lá na tela de listagem graças ao thymleaf e vamos passar todos os registros que estao la no departamento repositoriios que gere o banco de dados e vamos pegar todos os registro do banco de dados
		return mv;
		
	}
	

	
	@GetMapping("/editarDepartamento/{id}")//para chamar a função editar departamento no front-end vamos isar o thymeleaf i vamos pasar o nome \editarDepartamento\{id} responsavel pelo método de editar departamento com o parametro id
	public ModelAndView editar(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Departamento> departamento =departamentoRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de departamento para edição
		return cadastrar(departamento.get());
	}
	
	@GetMapping("/removerDepartamento/{id}")//para chamar a função editar departamento no front-end vamos isar o thymeleaf i vamos pasar o nome \editarDepartamento\{id} responsavel pelo método de editar departamento com o parametro id
	public ModelAndView remover(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Departamento> departamento =departamentoRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de departamento para edição
		departamentoRepositorio.delete(departamento.get());//vamos remover 
		return listar();//depois so vamos retornar a lista
	}
	
	
	@PostMapping("/salvarDepartamento")//post oculta os dados
	public ModelAndView salvar(Departamento departamento,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return cadastrar(departamento);//se tiver um erro terá que chamar a função de cadastrar departamento
			
		}
		departamentoRepositorio.saveAndFlush(departamento);//essa é uma função do jpa para salvar
		String mensagem = "Departamento '" + departamento.getNome() + "' salvo com sucesso!";
		model.addAttribute("message", mensagem);
		return cadastrar(new Departamento());
		
		
	}
	

	
	
}
