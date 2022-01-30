package br.com.premiosprodutora.premiosProdutora.resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.premiosprodutora.premiosProdutora.entidades.Filmes;
import br.com.premiosprodutora.premiosProdutora.repository.FilmesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/ImportacaoFilmes")
@Component
public class ImportacaoFilmesCSV {

    @Autowired
    private FilmesRepository filmesRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @PostConstruct
    public int importacaoFilmes()  {

        Path path = Paths.get("src/main/java/planilhaimportacao/movielist.csv");
        List<String> rowFile = new ArrayList<>() ;
        int cont = 0;
        int countImported = 0;

        try {
            rowFile = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
            filmesRepository.deleteAll();
            for (String row : rowFile) {
                if(!row.contains(";;;;")) {
                    String[] splitValue = row.split(";",-1);
                    if (cont >= 1 ) {
                        if( !splitValue[1].isBlank() &&
                                !splitValue[2].isBlank() &&
                                !splitValue[3].isBlank()) {

                            Filmes filmes = new Filmes();
                            filmes.setAno(Integer.parseInt(splitValue[0]));
                            filmes.setTitulo(splitValue[1]);
                            filmes.setEstudio(splitValue[2]);
                            filmes.setProdutora(splitValue[3]);
                            filmes.setVencedora(splitValue[4]);

                            filmesRepository.save(filmes);
                            countImported ++;
                        }

                    }
                }

                cont ++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return countImported;
    }

}
