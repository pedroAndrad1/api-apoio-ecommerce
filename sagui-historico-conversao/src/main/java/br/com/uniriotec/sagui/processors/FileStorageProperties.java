package br.com.uniriotec.sagui.processors;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    @Getter@Setter private String uploadDir;
    @Getter@Setter private String processedDir;
    @Getter@Setter private String pdfSplitedDir;
    @Getter@Setter private String comfirmacaoDir;
    @Getter@Setter private String svgDir;
    @Getter@Setter private String svgBsi;

}
