package br.com.unirio.sagui.svgIo;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.com.unirio.sagui.model.DisciplinaCursada;

@Component
@Slf4j
public class SvgHandler {
	public String paintSvg( Set<DisciplinaCursada> studentDisciplines ) {
		SvgIo svgIo = new SvgIo("C:\\grade_curricular2.svg");

		HashMap<String, DisciplinaCursada> disciplines = new HashMap<String, DisciplinaCursada>();
		Set<DisciplinaCursada> disciplinasCursadas = studentDisciplines;

		disciplinasCursadas.stream()
		.forEach( discipline -> disciplines.put(discipline.getCodigo(), discipline) );

		//Build a list of all "path" type lines of the svg. The path lines is where the discipline codes are
		NodeList pathsNodesList = svgIo.getNodeListByTag(SvgUsedElements.PATH.getElement());

		List<DisciplinaCursada> optativas = disciplines.values().stream().filter( discipline -> discipline.getTipo().equals("0002") ).collect( Collectors.toList());
		List<DisciplinaCursada> optativasAPV = optativas.stream().filter(discipline -> ( discipline.getSituacao().equalsIgnoreCase("Aprovado") ) ).collect( Collectors.toList());
		List<DisciplinaCursada> eletivas = disciplines.values().stream().filter(discipline -> discipline.getTipo().equals("0004")).collect(Collectors.toList());
		List<DisciplinaCursada> eletivasAPV = eletivas.stream().filter(discipline -> ( discipline.getSituacao().equalsIgnoreCase("Aprovado") ) ).collect( Collectors.toList());
		int optativasAPVFlag = optativasAPV.size();
		int eletivasAPVFlag = eletivasAPV.size();
		log.info( "Tamanho da lista de opt: "+ optativasAPVFlag + " optativas size: "+ optativas.size() + "  opt apv size: " + optativasAPV.size()  );
		for( int i = 0; i < pathsNodesList.getLength(); i++) {
			Element pathElement = (Element) pathsNodesList.item(i);
			Optional<DisciplinaCursada> sparring = null;
			if( pathElement.getAttribute("id").matches("[A-Z]{3}[0-9]{4}")){
				if( disciplines.containsKey( pathElement.getAttribute("id") ) ) {
					paintElement( disciplines.get( pathElement.getAttribute("id") ),  pathElement);
				}
			}else if(pathElement.getAttribute("id").contains("OPTATIVA") && !optativas.isEmpty()  ) {
				if(  optativasAPVFlag  == 8 ) {
					sparring = optativasAPV.stream().findFirst();
					if(sparring.isPresent()){
						paintElement( sparring.get(), pathElement );
						optativasAPV.remove(sparring.get());
					}
				}else {
					sparring = optativas.stream().findFirst();
					if(sparring.isPresent()){
						paintElement( sparring.get(), pathElement );
						optativas.remove(sparring.get());
					}
				}
			}else if(pathElement.getAttribute("id").contains("ELETIVA") && !eletivas.isEmpty()) {
				if( eletivasAPVFlag == 4 ){
					sparring = eletivasAPV.stream().findFirst();
					if( sparring.isPresent() ) {
						paintElement( sparring.get(), pathElement );
						eletivasAPV.remove(sparring.get());
					}
				}else{
					sparring = eletivas.stream().findFirst();
					if( sparring.isPresent() ) {
						paintElement( sparring.get(), pathElement );
						eletivas.remove(sparring.get());
					}
				}
			}

		}

		return svgIo.toString();
	}
	private void paintElement(DisciplinaCursada disciplinaCursada, Element pathElement) {
		// TODO Auto-generated method stub
		if( disciplinaCursada.getSituacao().equalsIgnoreCase("Aprovado") ) {
			pathElement.setAttribute(SvgUsedElements.STYLE.getElement(), SvgFillCollorStyle.APROVADO.getLineStyle());
		}else if( disciplinaCursada.getSituacao().equalsIgnoreCase("Reprovado") ){
			pathElement.setAttribute(SvgUsedElements.STYLE.getElement(), SvgFillCollorStyle.REPROVADO.getLineStyle());
		}else if( disciplinaCursada.getSituacao().equalsIgnoreCase("Matriculado")  ) {
			pathElement.setAttribute(SvgUsedElements.STYLE.getElement(), SvgFillCollorStyle.CURSANDO.getLineStyle());
		}

	}
}
