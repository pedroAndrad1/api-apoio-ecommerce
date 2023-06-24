package br.com.uniriotec.sagui.processors;

import br.com.uniriotec.sagui.pdfIo.PDFIO;
import br.com.uniriotec.sagui.pdfIo.PdfMineLines;
import br.com.uniriotec.sagui.pdfIo.StudentMap;
import lombok.Getter;

/**
 * Faz as classes de pdfIO
 */
@Getter
public class FixtureForPdfProcessor {
    private StudentMap studentMap;
    public FixtureForPdfProcessor( String file ){
        PDFIO pdf = new PDFIO(file);
        PdfMineLines pdfMineLines = new PdfMineLines();
        this.studentMap = pdfMineLines.mineLines( pdf.getLinesList() );
    }
}
