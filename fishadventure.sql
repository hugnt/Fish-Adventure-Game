-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 15, 2023 lúc 02:51 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `fishadventure`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thint`
--

CREATE TABLE `thint` (
  `id` mediumint(9) NOT NULL,
  `LevelId` mediumint(9) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thint`
--

INSERT INTO `thint` (`id`, `LevelId`, `content`) VALUES
(1, 1, 'The first character of key is S'),
(2, 1, 'The key contains character K'),
(3, 1, 'The key relevants to a big fish'),
(4, 1, 'The key contains character H'),
(5, 1, 'More than 30000 teeth'),
(6, 1, 'Living in the ocean'),
(7, 1, '... Tank TV Program'),
(8, 2, 'A famous song'),
(9, 2, 'Vietnamese singer'),
(10, 2, 'Released in 2013'),
(11, 2, 'Yesterday'),
(12, 2, 'The singer was born in ThaiBinh'),
(13, 2, 'Blue hair in the MV'),
(14, 3, 'A sport club'),
(15, 3, 'Premier League'),
(16, 3, 'A champion'),
(17, 3, 'Red'),
(18, 3, 'C2 2022-2023'),
(19, 3, 'Gunners'),
(20, 4, 'A famous game'),
(21, 4, 'An old game'),
(22, 4, 'Japan'),
(23, 4, 'Playing in a maze'),
(24, 4, 'Circle characters'),
(25, 4, 'being chased'),
(26, 5, 'A fish species'),
(27, 5, 'Long mouth'),
(28, 5, 'Jump on the water'),
(29, 5, 'Intelligent'),
(30, 5, 'Friendly'),
(31, 5, 'Help people'),
(32, 6, 'A famous firm'),
(33, 6, 'Cartoon'),
(34, 6, 'In the sea'),
(35, 6, 'Losing someone'),
(36, 6, 'Dorry'),
(37, 6, 'Clownfish');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tlevel`
--

CREATE TABLE `tlevel` (
  `id` mediumint(9) NOT NULL,
  `KeyAnswer` varchar(50) NOT NULL,
  `map` varchar(50) DEFAULT NULL,
  `KeyAnswer2` varchar(50) DEFAULT NULL,
  `limitTyping` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tlevel`
--

INSERT INTO `tlevel` (`id`, `KeyAnswer`, `map`, `KeyAnswer2`, `limitTyping`) VALUES
(1, 'shark', 'map005.png', 'sharks', 6),
(2, 'emcuangayhomqua', 'map006.png', 'emcuangayhomqua', 5),
(3, 'arsenal', 'map007.png', 'arsenal', 3),
(4, 'pacman', 'map008.png', 'pacman', 4),
(5, 'dolphin', 'map009.png', 'dolphins', 2),
(6, 'findingnemo', 'map010.png', 'nemo', 3);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `thint`
--
ALTER TABLE `thint`
  ADD PRIMARY KEY (`id`),
  ADD KEY `LevelId` (`LevelId`);

--
-- Chỉ mục cho bảng `tlevel`
--
ALTER TABLE `tlevel`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `thint`
--
ALTER TABLE `thint`
  MODIFY `id` mediumint(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT cho bảng `tlevel`
--
ALTER TABLE `tlevel`
  MODIFY `id` mediumint(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `thint`
--
ALTER TABLE `thint`
  ADD CONSTRAINT `thint_ibfk_1` FOREIGN KEY (`LevelId`) REFERENCES `tlevel` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
