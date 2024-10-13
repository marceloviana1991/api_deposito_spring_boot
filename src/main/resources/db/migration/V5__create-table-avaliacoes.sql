create table avaliacoes(

    id bigint not null auto_increment,
    nota double not null,
    feedback varchar(250) not null,
    data_emissao_avaliacao datetime not null,
    id_usuario bigint not null,
    foreign key (id_usuario) references usuarios(id),
    id_produto bigint not null,
    foreign key (id_produto) references produtos(id),

    primary key(id)
);