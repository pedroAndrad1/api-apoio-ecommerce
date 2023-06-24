package br.com.uniriotec.sagui.svgio;

import br.com.uniriotec.sagui.model.DisciplinaCursada;
import br.com.uniriotec.sagui.pdfIo.DisciplineStatus;
import br.com.uniriotec.sagui.processors.FileStorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe responsável por receber as disciplinas de um aluno iterar sobre elas
 * e pintando a grade curricular com os status das disciplinas.
 */
@Component
@Slf4j
public class SvgHandler {
	@Autowired
	private FileStorageProperties fileStorageProperties;

	private static final int NUMERO_DE_OPTATIVAS = 8;
	private static final int NUMERO_DE_ELETIVAS = 4;
	private static final String DISCIPLINA_TIPO_OPTATIVA = "0002";
	private static final String DISCIPLINA_TIPO_ELETIVA = "0004";
	private static final String DISCIPLINA_SVG_TIPO_OPTATIVA = "OPTATIVA";
	private static final String DISCIPLINA_SVG_TIPO_ELETIVA = "ELETIVA";
	private static final String DISCIPLINA_REGEX = "[A-Z]{3}[0-9]{4}";
	public String paintSvg( Set<DisciplinaCursada> studentDisciplines, String svgNomeArquivo ) {
		String archive = fileStorageProperties.getSvgDir().concat(svgNomeArquivo) ;
		SvgIo svgIo = new SvgIo(archive);

		Map<String, DisciplinaCursada> disciplines = studentDisciplines.stream()
				.collect(Collectors.toMap( DisciplinaCursada::getCodigo, Function.identity() ));

		//Build a list of all "path" type lines of the svg. The path lines is where the discipline codes are
		NodeList pathsNodesList = svgIo.getNodeListByTag(SvgUsedElements.PATH.getElement());
		//Gera lista com as optativas
		List<DisciplinaCursada> optativas = disciplines.values().stream().filter( discipline -> discipline.getTipo().equals(DISCIPLINA_TIPO_OPTATIVA) ).collect(Collectors.toList());
		//Gera lista com as optativas com status aprovado
		List<DisciplinaCursada> optativasAPV = optativas.stream().filter(discipline -> ( discipline.getSituacao().equalsIgnoreCase(DisciplineStatus.APROVADO.getStatus()) ) ).collect(Collectors.toList());
		//Gera lista com eletivas
		List<DisciplinaCursada> eletivas = disciplines.values().stream().filter(discipline -> discipline.getTipo().equals(DISCIPLINA_TIPO_ELETIVA)).collect(Collectors.toList());
		//Gera lista com as eletivas com estatus aprovado
		List<DisciplinaCursada> eletivasAPV = eletivas.stream().filter(discipline -> ( discipline.getSituacao().equalsIgnoreCase(DisciplineStatus.APROVADO.getStatus()) ) ).collect(Collectors.toList());
		int optativasAPVFlag = optativasAPV.size();
		int eletivasAPVFlag = eletivasAPV.size();
		//Itera sobore os itens PATH onde se encontram as disciplinas
		for( int i = 0; i < pathsNodesList.getLength(); i++) {
			Element pathElement = (Element) pathsNodesList.item(i);
			//Procura uma Disciplina Obrigatória
			if( pathElement.getAttribute("id").matches(DISCIPLINA_REGEX)){
				if( disciplines.containsKey( pathElement.getAttribute("id") ) ) {
					paintElement( disciplines.get( pathElement.getAttribute("id") ),  pathElement);
				}
			}else if(pathElement.getAttribute("id").contains(DISCIPLINA_SVG_TIPO_OPTATIVA) && !optativas.isEmpty()  ) {
				resolveOptativa(optativas, optativasAPV, optativasAPVFlag, pathElement);
			}else if(pathElement.getAttribute("id").contains(DISCIPLINA_SVG_TIPO_ELETIVA) && !eletivas.isEmpty()) {
				resolveEletiva(eletivas, eletivasAPV, eletivasAPVFlag, pathElement);
			}

		}
		return svgIo.toString();
	}

	private static void resolveEletiva(List<DisciplinaCursada> eletivas, List<DisciplinaCursada> eletivasAPV, int eletivasAPVFlag, Element pathElement) {
		resolveNaoObrigatorias(eletivas, eletivasAPV, eletivasAPVFlag, pathElement, NUMERO_DE_ELETIVAS);
	}
	private static void resolveOptativa(List<DisciplinaCursada> optativas, List<DisciplinaCursada> optativasAPV, int optativasAPVFlag, Element pathElement) {
		resolveNaoObrigatorias(optativas, optativasAPV, optativasAPVFlag, pathElement, NUMERO_DE_OPTATIVAS);
	}
	private static void resolveNaoObrigatorias(List<DisciplinaCursada> naoObrigatorias, List<DisciplinaCursada> naoObrigatoriaAPV, int naoObrigatoriaAPVFlag, Element pathElement, int numeroDeNaoObrigatorias) {
		Optional<DisciplinaCursada> sparring;
		if( naoObrigatoriaAPVFlag == numeroDeNaoObrigatorias){
			sparring = naoObrigatoriaAPV.stream().findFirst();
			if( sparring.isPresent() ) {
				paintElement( sparring.get(), pathElement);
				naoObrigatoriaAPV.remove(sparring.get());
			}
		}else{
			sparring = naoObrigatorias.stream().findFirst();
			if( sparring.isPresent() ) {
				paintElement( sparring.get(), pathElement);
				naoObrigatorias.remove(sparring.get());
			}
		}
	}
	private static void paintElement(DisciplinaCursada disciplinaCursada, Element pathElement) {
		if( disciplinaCursada.getSituacao().equalsIgnoreCase(DisciplineStatus.APROVADO.getStatus()) ) {
			pathElement.setAttribute(SvgUsedElements.STYLE.getElement(), SvgFillCollorStyle.APROVADO.getLineStyle());
		}else if( disciplinaCursada.getSituacao().equalsIgnoreCase(DisciplineStatus.REPROVADO.getStatus()) ){
			pathElement.setAttribute(SvgUsedElements.STYLE.getElement(), SvgFillCollorStyle.REPROVADO.getLineStyle());
		}else if( disciplinaCursada.getSituacao().equalsIgnoreCase(DisciplineStatus.MATRICULA.getStatus())  ) {
			pathElement.setAttribute(SvgUsedElements.STYLE.getElement(), SvgFillCollorStyle.CURSANDO.getLineStyle());
		}

	}
}
