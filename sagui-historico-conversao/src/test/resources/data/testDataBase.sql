-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           8.0.23 - MySQL Community Server - GPL
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para sagui
CREATE DATABASE IF NOT EXISTS `sagui` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sagui`;

-- Copiando estrutura para tabela sagui.aluno
CREATE TABLE IF NOT EXISTS `aluno` (
  `Aluno_Id` bigint NOT NULL AUTO_INCREMENT,
  `NOME_ALUNO` varchar(300) DEFAULT NULL,
  `Matricula` varchar(45) NOT NULL,
  `Entrada_Ano_Periodo` varchar(5) NOT NULL,
  `CRA` double DEFAULT NULL,
  `Curso` varchar(10) NOT NULL,
  `EMAIL` varchar(300) DEFAULT NULL,
  `VERSAO_GRADE` varchar(45) DEFAULT NULL,
  `tutor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`Aluno_Id`),
  UNIQUE KEY `Matricula_UNIQUE` (`Matricula`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`),
  KEY `TUTOR_FK_idx` (`tutor_id`),
  CONSTRAINT `TUTOR_FK` FOREIGN KEY (`tutor_id`) REFERENCES `usuario` (`USUARIO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando estrutura para view sagui.alunos_por_professor
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `alunos_por_professor` (
	`usuario_id` BIGINT(19) NOT NULL,
	`NOME` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`email` VARCHAR(300) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`PERIODO_TUTOR` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`Aluno_Id` BIGINT(19) NOT NULL,
	`nome_aluno` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`matricula` VARCHAR(45) NOT NULL COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=InnoDB;

-- Copiando estrutura para view sagui.carga_horaria_comp_ext
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `carga_horaria_comp_ext` (
	`ALUNO_ID` BIGINT(19) NOT NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD` BIGINT(19) NOT NULL,
	`SEM_ATIVIDADE_EXTENSIONISTA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`ANO_ENTRADA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_PERIODOS` INT(10) NULL
) ENGINE=InnoDB;

-- Copiando estrutura para view sagui.carga_horaria_eletiva
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `carga_horaria_eletiva` (
	`ALUNO_ID` BIGINT(19) NOT NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD` BIGINT(19) NOT NULL,
	`SEM_ELETIVA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`ANO_ENTRADA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_PERIODOS` INT(10) NULL
) ENGINE=InnoDB;

-- Copiando estrutura para view sagui.carga_horaria_optativa
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `carga_horaria_optativa` (
	`ALUNO_ID` BIGINT(19) NOT NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD` BIGINT(19) NOT NULL,
	`SEM_OPTATIVA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`ANO_ENTRADA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_PERIODOS` INT(10) NULL
) ENGINE=InnoDB;

-- Copiando estrutura para tabela sagui.confirmacao_matricula
CREATE TABLE IF NOT EXISTS `confirmacao_matricula` (
  `CONFIRMACAO_MATRICULA_ID` bigint NOT NULL AUTO_INCREMENT,
  `CONF_MAT_TEXT` varchar(10000) DEFAULT NULL,
  `CM_ALUNO_FK` bigint DEFAULT NULL,
  PRIMARY KEY (`CONFIRMACAO_MATRICULA_ID`),
  KEY `CM_ALUNO_FK_idx` (`CM_ALUNO_FK`),
  CONSTRAINT `CM_ALUNO_FK` FOREIGN KEY (`CM_ALUNO_FK`) REFERENCES `aluno` (`Aluno_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.confirmacao_matricula: ~0 rows (aproximadamente)

-- Copiando estrutura para view sagui.consolidaregras
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `consolidaregras` (
	`Aluno_Id` BIGINT(19) NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_CHCE` BIGINT(19) NULL,
	`SEM_ATIVIDADE_EXTENSIONISTA_CHCE` VARCHAR(5) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`ANO_ENTRADA_CHCE` VARCHAR(5) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_PERIODOS_CHCE` BIGINT(19) NULL,
	`QTD_CHE` BIGINT(19) NULL,
	`SEM_ELETIVA_CHE` VARCHAR(5) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_CHO` BIGINT(19) NULL,
	`SEM_OPTATIVA_CHO` VARCHAR(5) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`SOMENTE_REP` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`LIMITE_REP` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_MQ5P` BIGINT(19) NULL,
	`MAIS_MQ5P` VARCHAR(12) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`TRT_GERAL` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`SOMA_PERIODIZADO` DOUBLE NULL,
	`CRA` DOUBLE NULL
) ENGINE=InnoDB;

-- Copiando estrutura para view sagui.consta_reprovacao
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `consta_reprovacao` (
	`DC_ALUNO_FK` BIGINT(19) NOT NULL
) ENGINE=InnoDB;

-- Copiando estrutura para tabela sagui.cr_periodizado
CREATE TABLE IF NOT EXISTS `cr_periodizado` (
  `CR_PERIODIZADO_ID` bigint NOT NULL AUTO_INCREMENT,
  `CR_P_ALUNO_FK` bigint NOT NULL,
  `CR` double NOT NULL,
  `ANO_PERIODO` varchar(4) DEFAULT NULL,
  `carga_creditos` varchar(5) DEFAULT NULL,
  `carga_horaria` varchar(5) DEFAULT NULL,
  `periodo` varchar(22) NOT NULL,
  PRIMARY KEY (`CR_PERIODIZADO_ID`),
  UNIQUE KEY `UNIQUE_ALUNO_PERIODO_UNQ` (`CR_P_ALUNO_FK`,`ANO_PERIODO`) /*!80000 INVISIBLE */,
  KEY `fk_CR_PERIODIZADO_ALUNO1_idx` (`CR_P_ALUNO_FK`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_CR_PERIODIZADO_ALUNO1` FOREIGN KEY (`CR_P_ALUNO_FK`) REFERENCES `aluno` (`Aluno_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela sagui.disciplinas_periodo
CREATE TABLE IF NOT EXISTS `disciplinas_periodo` (
  `DISICPLINAS_PERIODO_ID` bigint NOT NULL AUTO_INCREMENT,
  `PERIODO` int DEFAULT NULL,
  `CURSO` varchar(3) DEFAULT NULL,
  `POR_PERIODO` int DEFAULT NULL,
  `IS_50_PER_CENT` varchar(5) DEFAULT NULL,
  `SOMATORIO` int DEFAULT NULL,
  PRIMARY KEY (`DISICPLINAS_PERIODO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.disciplinas_periodo: ~8 rows (aproximadamente)
INSERT INTO `disciplinas_periodo` (`DISICPLINAS_PERIODO_ID`, `PERIODO`, `CURSO`, `POR_PERIODO`, `IS_50_PER_CENT`, `SOMATORIO`) VALUES
	(1, 1, '210', 6, 'FALSE', 6),
	(2, 2, '210', 6, 'FALSE', 12),
	(3, 3, '210', 7, 'FALSE', 19),
	(4, 4, '210', 7, 'TRUE', 26),
	(5, 5, '210', 7, 'FALSE', 33),
	(6, 6, '210', 6, 'FALSE', 39),
	(7, 7, '210', 6, 'FALSE', 45),
	(8, 8, '210', 6, 'FALSE', 51);

-- Copiando estrutura para tabela sagui.disciplina_cursada
CREATE TABLE IF NOT EXISTS `disciplina_cursada` (
  `Disciplina_Cursada_Id` bigint NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(45) NOT NULL,
  `DC_ALUNO_FK` bigint NOT NULL,
  `TITULO` varchar(300) DEFAULT NULL,
  `CREDITOS` double DEFAULT NULL,
  `NOTA` double DEFAULT NULL,
  `SITUACAO` varchar(45) DEFAULT NULL,
  `QTD_CURSADA` int DEFAULT NULL,
  `QTD_REPROVACAO` int DEFAULT NULL,
  `CURSO` varchar(45) DEFAULT NULL,
  `TIPO` varchar(4) DEFAULT NULL,
  `PERIODO` int DEFAULT NULL,
  `VERSAO_GRADE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Disciplina_Cursada_Id`),
  UNIQUE KEY `CURSO_ALUNO_FK_UNQ` (`CODIGO`,`DC_ALUNO_FK`),
  KEY `CD_ALUNO_FK_idx` (`DC_ALUNO_FK`) /*!80000 INVISIBLE */,
  CONSTRAINT `CD_ALUNO_FK` FOREIGN KEY (`DC_ALUNO_FK`) REFERENCES `aluno` (`Aluno_Id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	
-- Copiando estrutura para função sagui.EM_SITUACAO_JUBILAMENTO
DELIMITER //
CREATE FUNCTION `EM_SITUACAO_JUBILAMENTO`( IN_ALUNO_ID INT ) RETURNS int
    DETERMINISTIC
BEGIN

	DECLARE rCO_RE INT;

	DECLARE rQTD INT;

      SELECT (SUM(CR) / COUNT(CR)) INTO rCO_RE

	  FROM CR_PERIODIZADO

      WHERE CONCAT(SUBSTRING(PERIODO, 17, 20), SUBSTRING(PERIODO, 1, 1) ) <> GET_PERIODO_ATUAL() 

      AND CR_P_ALUNO_FK = IN_ALUNO_ID;

      

      SELECT COUNT(QTD_REPROVACAO) INTO rQTD

      FROM DISCIPLINA_CURSADA 

      WHERE QTD_REPROVACAO > 3

      AND DC_ALUNO_FK = IN_ALUNO_ID;

      

      IF ( rCO_RE < 4 AND rQTD > 3 ) THEN

		RETURN 1;

	  ELSE RETURN 0;

      END IF;

END//
DELIMITER ;

-- Copiando estrutura para função sagui.GET_PERIODO
DELIMITER //
CREATE FUNCTION `GET_PERIODO`(IN_ALUNO_ID INT) RETURNS int
    DETERMINISTIC
BEGIN

	DECLARE rPeriodo INT;

	##Periodos

	SELECT ( ( ( SELECT PERIODO_ID FROM PERIODOS WHERE PERIODO =  CONCAT(YEAR(SYSDATE()), CASE WHEN MONTH(SYSDATE()) <7 THEN 1 ELSE 2 END) ) -  PERIODO_ID ) +  1 ) as CountPeriodos into rPeriodo

	FROM PERIODOS

    WHERE PERIODO = (SELECT ENTRADA_ANO_PERIODO FROM ALUNO WHERE ALUNO_ID = IN_ALUNO_ID );

	RETURN rPeriodo;

END//
DELIMITER ;

-- Copiando estrutura para função sagui.GET_PERIODO_ATUAL
DELIMITER //
CREATE FUNCTION `GET_PERIODO_ATUAL`() RETURNS int
    DETERMINISTIC
BEGIN

	DECLARE rPeriodo INT;

	SELECT CONCAT(YEAR(SYSDATE()), CASE WHEN MONTH(SYSDATE()) <7 THEN 1 ELSE 2 END) as Semestre INTO rPERIODO;

	RETURN rPeriodo;

END//
DELIMITER ;

-- Copiando estrutura para tabela sagui.grade
CREATE TABLE IF NOT EXISTS `grade` (
  `GRADE_ID` bigint NOT NULL AUTO_INCREMENT,
  `CODIGO` varchar(45) NOT NULL,
  `TIPO` varchar(4) DEFAULT NULL,
  `CREDITOS` double DEFAULT NULL,
  `PERIODO` int DEFAULT NULL,
  `NOME` varchar(300) DEFAULT NULL,
  `CURSO` varchar(45) DEFAULT NULL,
  `VERSAO_GRADE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`GRADE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.grade: ~81 rows (aproximadamente)
INSERT INTO `grade` (`GRADE_ID`, `CODIGO`, `TIPO`, `CREDITOS`, `PERIODO`, `NOME`, `CURSO`, `VERSAO_GRADE`) VALUES
	(1, 'HTD0058', '0001', 3, 1, 'TEORIAS E PRÁTICAS DISCURSIVAS', '210', '20081'),
	(2, 'TIN0011', '0001', 5, 2, 'TÉCNICAS DE PROGRAMAÇÃO II', '210', '20081'),
	(3, 'TIN0013', '0001', 4, 2, 'ANÁLISE EMPRESARIAL E ADMINISTRATIVA', '210', '20081'),
	(4, 'TIN0054', '0001', 3, 5, 'ATIVIDADES COMPLEMENTARES DE EXTENSÃO 1', '210', '20081'),
	(5, 'TIN0055', '0001', 3, 6, 'ATIVIDADES COMPLEMENTARES DE EXTENSÃO 2', '210', '20081'),
	(6, 'TIN0056', '0001', 3, 7, 'ATIVIDADES COMPLEMENTARES DE EXTENSÃO 3', '210', '20081'),
	(7, 'TIN0057', '0001', 3, 8, 'ATIVIDADES COMPLEMENTARES DE EXTENSÃO 4', '210', '20081'),
	(8, 'TIN0105', '0001', 4, 2, 'INTRODUÇÃO À LÓGICA COMPUTACIONAL', '210', '20081'),
	(9, 'TIN0106', '0001', 4, 1, 'DESENVOLVIMENTO DE PÁGINAS WEB', '210', '20081'),
	(10, 'TIN0107', '0001', 5, 1, 'TÉCNICAS DE PROGRAMAÇÃO I', '210', '20081'),
	(11, 'TIN0108', '0001', 4, 1, 'ORGANIZAÇÃO DE COMPUTADORES', '210', '20081'),
	(12, 'TIN0109', '0001', 4, 3, 'ESTRUTURAS DISCRETAS', '210', '20081'),
	(13, 'TIN0110', '0001', 4, 4, 'INTERAÇÃO HUMANO-COMPUTADOR', '210', '20081'),
	(14, 'TIN0112', '0001', 4, 1, 'FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO', '210', '20081'),
	(15, 'TIN0114', '0001', 4, 3, 'ESTRUTURAS DE DADOS I', '210', '20081'),
	(16, 'TIN0115', '0001', 4, 4, 'ANÁLISE DE SISTEMAS', '210', '20081'),
	(17, 'TIN0116', '0001', 4, 3, 'SISTEMAS OPERACIONAIS', '210', '20081'),
	(18, 'TIN0117', '0001', 4, 6, 'ADMINISTRAÇÃO FINANCEIRA', '210', '20081'),
	(19, 'TIN0118', '0001', 4, 5, 'ANÁLISE DE ALGORITMOS', '210', '20081'),
	(20, 'TIN0119', '0001', 4, 4, 'LINGUAGENS FORMAIS E AUTÔMATOS', '210', '20081'),
	(21, 'TIN0120', '0001', 4, 3, 'BANCOS DE DADOS I', '210', '20081'),
	(22, 'TIN0121', '0001', 4, 6, 'PROGRAMAÇÃO MODULAR', '210', '20081'),
	(23, 'TIN0122', '0001', 4, 7, 'PROCESSOS DE SOFTWARE', '210', '20081'),
	(24, 'TIN0123', '0001', 4, 4, 'REDES DE COMPUTADORES I', '210', '20081'),
	(25, 'TIN0125', '0001', 4, 6, 'PROJETO E CONSTRUÇÃO DE SISTEMAS COM SGBD', '210', '20081'),
	(26, 'TIN0126', '0001', 4, 5, 'REDES DE COMPUTADORES II', '210', '20081'),
	(27, 'TIN0130', '0001', 4, 5, 'EMPREENDEDORISMO', '210', '20081'),
	(28, 'TIN0131', '0001', 3, 7, 'PROJETO DE GRADUAÇÃO I', '210', '20081'),
	(29, 'TIN0132', '0001', 4, 8, 'GERÊNCIA DE PROJETOS DE INFORMÁTICA', '210', '20081'),
	(30, 'TIN0133', '0001', 3, 8, 'PROJETO DE GRADUAÇÃO II', '210', '20081'),
	(31, 'TIN0168', '0001', 4, 4, 'ESTRUTURAS DE DADOS II', '210', '20081'),
	(32, 'TIN0169', '0001', 4, 5, 'BANCOS DE DADOS II', '210', '20081'),
	(33, 'TIN0171', '0001', 4, 5, 'PROJETO E CONSTRUÇÃO DE SISTEMAS', '210', '20081'),
	(34, 'TME0101', '0001', 2, 1, 'MATEMÁTICA BÁSICA', '210', '20081'),
	(35, 'TME0015', '0001', 4, 2, 'ÁLGEBRA LINEAR', '210', '20081'),
	(36, 'TME0112', '0001', 4, 2, 'CÁLCULO DIFERENCIAL E INTEGRAL I', '210', '20081'),
	(37, 'TME0113', '0001', 4, 3, 'CÁLCULO DIFERENCIAL E INTEGRAL II', '210', '20081'),
	(38, 'TME0114', '0001', 4, 3, 'PROBABILIDADE', '210', '20081'),
	(39, 'TME0115', '0001', 4, 4, 'ESTATÍSTICA', '210', '20081'),
	(40, 'TIN0151', '0004', 4, 2, 'FORMAÇÃO COMPLEMENTAR I', '210', '20081'),
	(41, 'TIN0152', '0004', 4, 3, 'FORMAÇÃO COMPLEMENTAR II', '210', '20081'),
	(42, 'TIN0153', '0004', 4, 4, 'FORMAÇÃO COMPLEMENTAR III', '210', '20081'),
	(43, 'TIN0154', '0004', 4, 5, 'FORMAÇÃO COMPLEMENTAR IV', '210', '20081'),
	(44, 'REP0001', '0004', 4, 2, 'ELETIVA I', '210', '20081'),
	(45, 'REP0002', '0004', 4, 3, 'ELETIVA II', '210', '20081'),
	(46, 'REP0003', '0004', 4, 4, 'ELETIVA |||', '210', '20081'),
	(47, 'REP0004', '0004', 4, 5, 'ELETIVA ||||', '210', '20081'),
	(48, 'REP0005', '0005', 5, 6, 'OPTATIVA I', '210', '20081'),
	(49, 'REP0006', '0005', 5, 6, 'OPTATIVA II', '210', '20081'),
	(50, 'REP0007', '0005', 5, 7, 'OPTATIVA III', '210', '20081'),
	(51, 'REP0008', '0005', 5, 7, 'OPTATIVA IV', '210', '20081'),
	(52, 'REP0009', '0005', 5, 7, 'OPTATIVA V', '210', '20081'),
	(53, 'REP0010', '0005', 5, 8, 'OPTATIVA VI', '210', '20081'),
	(54, 'REP0011', '0005', 5, 8, 'OPTATIVA VII', '210', '20081'),
	(55, 'REP0012', '0005', 5, 8, 'OPTATIVA VIII', '210', '20081'),
	(56, 'TIN0135', '0002', 4, 99, 'ADMINIST. DE BANCO DE DADOS', '210', '20081'),
	(57, 'TIN0144', '0002', 4, 99, 'ALGORITMOS P/ PROB. COMBINAT.', '210', '20081'),
	(58, 'TIN0150', '0002', 4, 99, 'AMBIENTE OPERACIONAL UNIX', '210', '20081'),
	(59, 'TIN0146', '0002', 4, 99, 'COMPILADORES', '210', '20081'),
	(60, 'TIN0149', '0002', 4, 99, 'COMPUTAÇÃO GRÁFICA', '210', '20081'),
	(61, 'TIN0138', '0002', 4, 99, 'COMUNIC. E SEGURANÇA DE DADOS', '210', '20081'),
	(62, 'TIN0158', '0002', 4, 99, 'DESENVOLV. DE SERVIDOR WEB', '210', '20081'),
	(63, 'TIN0143', '0002', 4, 99, 'FLUXOS EM REDES', '210', '20081'),
	(64, 'TIN0147', '0002', 4, 99, 'FUND. REPR. CONH. E RACIOCÍNIO', '210', '20081'),
	(65, 'TIN0136', '0002', 4, 99, 'GERÊNCIA DE DADOS EM AMB. DISTRIBUÍDOS E PARALELOS', '210', '20081'),
	(66, 'TIN0160', '0002', 4, 99, 'GEST. DE PROCESSOS DE NEGÓCIOS', '210', '20081'),
	(67, 'TIN0128', '0002', 4, 99, 'INFORMÁTICA NA EDUCAÇÃO', '210', '20081'),
	(68, 'TIN0172', '0002', 4, 99, 'INTELIGÊNCIA ARTIFICIAL', '210', '20081'),
	(69, 'TIN0142', '0002', 4, 99, 'PROGRAMAÇÃO LINEAR', '210', '20081'),
	(70, 'TIN0159', '0002', 4, 99, 'SISTEMAS COLABORATIVOS', '210', '20081'),
	(71, 'TIN0148', '0002', 4, 99, 'SISTEMAS MULTIMÍDIA', '210', '20081'),
	(72, 'TIN0145', '0002', 4, 99, 'TÓP. AVANÇADOS EM ALGORITMOS', '210', '20081'),
	(73, 'TIN0137', '0002', 4, 99, 'TÓP. AVANÇADOS EM BD I', '210', '20081'),
	(74, 'TIN0162', '0002', 4, 99, 'TÓP. AVANÇADOS EM BD II', '210', '20081'),
	(75, 'TIN0163', '0002', 4, 99, 'TÓP. AVANÇADOS EM BD III', '210', '20081'),
	(76, 'TIN0161', '0002', 4, 99, 'TÓP. AVANÇADOS EM ENG. SW. I', '210', '20081'),
	(77, 'TIN0166', '0002', 4, 99, 'TÓP. AVANÇADOS EM ENG. SW. II', '210', '20081'),
	(78, 'TIN0141', '0002', 4, 99, 'TÓP. AVAN. EM REDES DE COMP. I', '210', '20081'),
	(79, 'TIN0164', '0002', 4, 99, 'TÓP. AVAN. EM REDES DE COMP. II', '210', '20081'),
	(80, 'TIN0165', '0002', 4, 99, 'TÓP. AVAN. EM REDES DE COMP. III', '210', '20081'),
	(81, 'TRT0001', '0003', 0, 99, 'TRANCAMENTO GERAL', '210', '20081');

-- Copiando estrutura para view sagui.limite_reprovacoes_atingido
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `limite_reprovacoes_atingido` (
	`ALUNO_ID` BIGINT(19) NOT NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_REPROVACAO` INT(10) NULL
) ENGINE=InnoDB;

-- Copiando estrutura para view sagui.mais_que_50_por_cento
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `mais_que_50_por_cento` (
	`ALUNO_ID` BIGINT(19) NOT NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`CURSO` VARCHAR(10) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD` BIGINT(19) NOT NULL,
	`ANO_ENTRADA` VARCHAR(5) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_PERIODOS` INT(10) NULL,
	`50_PER_CENT_CURSADO` VARCHAR(12) NOT NULL COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=InnoDB;

-- Copiando estrutura para tabela sagui.periodos
CREATE TABLE IF NOT EXISTS `periodos` (
  `PERIODO_ID` bigint NOT NULL AUTO_INCREMENT,
  `PERIODO` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`PERIODO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.periodos: ~205 rows (aproximadamente)
INSERT INTO `periodos` (`PERIODO_ID`, `PERIODO`) VALUES
	(204, NULL),
	(205, '19981'),
	(206, '19982'),
	(207, '19991'),
	(208, '19992'),
	(209, '20001'),
	(210, '20002'),
	(211, '20011'),
	(212, '20012'),
	(213, '20021'),
	(214, '20022'),
	(215, '20031'),
	(216, '20032'),
	(217, '20041'),
	(218, '20042'),
	(219, '20051'),
	(220, '20052'),
	(221, '20061'),
	(222, '20062'),
	(223, '20071'),
	(224, '20072'),
	(225, '20081'),
	(226, '20082'),
	(227, '20091'),
	(228, '20092'),
	(229, '20101'),
	(230, '20102'),
	(231, '20111'),
	(232, '20112'),
	(233, '20121'),
	(234, '20122'),
	(235, '20131'),
	(236, '20132'),
	(237, '20141'),
	(238, '20142'),
	(239, '20151'),
	(240, '20152'),
	(241, '20161'),
	(242, '20162'),
	(243, '20171'),
	(244, '20172'),
	(245, '20181'),
	(246, '20182'),
	(247, '20191'),
	(248, '20192'),
	(249, '20201'),
	(250, '20202'),
	(251, '20211'),
	(252, '20212'),
	(253, '20221'),
	(254, '20222'),
	(255, '20231'),
	(256, '20232'),
	(257, '20241'),
	(258, '20242'),
	(259, '20251'),
	(260, '20252'),
	(261, '20261'),
	(262, '20262'),
	(263, '20271'),
	(264, '20272'),
	(265, '20281'),
	(266, '20282'),
	(267, '20291'),
	(268, '20292'),
	(269, '20301'),
	(270, '20302'),
	(271, '20311'),
	(272, '20312'),
	(273, '20321'),
	(274, '20322'),
	(275, '20331'),
	(276, '20332'),
	(277, '20341'),
	(278, '20342'),
	(279, '20351'),
	(280, '20352'),
	(281, '20361'),
	(282, '20362'),
	(283, '20371'),
	(284, '20372'),
	(285, '20381'),
	(286, '20382'),
	(287, '20391'),
	(288, '20392'),
	(289, '20401'),
	(290, '20402'),
	(291, '20411'),
	(292, '20412'),
	(293, '20421'),
	(294, '20422'),
	(295, '20431'),
	(296, '20432'),
	(297, '20441'),
	(298, '20442'),
	(299, '20451'),
	(300, '20452'),
	(301, '20461'),
	(302, '20462'),
	(303, '20471'),
	(304, '20472'),
	(305, '20481'),
	(306, '20482'),
	(307, '20491'),
	(308, '20492'),
	(309, '20501'),
	(310, '20502'),
	(311, '20511'),
	(312, '20512'),
	(313, '20521'),
	(314, '20522'),
	(315, '20531'),
	(316, '20532'),
	(317, '20541'),
	(318, '20542'),
	(319, '20551'),
	(320, '20552'),
	(321, '20561'),
	(322, '20562'),
	(323, '20571'),
	(324, '20572'),
	(325, '20581'),
	(326, '20582'),
	(327, '20591'),
	(328, '20592'),
	(329, '20601'),
	(330, '20602'),
	(331, '20611'),
	(332, '20612'),
	(333, '20621'),
	(334, '20622'),
	(335, '20631'),
	(336, '20632'),
	(337, '20641'),
	(338, '20642'),
	(339, '20651'),
	(340, '20652'),
	(341, '20661'),
	(342, '20662'),
	(343, '20671'),
	(344, '20672'),
	(345, '20681'),
	(346, '20682'),
	(347, '20691'),
	(348, '20692'),
	(349, '20701'),
	(350, '20702'),
	(351, '20711'),
	(352, '20712'),
	(353, '20721'),
	(354, '20722'),
	(355, '20731'),
	(356, '20732'),
	(357, '20741'),
	(358, '20742'),
	(359, '20751'),
	(360, '20752'),
	(361, '20761'),
	(362, '20762'),
	(363, '20771'),
	(364, '20772'),
	(365, '20781'),
	(366, '20782'),
	(367, '20791'),
	(368, '20792'),
	(369, '20801'),
	(370, '20802'),
	(371, '20811'),
	(372, '20812'),
	(373, '20821'),
	(374, '20822'),
	(375, '20831'),
	(376, '20832'),
	(377, '20841'),
	(378, '20842'),
	(379, '20851'),
	(380, '20852'),
	(381, '20861'),
	(382, '20862'),
	(383, '20871'),
	(384, '20872'),
	(385, '20881'),
	(386, '20882'),
	(387, '20891'),
	(388, '20892'),
	(389, '20901'),
	(390, '20902'),
	(391, '20911'),
	(392, '20912'),
	(393, '20921'),
	(394, '20922'),
	(395, '20931'),
	(396, '20932'),
	(397, '20941'),
	(398, '20942'),
	(399, '20951'),
	(400, '20952'),
	(401, '20961'),
	(402, '20962'),
	(403, '20971'),
	(404, '20972'),
	(405, '20981'),
	(406, '20982'),
	(407, '20991'),
	(408, '20992');

-- Copiando estrutura para tabela sagui.plano_de_integralizacao
CREATE TABLE IF NOT EXISTS `plano_de_integralizacao` (
  `PLANO_DE_INTEGRALIZACAO_ID` bigint NOT NULL AUTO_INCREMENT,
  `PLANO_TEXT` varchar(10000) DEFAULT NULL,
  `DATA_ENVIO` date DEFAULT NULL,
  `PI_ALUNO_FK` bigint DEFAULT NULL,
  PRIMARY KEY (`PLANO_DE_INTEGRALIZACAO_ID`),
  KEY `PI_ALUNO_FK_idx` (`PI_ALUNO_FK`),
  CONSTRAINT `PI_ALUNO_FK` FOREIGN KEY (`PI_ALUNO_FK`) REFERENCES `aluno` (`Aluno_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.plano_de_integralizacao: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela sagui.regras_aplicadas
CREATE TABLE IF NOT EXISTS `regras_aplicadas` (
  `REGRAS_A_ID` bigint NOT NULL AUTO_INCREMENT,
  `REGRAS_A_ALUNO_FK` bigint NOT NULL,
  `CR_MAIOR_QUE_4` varchar(3) NOT NULL,
  `NESSESSITA_PLANO_I` varchar(3) NOT NULL,
  `CONCLUIR_NO_TEMPO` varchar(3) DEFAULT NULL,
  `CR_PLANO_I_RESPECTED` varchar(3) DEFAULT NULL,
  `JUBILAMENTO` varchar(3) DEFAULT NULL,
  `QTD_REPROVACOES` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`REGRAS_A_ID`),
  UNIQUE KEY `REGRAS_A_ALUNO_FK` (`REGRAS_A_ALUNO_FK`),
  KEY `fk_REGRAS_APLICADAS_ALUNO1_idx` (`REGRAS_A_ALUNO_FK`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_REGRAS_APLICADAS_ALUNO1` FOREIGN KEY (`REGRAS_A_ALUNO_FK`) REFERENCES `aluno` (`Aluno_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.regras_aplicadas: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela sagui.role
CREATE TABLE IF NOT EXISTS `role` (
  `ROLE_ID` bigint NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.role: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela sagui.svg
CREATE TABLE IF NOT EXISTS `svg` (
  `SGV_ID` bigint NOT NULL AUTO_INCREMENT,
  `SVG` longtext NOT NULL,
  `ALUNO_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`SGV_ID`),
  UNIQUE KEY `ALUNO_ID_UNIQUE` (`ALUNO_ID`),
  KEY `FK_ALUNO_ID_idx` (`ALUNO_ID`),
  CONSTRAINT `FK_ALUNO_ID` FOREIGN KEY (`ALUNO_ID`) REFERENCES `aluno` (`Aluno_Id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando estrutura para view sagui.trancamentos_gerais_atingido
-- Criando tabela temporária para evitar erros de dependência de VIEW
CREATE TABLE `trancamentos_gerais_atingido` (
	`ALUNO_ID` BIGINT(19) NOT NULL,
	`NOME_ALUNO` VARCHAR(300) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`CODIGO` VARCHAR(45) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`QTD_CURSADA` INT(10) NULL
) ENGINE=InnoDB;

-- Copiando estrutura para tabela sagui.tutoria
CREATE TABLE IF NOT EXISTS `tutoria` (
  `TUTORIA_ID` bigint NOT NULL AUTO_INCREMENT,
  `PERIODO_TUTOR` varchar(5) NOT NULL,
  `TUTOR_USUARIO_FK` bigint NOT NULL,
  PRIMARY KEY (`TUTORIA_ID`),
  UNIQUE KEY `PERIODO_TUTOR_UNIQUE` (`PERIODO_TUTOR`),
  KEY `TUTOR_USUARIO_FK_idx` (`TUTOR_USUARIO_FK`) /*!80000 INVISIBLE */,
  CONSTRAINT `TUTOR_USUARIO_FK` FOREIGN KEY (`TUTOR_USUARIO_FK`) REFERENCES `usuario` (`USUARIO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela sagui.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `USER_ROLE_ID` bigint NOT NULL AUTO_INCREMENT,
  `USUARIO_ID` bigint NOT NULL,
  `ROLE_ID` bigint NOT NULL,
  PRIMARY KEY (`USER_ROLE_ID`),
  KEY `CD_USUARIO_FK` (`USUARIO_ID`),
  KEY `CD_ROLE_FK` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela sagui.user_role: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela sagui.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `USUARIO_ID` bigint NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(300) NOT NULL,
  `NOME` varchar(300) DEFAULT NULL,
  `OAUTH_KEY` varchar(500) DEFAULT NULL,
  `SENHA` varchar(16) DEFAULT NULL,
  `LAST_LOGIN` datetime DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `LOGIN_ATTEMPTS` int DEFAULT NULL,
  PRIMARY KEY (`USUARIO_ID`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`),
  UNIQUE KEY `OAUTH_KEY_UNIQUE` (`OAUTH_KEY`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para trigger sagui.FixGradeFieldsInsert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `FixGradeFieldsInsert` BEFORE INSERT ON `disciplina_cursada` FOR EACH ROW BEGIN
	declare flag integer;
	declare flag2 integer;

	-- Checa se a disciplina existe na tabela GRADE para o curso e código
    SET flag := (SELECT COUNT(*) FROM GRADE WHERE CODIGO = new.CODIGO AND CURSO = new.CURSO); 
    -- Checa se a disciplina existe na tabela GRADE para o código
	SET flag2 := (SELECT COUNT(*) FROM GRADE WHERE CODIGO = new.CODIGO); 
-- Se a disciplina existe e é do curso passado então altera com o tipo. Obrigatória 
 IF (flag > 0) THEN    
    SELECT NOME, CURSO, TIPO, VERSAO_GRADE, CREDITOS, PERIODO
	INTO @inNome, @inCUrso, @inTipo, @inVersao_Grade, @inCreditos, @inPeriodos
	FROM GRADE 
	WHERE CODIGO = new.CODIGO
    AND CURSO = new.CURSO;

	SET new.TITULO = @inNome;
	SET new.CURSO = @inCurso;
	SET new.TIPO = @inTIpo;
	SET new.VERSAO_GRADE = @inVersao_Grade;
	SET new.CREDITOS = @inCreditos;
	SET new.PERIODO = @inPeriodos;
ELSEIF (flag2 > 0) THEN
	SELECT NOME, CURSO, VERSAO_GRADE, CREDITOS, PERIODO
	INTO @inNome, @inCUrso, @inVersao_Grade, @inCreditos, @inPeriodos
	FROM GRADE 
	WHERE CODIGO = new.CODIGO;
    
	SET new.TITULO = @inNome;
	SET new.CURSO = @inCurso;
	SET new.TIPO = '0004';
	SET new.VERSAO_GRADE = @inVersao_Grade;
	SET new.CREDITOS = @inCreditos;
	SET new.PERIODO = @inPeriodos;
ELSE 
	SET new.TITULO = 'Não Cadastrado';
	SET new.CURSO = 'N/A';
	SET new.TIPO = '0004';
	SET new.VERSAO_GRADE = 'N/A';
	SET new.CREDITOS = 0;
	SET new.PERIODO = 99;
END IF;    
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Copiando estrutura para trigger sagui.FixGradeFieldsUpdate
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `FixGradeFieldsUpdate` BEFORE UPDATE ON `disciplina_cursada` FOR EACH ROW BEGIN
	declare flag integer;
	declare flag2 integer;

	-- Checa se a disciplina existe na tabela GRADE para o curso e código
    SET flag := (SELECT COUNT(*) FROM GRADE WHERE CODIGO = new.CODIGO AND CURSO = new.CURSO);
    -- Checa se a disciplina existe na tabela GRADE para o código
	SET flag2 := (SELECT COUNT(*) FROM GRADE WHERE CODIGO = new.CODIGO); 
    
-- Se a disciplina existe e é do curso passado então altera com o tipo. Obrigatória 
 IF (flag > 0) THEN    
    SELECT NOME, CURSO, TIPO, VERSAO_GRADE, CREDITOS, PERIODO
	INTO @inNome, @inCUrso, @inTipo, @inVersao_Grade, @inCreditos, @inPeriodos
	FROM GRADE 
	WHERE CODIGO = new.CODIGO
    AND CURSO = new.CURSO;

	SET new.TITULO = @inNome;
	SET new.CURSO = @inCurso;
	SET new.TIPO = @inTIpo;
	SET new.VERSAO_GRADE = @inVersao_Grade;
	SET new.CREDITOS = @inCreditos;
	SET new.PERIODO = @inPeriodos;
ELSEIF (flag2 > 0) THEN
	SELECT NOME, CURSO, VERSAO_GRADE, CREDITOS, PERIODO
	INTO @inNome, @inCUrso, @inVersao_Grade, @inCreditos, @inPeriodos
	FROM GRADE 
	WHERE CODIGO = new.CODIGO;
    
	SET new.TITULO = @inNome;
	SET new.CURSO = @inCurso;
	SET new.TIPO = '0004';
	SET new.VERSAO_GRADE = @inVersao_Grade;
	SET new.CREDITOS = @inCreditos;
	SET new.PERIODO = @inPeriodos;
ELSE 
	SET new.TITULO = 'Não Cadastrado';
	SET new.CURSO = 'N/A';
	SET new.TIPO = '0004';
	SET new.VERSAO_GRADE = 'N/A';
	SET new.CREDITOS = 0;
	SET new.PERIODO = 99;
END IF;    
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Copiando estrutura para view sagui.alunos_por_professor
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `alunos_por_professor`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `alunos_por_professor` AS select `a`.`USUARIO_ID` AS `usuario_id`,`a`.`NOME` AS `NOME`,`a`.`EMAIL` AS `email`,`b`.`PERIODO_TUTOR` AS `PERIODO_TUTOR`,`c`.`Aluno_Id` AS `Aluno_Id`,`c`.`NOME_ALUNO` AS `nome_aluno`,`c`.`Matricula` AS `matricula` from ((`usuario` `a` join `tutoria` `b`) join `aluno` `c`) where ((`a`.`USUARIO_ID` = `b`.`TUTOR_USUARIO_FK`) and (`b`.`PERIODO_TUTOR` = `c`.`Entrada_Ano_Periodo`));

-- Copiando estrutura para view sagui.carga_horaria_comp_ext
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `carga_horaria_comp_ext`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `carga_horaria_comp_ext` AS select `a`.`Aluno_Id` AS `ALUNO_ID`,`a`.`NOME_ALUNO` AS `NOME_ALUNO`,count(`d`.`CODIGO`) AS `QTD`,(case when (count(`d`.`CODIGO`) = 0) then 'TRUE' else 'FALSE' end) AS `SEM_ATIVIDADE_EXTENSIONISTA`,`a`.`Entrada_Ano_Periodo` AS `ANO_ENTRADA`,`GET_PERIODO`(`a`.`Aluno_Id`) AS `QTD_PERIODOS` from (`aluno` `a` left join `disciplina_cursada` `d` on(((`d`.`DC_ALUNO_FK` = `a`.`Aluno_Id`) and (upper(`d`.`TITULO`) like '%EXTENSÃO%') and (`d`.`SITUACAO` = 'Aprovado')))) group by `a`.`Aluno_Id`,`a`.`NOME_ALUNO`;

-- Copiando estrutura para view sagui.carga_horaria_eletiva
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `carga_horaria_eletiva`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `carga_horaria_eletiva` AS select `a`.`Aluno_Id` AS `ALUNO_ID`,`a`.`NOME_ALUNO` AS `NOME_ALUNO`,count(`d`.`CODIGO`) AS `QTD`,(case when (count(`d`.`CODIGO`) = 0) then 'TRUE' else 'FALSE' end) AS `SEM_ELETIVA`,`a`.`Entrada_Ano_Periodo` AS `ANO_ENTRADA`,`GET_PERIODO`(`a`.`Aluno_Id`) AS `QTD_PERIODOS` from (`aluno` `a` join `disciplina_cursada` `d`) where ((`a`.`Aluno_Id` = `d`.`DC_ALUNO_FK`) and ((`d`.`CURSO` <> `a`.`Curso`) or (`d`.`CURSO` is null))) group by `a`.`Aluno_Id`,`a`.`NOME_ALUNO`;

-- Copiando estrutura para view sagui.carga_horaria_optativa
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `carga_horaria_optativa`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `carga_horaria_optativa` AS select `a`.`Aluno_Id` AS `ALUNO_ID`,`a`.`NOME_ALUNO` AS `NOME_ALUNO`,count(`d`.`CODIGO`) AS `QTD`,(case when (count(`d`.`CODIGO`) = 0) then 'TRUE' else 'FALSE' end) AS `SEM_OPTATIVA`,`a`.`Entrada_Ano_Periodo` AS `ANO_ENTRADA`,`GET_PERIODO`(`a`.`Aluno_Id`) AS `QTD_PERIODOS` from (`aluno` `a` left join `disciplina_cursada` `d` on(((`a`.`Aluno_Id` = `d`.`DC_ALUNO_FK`) and (`d`.`TIPO` = '0002') and (`d`.`SITUACAO` = 'Aprovado')))) group by `a`.`Aluno_Id`,`a`.`NOME_ALUNO`;

-- Copiando estrutura para view sagui.consolidaregras
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `consolidaregras`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `consolidaregras` AS select `aluno`.`Aluno_Id` AS `Aluno_Id`,`aluno`.`NOME_ALUNO` AS `NOME_ALUNO`,(select `a`.`QTD` from `carga_horaria_comp_ext` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `QTD_CHCE`,(select `a`.`SEM_ATIVIDADE_EXTENSIONISTA` from `carga_horaria_comp_ext` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `SEM_ATIVIDADE_EXTENSIONISTA_CHCE`,(select `a`.`ANO_ENTRADA` from `carga_horaria_comp_ext` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `ANO_ENTRADA_CHCE`,(select `a`.`QTD_PERIODOS` from `carga_horaria_comp_ext` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `QTD_PERIODOS_CHCE`,(case when ((select `b`.`QTD` from `carga_horaria_eletiva` `b` where (`b`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) is null) then 0 else (select `b`.`QTD` from `carga_horaria_eletiva` `b` where (`b`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) end) AS `QTD_CHE`,(case when ((select `b`.`SEM_ELETIVA` from `carga_horaria_eletiva` `b` where (`b`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) is null) then 'TRUE' else (select `b`.`SEM_ELETIVA` from `carga_horaria_eletiva` `b` where (`b`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) end) AS `SEM_ELETIVA_CHE`,(select `a`.`QTD` from `carga_horaria_optativa` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `QTD_CHO`,(select `a`.`SEM_OPTATIVA` from `carga_horaria_optativa` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `SEM_OPTATIVA_CHO`,(case when ((select `b`.`DC_ALUNO_FK` from `consta_reprovacao` `b` where (`b`.`DC_ALUNO_FK` = `aluno`.`Aluno_Id`)) is null) then 'FALSE' else 'TRUE' end) AS `SOMENTE_REP`,(case when ((select `b`.`ALUNO_ID` from `limite_reprovacoes_atingido` `b` where (`b`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) is null) then 'FALSE' else 'TRUE' end) AS `LIMITE_REP`,(select `a`.`QTD` from `mais_que_50_por_cento` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `QTD_MQ5P`,(select `a`.`50_PER_CENT_CURSADO` from `mais_que_50_por_cento` `a` where (`a`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) AS `MAIS_MQ5P`,(case when ((select `b`.`ALUNO_ID` from `trancamentos_gerais_atingido` `b` where (`b`.`ALUNO_ID` = `aluno`.`Aluno_Id`)) is null) then 'FALSE' else 'TRUE' end) AS `TRT_GERAL`,(select sum(`a`.`CR`) from `cr_periodizado` `a` where (`a`.`CR_P_ALUNO_FK` = `aluno`.`Aluno_Id`) group by `a`.`CR_P_ALUNO_FK`) AS `SOMA_PERIODIZADO`,(select (sum(`a`.`CR`) / count(`a`.`CR_P_ALUNO_FK`)) from `cr_periodizado` `a` where (`a`.`CR_P_ALUNO_FK` = `aluno`.`Aluno_Id`) group by `a`.`CR_P_ALUNO_FK`) AS `CRA` from `aluno`;

-- Copiando estrutura para view sagui.consta_reprovacao
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `consta_reprovacao`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `consta_reprovacao` AS select `disciplina_cursada`.`DC_ALUNO_FK` AS `DC_ALUNO_FK` from `disciplina_cursada` where ((`disciplina_cursada`.`SITUACAO` = 'Reprovado') and `disciplina_cursada`.`DC_ALUNO_FK` in (select `disciplina_cursada`.`DC_ALUNO_FK` from `disciplina_cursada` where (`disciplina_cursada`.`SITUACAO` = 'Aprovado') group by `disciplina_cursada`.`DC_ALUNO_FK`) is false) group by `disciplina_cursada`.`DC_ALUNO_FK`;

-- Copiando estrutura para view sagui.limite_reprovacoes_atingido
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `limite_reprovacoes_atingido`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `limite_reprovacoes_atingido` AS select `a`.`Aluno_Id` AS `ALUNO_ID`,`a`.`NOME_ALUNO` AS `NOME_ALUNO`,`d`.`QTD_REPROVACAO` AS `QTD_REPROVACAO` from (`aluno` `a` join `disciplina_cursada` `d`) where ((`a`.`Aluno_Id` = `d`.`DC_ALUNO_FK`) and (`d`.`QTD_REPROVACAO` > 3)) group by `a`.`NOME_ALUNO`;

-- Copiando estrutura para view sagui.mais_que_50_por_cento
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `mais_que_50_por_cento`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `mais_que_50_por_cento` AS select `alunos`.`ALUNO_ID` AS `ALUNO_ID`,`alunos`.`NOME_ALUNO` AS `NOME_ALUNO`,`alunos`.`CURSO` AS `CURSO`,`alunos`.`QTD` AS `QTD`,`alunos`.`ANO_ENTRADA` AS `ANO_ENTRADA`,`alunos`.`QTD_PERIODOS` AS `QTD_PERIODOS`,`alunos`.`50_PER_CENT_CURSADO` AS `50_PER_CENT_CURSADO` from (select `a`.`Aluno_Id` AS `ALUNO_ID`,`a`.`NOME_ALUNO` AS `NOME_ALUNO`,`a`.`Curso` AS `CURSO`,count(`d`.`CODIGO`) AS `QTD`,`a`.`Entrada_Ano_Periodo` AS `ANO_ENTRADA`,`GET_PERIODO`(`a`.`Aluno_Id`) AS `QTD_PERIODOS`,(case when (((select `disciplinas_periodo`.`PERIODO` from `disciplinas_periodo` where ((`disciplinas_periodo`.`CURSO` = `a`.`Curso`) and (`disciplinas_periodo`.`IS_50_PER_CENT` = 'TRUE'))) <= `GET_PERIODO`(`a`.`Aluno_Id`)) and ((select `disciplinas_periodo`.`SOMATORIO` from `disciplinas_periodo` where ((`disciplinas_periodo`.`CURSO` = `a`.`Curso`) and (`disciplinas_periodo`.`IS_50_PER_CENT` = 'TRUE'))) > count(`d`.`CODIGO`))) then 'Menos de 50%' else 'Mais de 50%' end) AS `50_PER_CENT_CURSADO` from (`aluno` `a` join `disciplina_cursada` `d`) where ((`a`.`Aluno_Id` = `d`.`DC_ALUNO_FK`) and (`d`.`SITUACAO` = 'Aprovado')) group by `a`.`Aluno_Id`,`a`.`NOME_ALUNO`) `alunos` where (`alunos`.`QTD_PERIODOS` >= (select `disciplinas_periodo`.`PERIODO` from `disciplinas_periodo` where ((`disciplinas_periodo`.`CURSO` = `alunos`.`CURSO`) and (`disciplinas_periodo`.`IS_50_PER_CENT` = 'TRUE'))));

-- Copiando estrutura para view sagui.trancamentos_gerais_atingido
-- Removendo tabela temporária e criando a estrutura VIEW final
DROP TABLE IF EXISTS `trancamentos_gerais_atingido`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `trancamentos_gerais_atingido` AS select `a`.`Aluno_Id` AS `ALUNO_ID`,`a`.`NOME_ALUNO` AS `NOME_ALUNO`,`d`.`CODIGO` AS `CODIGO`,`d`.`QTD_CURSADA` AS `QTD_CURSADA` from (`aluno` `a` join `disciplina_cursada` `d`) where ((`a`.`Aluno_Id` = `d`.`DC_ALUNO_FK`) and (`d`.`CODIGO` like 'TRT%') and (`d`.`QTD_CURSADA` > 3));

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
