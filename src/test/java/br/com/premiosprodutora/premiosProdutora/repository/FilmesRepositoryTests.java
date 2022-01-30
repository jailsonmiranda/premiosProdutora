package br.com.premiosprodutora.premiosProdutora.repository;

import br.com.premiosprodutora.premiosProdutora.dto.IntervaloPremiosProdutoraDTO;
import br.com.premiosprodutora.premiosProdutora.resource.ConsultaPremios;
import br.com.premiosprodutora.premiosProdutora.resource.ImportacaoFilmesCSV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class FilmesRepositoryTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ImportacaoFilmesCSV importacaoFilmesCSV;

	@Autowired
	private ConsultaPremios consultaPremios;

	@Autowired
	private FilmesRepository filmesRepository;

	@Test
	public void validacaoTotalImportadoComGravadoBanco_RetornaTotalResultados() throws Exception {
		//Teste para validar o total de registros importados e comparar com o total do banco de dados:
		mockMvc.perform(post("/ImportacaoFilmes")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(""))
				.andExpect(MockMvcResultMatchers.status().isOk());

		long countImportCsv = importacaoFilmesCSV.importacaoFilmes();
		long countRecordsDatabase = filmesRepository.count();
		Assertions.assertEquals(countImportCsv, countRecordsDatabase);
	}

	@Test
	public void consultaPremiosValida_Status200() throws Exception {
		mockMvc.perform(get("/produtoras/intervaloPremios")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(""))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void valicaoPremiacaoIntervaloMinimo_ReturnTotalRecords() throws Exception {
		//Testa se total de produtoras premiados com intevalo minimo bate com quantidade informada
		mockMvc.perform(get("/produtoras/intervaloPremios")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(""))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Map<String, List<IntervaloPremiosProdutoraDTO>> mapReturn = consultaPremios.intervaloPremios();
		Assertions.assertEquals(mapReturn.get("min").size(), 1);
	}

	@Test
	public void validacaoPremiacaoIntervaloMaximo_ReturnStatus200() throws Exception {
		//Testa se total de produtoras premiados com intevalo máximo bate com quantidade informada
		mockMvc.perform(get("/produtoras/intervaloPremios")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(""))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Map<String, List<IntervaloPremiosProdutoraDTO>> mapReturn = consultaPremios.intervaloPremios();
		Assertions.assertEquals(mapReturn.get("max").size(), 1);

	}

	@Test
	void testeConsultaPremios() throws Exception {
	//só será valido para importação de CSV atual.
		String response = "{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";
		var result = mockMvc.perform(get("/produtoras/intervaloPremios")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		Assertions.assertEquals(response,result);
	}
}
