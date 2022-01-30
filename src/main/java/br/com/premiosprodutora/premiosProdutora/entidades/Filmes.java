package br.com.premiosprodutora.premiosProdutora.entidades;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Filmes implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String estudio;

    @Column(nullable = false)
    private String produtora;

    @Column(nullable = true)
    private String vencedora;

}
