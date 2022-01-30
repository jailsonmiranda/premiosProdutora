package br.com.premiosprodutora.premiosProdutora.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude( JsonInclude.Include.NON_NULL)
public class IntervaloPremiosProdutoraDTO {
    @JsonProperty( value = "producer")
    private String produtora;

    @JsonProperty( value = "interval")
    private int intervalo;

    @JsonProperty( value = "previousWin")
    private int vitoriaAnterior;

    @JsonProperty( value = "followingWin")
    private int vitoriaSequinte;

}
