CREATE TABLE `empresa` (
`id` bigint(20) NOT NULL,
`cnpj` varchar(255) NOT NULL,
`data_atualizaçao` datetime NOT NULL,
`data_criacao` datetime NOT NULL,
`razao_social` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `funcionario` (
`id` bigint(20) NOT NULL,
`cpf` varchar(255) NOT NULL,
`data_atualizacao` datetime NOT NULL,
`data_criacao` datetime NOT NULL,
`email` varchar(255) NOT NULL,
`perfil` varchar(255) NOT NULL,
`qtd_horas_almoco` float DEFAULT NULL,
`qtd_horas_trabalho_dia` float DEFAULT NULL,
`senha` varchar(255) NOT NULL,
`valor_hora` decimal(19,2) DEFAULT NULL,
`empresa_id` bigint(20) DEFAULT NULL   
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lancamento` (
`id` bigint(20) NOT NULL,
`data` datetime  NOT NULL,
`data_atualizacao` datetime NOT NULL,
`data_criacao` datetime NOT NULL,
`descricao` varchar(255) DEFAULT NULL,
`localizacao` varchar(255) DEFAULT NULL,
`tipo` varchar(255) NOT NULL,
`funcionario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `empresa`
ADD PRIMARY KEY (`id`);

ALTER TABLE `funcionario`
 ADD PRIMARY KEY (ID),
ADD KEY FK4cm1kg523jlopyexjbmi6y54j (EMPRESA_ID);

ALTER TABLE `lancamento`
	ADD PRIMARY KEY (ID),
ADD KEY FK46i4K7VL8wah7feutye9kbpi4 (FUNCIONARIO_ID);


ALTER TABLE `empresa`
MODIFY ID BIGINT(20) NOT NULL AUTO_INCREMENT; 


ALTER TABLE  `lancamento`
MODIFY ID BIGINT(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE FUNCIONARIO
ADD CONSTRAINT FK4cm1kg523jlopyexjbmi6y54j FOREIGN KEY (EMPRESA_ID) REFERENCES EMPRESA (ID);

ALTER TABLE LANCAMENTO 
ADD CONSTRAINT FK46i4K5VL8WAH7FEUTYE9KBPI4 FOREIGN KEY (FUNCIONARIO_ID) REFERENCES FUNCIONARIO (ID);
  