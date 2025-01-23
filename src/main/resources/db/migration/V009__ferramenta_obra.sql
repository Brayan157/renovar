CREATE TABLE tb_ferramenta_obra(
    ferramenta_id INT NOT NULL,
    obra_id INT NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    data_entrega DATE NOT NULL,
    data_saida DATE NOT NULL,
    funcionario_id INT NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    foreign key (ferramenta_id) references tb_ferramenta(id),
    foreign key (obra_id) references tb_obra(id),
    foreign key (funcionario_id) references tb_funcionario(id)
);