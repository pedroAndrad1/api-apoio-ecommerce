package br.com.uniriotec.sagui.svgio;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SvgFillCollorStyle {
	APROVADO ("fill:#87DCC0;fill-opacity:1;fill-rule:evenodd;stroke:#000000;stroke-width:1.22303033px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1"),
	CURSANDO ("fill:#FFF49C;fill-opacity:1;fill-rule:evenodd;stroke:#000000;stroke-width:1.22303033px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1"),
	REPROVADO("fill:#FF756D;fill-opacity:1;fill-rule:evenodd;stroke:#000000;stroke-width:1.22303033px;stroke-linecap:butt;stroke-linejoin:miter;stroke-opacity:1");
	
	@Getter private String lineStyle;
}
