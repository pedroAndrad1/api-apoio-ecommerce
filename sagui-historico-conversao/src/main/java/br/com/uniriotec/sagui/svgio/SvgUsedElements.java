package br.com.uniriotec.sagui.svgio;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SvgUsedElements {
	PATH    ("path"),
	TEXT    ("text"),
	ID      ("id"),
	STYLE   ("style"),
	OPTATIVA("OPTATIVA"),
	ELETIVA ("ELETIVA");
	@Getter private String element; 
}
