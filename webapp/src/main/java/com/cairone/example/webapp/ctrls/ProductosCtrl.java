package com.cairone.example.webapp.ctrls;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cairone.example.webapp.domain.ProductoEntity;
import com.cairone.example.webapp.dtos.ProductoDto;
import com.cairone.example.webapp.repositories.ProductoRepository;

@Controller 
public class ProductosCtrl {

	@Autowired ProductoRepository productoRepository = null;
	
	@GetMapping("/productos/lista")
	public ModelAndView inicio(Map<String,Object> model) {
		model.put("productos", productoRepository.findAll());
		return new ModelAndView("producto-lista", model);
	}
	
	@GetMapping("/productos/new")
	public ModelAndView nuevoView(Map<String,Object> model) {

		ProductoDto producto = new ProductoDto();
		
		model.put("producto", producto);
		model.put("disabled", false);
		
		return new ModelAndView("producto-frm", model);
	}

	@PostMapping("/productos/new")
	public String nuevoCmd(@ModelAttribute("producto") @Valid ProductoDto producto, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			model.addAttribute("producto", producto);
			model.addAttribute("disabled", false);
			return "producto-frm";
		}
		
		ProductoEntity productoEntity = new ProductoEntity();
		
		productoEntity.setId(producto.getId());
		productoEntity.setNombre(producto.getNombre());
		productoEntity.setDescripcion(producto.getDescripcion());
		
		if(productoEntity.getId() == null) {
			Long id = productoRepository.getMaxId();
			
			if(id == 0) id = 0L;
			id++;
			
			productoEntity.setId(id);
		}
		
		productoRepository.save(productoEntity);
		
		return "redirect:/productos/lista";
	}
	
	@GetMapping("/productos/{productoId}/edit")
	public ModelAndView editarView(@PathVariable("productoId") Long productoId, Map<String,Object> model) {
		
		ProductoEntity productoEntity = productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no se encuentra!"));
		ProductoDto productoDto = new ProductoDto();
		
		productoDto.setId(productoEntity.getId());
		productoDto.setNombre(productoEntity.getNombre());
		productoDto.setDescripcion(productoEntity.getDescripcion());
		
		model.put("producto", productoDto);
		model.put("disabled", true);
		
		return new ModelAndView("producto-frm", model);
	}

	@PostMapping("/productos/{productoId}/edit")
	public String editarCmd(final @PathVariable("productoId") Long productoId, @ModelAttribute("producto") @Valid ProductoDto producto, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			model.addAttribute("producto", producto);
			model.addAttribute("disabled", false);
			return "producto-frm";
		}

		ProductoEntity productoEntity = new ProductoEntity();
		
		productoEntity.setId(productoId);
		productoEntity.setNombre(producto.getNombre());
		productoEntity.setDescripcion(producto.getDescripcion());
		
		productoRepository.save(productoEntity);
		return "redirect:/productos/lista";
	}
	
	@GetMapping("/productos/{productoId}/delete")
	public String borrar(@PathVariable("productoId") Long productoId) {
		productoRepository.deleteById(productoId);
		return "redirect:/productos/lista";
	}
}
