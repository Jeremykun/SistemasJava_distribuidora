-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-03-2018 a las 05:36:02
-- Versión del servidor: 10.1.25-MariaDB
-- Versión de PHP: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `distribuidora`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `id_producto` int(11) NOT NULL,
  `codigo_pro` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_unidad` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `precio` double(10,0) NOT NULL,
  `stock` int(11) NOT NULL,
  `observacion` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`id_producto`, `codigo_pro`, `descripcion`, `tipo_unidad`, `precio`, `stock`, `observacion`) VALUES
(1, 'PROD_0001', 'VOLT DE 220 ml PAQUETE DE  12 und', 'PAQUETE', 23, 30, 'PROMOCION 2 und por cada paquete'),
(2, 'PROD_0002', 'DETERGENTE ARIEL DE 500 GR', 'BOLSA', 71, 50, 'el ariel se  vende sin promociones'),
(3, 'PROD_0003', 'GASEOSA CONCORDIA DE 3LT  PAQUETE DE 6', 'PAQUETE', 18, 30, 'TRATAR DE  VENDER LOS MIXTOS'),
(4, 'PROD_0004', 'GASEOSA SPRITE DE 1 1/2 PAQUETE DE 6', 'PAQUETE', 22, 10, 'NULL');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`id_producto`),
  ADD UNIQUE KEY `codigo_pro` (`codigo_pro`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
