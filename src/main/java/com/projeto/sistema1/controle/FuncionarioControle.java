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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.sistema1.modelos.Estagiario;
import com.projeto.sistema1.modelos.Estagiario;
import com.projeto.sistema1.modelos.Estagiario;
import com.projeto.sistema1.modelos.Funcionario;
import com.projeto.sistema1.modelos.Funcionario;
import com.projeto.sistema1.modelos.Funcionario;
import com.projeto.sistema1.repositorios.CargoRepositorio;
import com.projeto.sistema1.repositorios.EstagiarioRepository;
import com.projeto.sistema1.repositorios.FuncionarioRepositorio;


@Controller //para identificar que 'e um controle da arquitetura mvc
public class FuncionarioControle {
	@Autowired// para fazer a conexão com aquele repositório de funcionario para nós salvarmos os dados
	private FuncionarioRepositorio funcionarioRepositorio;
	@Autowired
	private CargoRepositorio cargoRepositorio;
	@Autowired
	private EstagiarioRepository estagiarioRepository;

		
	@GetMapping("/cadastroFuncionario") //para nós obtemos o mateamento(Quando a pessoa executar isso nós vamos executar um método(chamada da função ou seja sempre que a pessoa chama cadastra estato ela quer cadastrar o  funcionario)
	public ModelAndView cadastrar(Funcionario funcionario) {//vai ser responsável por criar a nossa view ou seja a nossa visualização(uma vez que nós estamos utilizado o timelieaf
		ModelAndView mv= new ModelAndView("/administrativo/funcionarios/cadastro");//esse é o caminho de pastas até chagar o nosso arquivo cadastro.html
		mv.addObject("funcionario",funcionario);
		mv.addObject("listaCargos",cargoRepositorio.findAll());
		return  mv;
		
	}
	
	@GetMapping("/listarFuncionario")
	public ModelAndView listar() {
		ModelAndView mv= new ModelAndView("/administrativo/funcionarios/lista");//criamos uma nova visualizaçao
		mv.addObject("listaFuncionarios",funcionarioRepositorio.findAll());//listaFuncionarios é oque está lá na tela de listagem graças ao thymleaf e vamos passar todos os registros que estao la no funcionario repositoriios que gere o banco de dados e vamos pegar todos os registro do banco de dados
		return mv;
		
	}
	

	
	@GetMapping("/editarFuncionario/{id}")//para chamar a função editar funcionario no front-end vamos isar o thymeleaf i vamos pasar o nome \editarFuncionario\{id} responsavel pelo método de editar funcionario com o parametro id
	public ModelAndView editar(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Funcionario> funcionario =funcionarioRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de funcionario para edição
		return cadastrar(funcionario.get());
	}
	
	@GetMapping("/removerFuncionario/{id}")//para chamar a função editar funcionario no front-end vamos isar o thymeleaf i vamos pasar o nome \editarFuncionario\{id} responsavel pelo método de editar funcionario com o parametro id
	public ModelAndView remover(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Funcionario> funcionario =funcionarioRepositorio.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de funcionario para edição
		funcionarioRepositorio.delete(funcionario.get());//vamos remover 
		return listar();//depois so vamos retornar a lista
	}
	
	
	@PostMapping("/salvarFuncionario")//post oculta os dados
	public ModelAndView salvar(Funcionario funcionario,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return cadastrar(funcionario);//se tiver um erro terá que chamar a função de cadastrar funcionario
			
		}
		funcionarioRepositorio.saveAndFlush(funcionario);//essa é uma função do jpa para salvar
		String mensagem = "Funcionário '" + funcionario.getNome() + "' salvo com sucesso!";
		model.addAttribute("message", mensagem);
		
		return cadastrar(new Funcionario());
			
	}
	
	@GetMapping("/detalheFuncionario/{id}")

	public ModelAndView detalhes(@PathVariable("id") Long id) {
	    Optional<Funcionario> funcionarioOptional = funcionarioRepositorio.findById(id);
	    
	    if (funcionarioOptional.isPresent()) {
	        Funcionario funcionario = funcionarioOptional.get();
	        ModelAndView mv = new ModelAndView("/administrativo/funcionarios/estagiarios");
	        mv.addObject("funcionario", funcionario);
	        
	        Estagiario estagiario = new Estagiario(); // Criar um novo estagiario
	        mv.addObject("estagiario", estagiario); // Adicionar o estagiario ao modelo
	        
	        Iterable<Estagiario> estagiarios = estagiarioRepository.findByFuncionario(funcionario);
	        mv.addObject("estagiarios", estagiarios);
	        
	        return mv;
	    } else {
	        // Lógica para lidar com o caso em que a funcionario não é encontrada
	        return new ModelAndView("redirect:/paginaDeErro");
	    }
	}
	
	@PostMapping("/salvarEstagiario/{id}")
	public ModelAndView salvarEstagiario(@PathVariable("id") Long id,Estagiario estagiario, BindingResult result, RedirectAttributes attributes) {
	    if (result.hasErrors()) {
	        attributes.addFlashAttribute("mensagem", "Verifique os campos");
	        return new ModelAndView("redirect:/detalheFuncionario/" + id);
	    }

	    // Verificar se a NUIT está duplicada
	    Estagiario estagiarioExistente = estagiarioRepository.findByNuit(estagiario.getNuit());
	    if (estagiarioExistente != null) {
	        attributes.addFlashAttribute("mensagem_erro", "Nuit duplicado");
	        return new ModelAndView("redirect:/detalheFuncionario/" + id);
	    }

	    // Verificar se a funcionario existe
	    Optional<Funcionario> funcionarioOptional = funcionarioRepositorio.findById(id);
	    if (!funcionarioOptional.isPresent()) {
	        attributes.addFlashAttribute("mensagem_erro", "Funcionario não encontrada");
	        return new ModelAndView("redirect:/algumaPaginaDeErro");
	    }

	    // Associar o estagiario à funcionario
	    Funcionario funcionario = funcionarioOptional.get();
	    estagiario.setFuncionario(funcionario);

	    // Salvar o estagiario no banco de dados
	    estagiarioRepository.save(estagiario);

	    // Redirecionar de volta para a página de detalhes da funcionario
	    attributes.addFlashAttribute("mensagem", "Estagiario adicionado com sucesso!");
	    return new ModelAndView("redirect:/detalheFuncionario/" + id);
	}
	
	//remover estagiario 
	@GetMapping("/removerEstagiario/{id}")//para chamar a função editar funcionario no front-end vamos isar o thymeleaf i vamos pasar o nome \editarFuncionario\{id} responsavel pelo método de editar funcionario com o parametro id
	public ModelAndView removerEstagiario(@PathVariable("id") Long id) {//(@PathVariable é para receber o nosso parámetro
		Optional<Estagiario> estagiario =estagiarioRepository.findById(id);//Option é para evitar questão de null quando nós caregamos as informações de funcionario para edição
		estagiarioRepository.delete(estagiario.get());//vamos remover 
		return new ModelAndView("redirect:/detalheFuncionario/" + id);
	}
	
}
