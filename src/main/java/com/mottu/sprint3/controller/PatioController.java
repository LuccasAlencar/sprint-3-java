package com.mottu.sprint3.controller;

import com.mottu.sprint3.dto.PatioDto;
import com.mottu.sprint3.model.Patio;
import com.mottu.sprint3.repository.PatioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @PostMapping("/patio/save")
    public String savePatio(@ModelAttribute("patioDto") @Valid PatioDto patioDto, RedirectAttributes ra) {
        try {
            Patio patio = patioDto.getId() != null ? patioRepository.findById(patioDto.getId()).orElse(new Patio()) : new Patio();
            patio.setNome(patioDto.getNome());
            patioRepository.save(patio);
            ra.addFlashAttribute("mensagemSucesso", "Pátio salvo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao salvar o pátio: " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/patio/delete/{id}")
    public String deletePatio(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            patioRepository.deleteById(id);
            ra.addFlashAttribute("mensagemSucesso", "Pátio deletado com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao deletar o pátio: " + e.getMessage());
        }
        return "redirect:/";
    }
}