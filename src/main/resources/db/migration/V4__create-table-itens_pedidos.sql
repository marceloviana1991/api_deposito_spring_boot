create table itens_pedidos(

    id bigint not null auto_increment,
    quantidade int not null,
    data_emissao_pedido datetime not null,
    local_entrega_cep varchar(8) not null,
    local_entrega_cidade varchar(100) not null,
    local_entrega_bairro varchar(100) not null,
    local_entrega_rua varchar(100) not null,
    local_entrega_numero varchar(100) not null,
    id_usuario bigint not null,
    foreign key (id_usuario) references usuarios(id),
    id_produto bigint not null,
    foreign key (id_produto) references produtos(id),

    primary key(id)
);