package com.mottu.sprint3.controller;

import com.mottu.sprint3.dto.MotoDto;
import com.mottu.sprint3.model.Moto;
import com.mottu.sprint3.repository.MotoRepository;
import com.mottu.sprint3.repository.PatioRepository;
import com.mottu.sprint3.repository.StatusRepository;
import com.mottu.sprint3.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private StatusRepository statusRepository;

    @PostMapping("/moto/save")
    public String saveMoto(@ModelAttribute("motoDto") @Valid MotoDto motoDto, RedirectAttributes ra) {
        try {
            Moto moto = new Moto();
            
            // Se for uma edição, busca a moto existente
            if (motoDto.getId() != null) {
                Optional<Moto> existingMoto = motoRepository.findById(motoDto.getId());
                if (existingMoto.isPresent()) {
                    moto = existingMoto.get();
                }
            }

            // Mapeia os dados do DTO para a Model
            moto.setPlaca(motoDto.getPlaca());
            moto.setChassi(motoDto.getChassi());
            moto.setQrCode(motoDto.getQrCode());
            moto.setFotos(motoDto.getFotos());
            moto.setObservacoes(motoDto.getObservacoes());
            
            // As datas precisam ser tratadas. Para 'dataEntrada', usamos a data/hora atual.
            if (moto.getDataEntrada() == null) {
                moto.setDataEntrada(Timestamp.valueOf(LocalDateTime.now()));
            }
            moto.setPrevisaoEntrega(motoDto.getPrevisaoEntrega());

            // Mapeamento dos relacionamentos com base nos IDs
            if (motoDto.getZonaId() != null) {
                moto.setZona(zonaRepository.findById(motoDto.getZonaId()).orElse(null));
            }
            if (motoDto.getPatioId() != null) {
                moto.setPatio(patioRepository.findById(motoDto.getPatioId()).orElse(null));
            }
            if (motoDto.getStatusId() != null) {
                moto.setStatus(statusRepository.findById(motoDto.getStatusId()).orElse(null));
            }

            // Salva a moto no banco de dados
            motoRepository.save(moto);
            ra.addFlashAttribute("mensagemSucesso", "Moto salva com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao salvar a moto: " + e.getMessage());
        }

        return "redirect:/"; // Redireciona para o dashboard
    }

    @GetMapping("/moto/delete/{id}")
    public String deleteMoto(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            motoRepository.deleteById(id);
            ra.addFlashAttribute("mensagemSucesso", "Moto deletada com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao deletar a moto: " + e.getMessage());
        }
        return "redirect:/";
    }
}