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
CREATE DATABASE IF NOT EXISTS `sagui` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `sagui`;

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