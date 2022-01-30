package br.com.premiosprodutora.premiosProdutora.resource;

import br.com.premiosprodutora.premiosProdutora.dto.IntervaloPremiosProdutoraDTO;
import br.com.premiosprodutora.premiosProdutora.entidades.Filmes;
import br.com.premiosprodutora.premiosProdutora.repository.FilmesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtoras")
public class ConsultaPremios {

    @Autowired
    private FilmesRepository filmesRepository;

    @GetMapping
    @RequestMapping("/intervaloPremios")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<IntervaloPremiosProdutoraDTO>> intervaloPremios() {

        List<String> produtoras = new ArrayList<>();
        List<Integer> anos = new ArrayList<>();
        List<IntervaloPremiosProdutoraDTO> listProdutoraPremiosIntervalo = new ArrayList<>();
        List<IntervaloPremiosProdutoraDTO> listIntervaloMin = new ArrayList<>();
        List<IntervaloPremiosProdutoraDTO> listIntervaloMax = new ArrayList<>();
        Map<String,Integer> maxIntervalo = new HashMap<String,Integer>();
        Map<String,Integer> minIntervalo = new HashMap<String,Integer>();
        int intervalo;

        for(Filmes filme: filmesRepository.findAll()) {
            if (!produtoras.contains(filme.getProdutora())) {
                produtoras.add(filme.getProdutora());
            }

        }

        for(String produtora : produtoras) {
            anos.clear();
            intervalo = 0;
            List<Filmes> filmeDaProdutora = filmesRepository.findAll().stream().filter(filme -> filme.getProdutora() .contains(produtora) && filme.getVencedora().equals("yes")).collect(Collectors.toList());
            if(filmeDaProdutora.size() > 1) {
                for (Filmes filme : filmeDaProdutora){
                    anos.add(filme.getAno());
                }

                if(anos.size() > 0) { //Se chegou até aqui vai montar o retorno da consulta em formato JSON
                    intervalo = Collections.max(anos) - Collections.min(anos) ;

                    IntervaloPremiosProdutoraDTO intervaloPremiosIntervalo = new IntervaloPremiosProdutoraDTO();
                    intervaloPremiosIntervalo.setProdutora(produtora);
                    intervaloPremiosIntervalo.setIntervalo(intervalo);
                    intervaloPremiosIntervalo.setVitoriaAnterior(Collections.min(anos));
                    intervaloPremiosIntervalo.setVitoriaSequinte(Collections.max(anos));
                    listProdutoraPremiosIntervalo.add(intervaloPremiosIntervalo);

                    if(maxIntervalo.isEmpty()) {
                        maxIntervalo.put(produtora, intervalo) ;
                        minIntervalo.put(produtora, intervalo) ;
                    }
                    else {
                        for(String keyMap : maxIntervalo.keySet()) {

                            if(maxIntervalo.get(keyMap) < intervalo) {
                                maxIntervalo.remove(keyMap);
                                maxIntervalo.put(produtora, intervalo) ;
                            }
                            else if(maxIntervalo.get(keyMap) == intervalo) {
                                maxIntervalo.put(produtora, intervalo) ;
                            }
                        }

                        for(String keyMap : minIntervalo.keySet()) {
                            if(minIntervalo.get(keyMap) > intervalo) {
                                minIntervalo.remove(keyMap);
                                minIntervalo.put(produtora, intervalo) ;
                            }
                            else if(minIntervalo.get(keyMap) == intervalo) {
                                minIntervalo.put(produtora, intervalo) ;
                            }
                        }
                    }
                }

            }

        }

        for(IntervaloPremiosProdutoraDTO intervaloPremiosProdutora  : listProdutoraPremiosIntervalo) {
            int intervalComp = intervaloPremiosProdutora.getVitoriaSequinte() - intervaloPremiosProdutora.getVitoriaAnterior();
            for(String keyMap : minIntervalo.keySet()) {
                if(minIntervalo.get(keyMap) == intervalComp) {
                    if(!listIntervaloMin.contains(intervaloPremiosProdutora)) {
                        listIntervaloMin.add(intervaloPremiosProdutora);
                    }

                }
            }
            for(String keyMap : maxIntervalo.keySet()) {
                if(maxIntervalo.get(keyMap) == intervalComp) {
                    if(!listIntervaloMax.contains(intervaloPremiosProdutora)) {
                        listIntervaloMax.add(intervaloPremiosProdutora);
                    }
                }
            }
        }


        HashMap<String, List<IntervaloPremiosProdutoraDTO>> mapListInterval = new HashMap<>();
        mapListInterval.put("min", listIntervaloMin);
        mapListInterval.put("max", listIntervaloMax);

        return mapListInterval;
    }


}
