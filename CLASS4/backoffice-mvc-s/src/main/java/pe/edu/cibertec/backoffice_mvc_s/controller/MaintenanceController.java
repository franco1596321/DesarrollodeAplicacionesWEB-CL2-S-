package pe.edu.cibertec.backoffice_mvc_s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetailDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmInsertDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/maintenance") //esto permite definir una ruta que responde al mapeo de cada peticion
public class MaintenanceController {
//model se encarga de la incrustacion de datos que se envian desde el controlador hacia el template

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model ){

        List<FilmDto> films = maintenanceService.getAllFilms();
        model.addAttribute("films", films);
        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        FilmDetailDto filmDetailDto = maintenanceService.getFilmById(id);
        model.addAttribute("filmDetailDto", filmDetailDto);
        return "maintenance-detail";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model ){

        FilmDetailDto filmDetailDto = maintenanceService.getFilmById(id);
        List<Language> languages = maintenanceService.getAllLanguages();
        model.addAttribute("filmDetailDto", filmDetailDto);
        model.addAttribute("languages", languages);
        return "maintenance-edit";
    }

    @PostMapping("/save")
    public String saveFilm(FilmDetailDto filmDetailDto) {
        maintenanceService.saveFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }
    @GetMapping("/insert")
    public String insertFilmForm(Model model) {
        FilmInsertDto filmInsertDto = new FilmInsertDto(
                "",
                "",
                null,
                null,
                "",
                0,
                0.0,
                null,
                0.0,
                "",
                "",
                new Date()
        );

        List<Language> languages = maintenanceService.getAllLanguages();

        model.addAttribute("filmInsertDto", filmInsertDto);
        model.addAttribute("languages", languages);

        return "maintenance-insert";
    }

    @PostMapping("/insert")
    public String insertFilm(FilmInsertDto filmInsertDto) {
        maintenanceService.insertFilm(filmInsertDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id) {
        maintenanceService.deleteFilmById(id);
        return "redirect:/maintenance/start";
    }
}