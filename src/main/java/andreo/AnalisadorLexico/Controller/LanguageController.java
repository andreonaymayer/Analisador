package andreo.AnalisadorLexico.Controller;

import andreo.AnalisadorLexico.Service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/analise")
@RequiredArgsConstructor

public class LanguageController {

    private final LanguageService languageService = new LanguageService();

    @PostMapping
    public String analise(@RequestBody String text) {
        System.out.println(text);
        return languageService.tester(text);
    }

    @PostMapping("/upload")
    public String uploadTextFile(@RequestParam("file") MultipartFile file) throws IOException {

        return languageService.tester(languageService.fileChecker(file));
    }
}
