create schema IF NOT EXISTS almundo_callcenter;

USE almundo_callcenter;

DROP TABLE IF EXISTS am_llamada;
CREATE TABLE am_llamada (
    id bigint auto_increment primary key,
    estado varchar(255) NOT NULL
);

DROP TABLE IF EXISTS am_empleado;
CREATE TABLE am_empleado (
    id bigint auto_increment primary key,
    nombre varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    estado varchar(255) NOT NULL,
    cargo varchar(255) NOT NULL
);

DROP TABLE IF EXISTS `am_empleado_llamada`;
CREATE TABLE `am_empleado_llamada` (
  `id` bigint auto_increment,
  `empleado_id` int(10) unsigned NOT NULL,
  `llamada_id` int(10) unsigned NOT NULL,
  `duracion` bigint unsigned NOT NULL,
  `fecha`date not null,
  PRIMARY KEY (`empleado_id`,`llamada_id`),
  FOREIGN KEY (`empleado_id`) references `am_empleado` (`id`),
  FOREIGN KEY (`llamada_id`) references `am_llamada` (`id`)
);