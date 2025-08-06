-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 06, 2025 at 08:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `comlab`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `fullname`, `username`, `email`, `password`, `user_type`, `gender`, `contact`, `age`) VALUES
(1, '[value-2]', 'admin', '[value-4]', 'admin', '[value-6]', '[value-7]', '[value-8]', 0);

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL,
  `student_name` varchar(255) NOT NULL,
  `attendance` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`attendance_id`, `student_name`, `attendance`) VALUES
(1, 'Student 1', 'Present'),
(2, 'Student 2', 'Absent'),
(3, 'Student 3', 'Present'),
(4, 'Student 4', 'Absent');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course` varchar(100) NOT NULL,
  `acronym` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course`, `acronym`) VALUES
('Bachelor of Science in Information Technology', 'BSIT'),
('jhgaeverve', 'HSDCD'),
('jkwythvy', 'JHS'),
('jasgfevrev', 'HJDH'),
('jhvfjrggirbgi', 'HFDC'),
('sfvdsv', 'SDA'),
('bachelor of science in accountancy', 'BSA');

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `facultyID` int(11) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `datehired` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `status` varchar(254) NOT NULL DEFAULT 'Inactive',
  `birthday` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`facultyID`, `fullname`, `gender`, `age`, `email`, `contact`, `datehired`, `username`, `password`, `status`, `birthday`) VALUES
(1, 'Neil A Mutia', 'male', 27, 'mutia@gmail.com', '09123456789', '2025-05-05 07:35:36', 'neil1234', 'nam12345', 'Inactive', '2025-07-22'),
(2, 'crab king', 'male', 23, 'king@gmail.com', '09873463442', '2025-05-22 06:28:25', 'jhon', '123', 'Inactive', '2025-07-22'),
(3, 'maramag', 'male', 23, 'mara@gmail.com', '0946627311', '2025-05-04 16:00:00', 'manji', '12345678', 'Inactive', '2025-07-22'),
(23412, 'rogene abijay', 'male', 31, 'rogene@gmail.com', '091234567893', '2025-05-05 16:00:00', 'rogene', '12345678', 'Inactive', '2025-07-22'),
(23413, 'lalah', 'mali', 23, 'lalah@gmail.com', '0932874', '2025-07-18 05:24:36', 'lalah', 'lalah', 'Active', '2025-07-22');

-- --------------------------------------------------------

--
-- Table structure for table `performance`
--

CREATE TABLE `performance` (
  `id` int(11) NOT NULL,
  `student_id` varchar(255) NOT NULL,
  `score` decimal(5,2) DEFAULT NULL,
  `task_id` int(11) NOT NULL,
  `file` blob DEFAULT NULL,
  `file_path` varchar(254) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `date_sub` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentID` int(20) NOT NULL,
  `lastname` varchar(200) NOT NULL,
  `firstname` varchar(200) NOT NULL,
  `gender` varchar(200) NOT NULL,
  `age` int(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `contact` varchar(254) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `birthday` date NOT NULL DEFAULT current_timestamp(),
  `middlename` varchar(254) DEFAULT NULL,
  `sec` varchar(254) DEFAULT NULL,
  `year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentID`, `lastname`, `firstname`, `gender`, `age`, `address`, `email`, `contact`, `username`, `password`, `birthday`, `middlename`, `sec`, `year`) VALUES
(1, 'canedo', 'jonathan', 'male', 23, '0912345678', 'jonathan01@gmail.com', '912345678', 'nathan', '090807', '2025-07-13', NULL, 'B', 2),
(23, 'bahinting', 'rejeans', 'female', 24, '0977443242', 'rejean@gmail.com', '977443242', 'rejean', '12345678', '2025-07-13', NULL, 'B', 2),
(26, 'plenos', 'via', 'female', 23, 'prk 5', 'via@gmail.com', '912345678', 'via', '12345678', '2025-07-13', NULL, 'B', 2),
(27, 'lah', 'lah', 'mali', 32, 'lasdk', 'lah@gmail.com', '999999999', 'lah', 'lah', '2025-07-13', NULL, 'A', 1),
(28, 'ASd', 'Sda', 'Male', 3, 'asd', 'asd', '951515165', 'lah', 'lahlah', '2021-08-03', NULL, 'B', 2),
(29, 'sadasd', 'sadsad', 'Male', 0, 'asdasdasd', 'sad@gmail.com', '09510476375', 'sadsad', 'Sadsad', '2025-07-15', NULL, 'B', 2),
(30, 'sfgs', 'fsgfs', 'Male', 9, '234dfa', 'adf@gmail.com', '09555555555', 'sadsadwqe', 'Sadsad', '2015-08-09', 'ssad', 'B', 2);

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `id` int(11) NOT NULL,
  `code` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `sem` varchar(254) DEFAULT '1st',
  `year` varchar(256) DEFAULT '2024-2025'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`id`, `code`, `description`, `instructor_id`, `sem`, `year`) VALUES
(437, 'ITP 135', 'Application Development', 1, '1st', '2024-2025'),
(438, 'ITBAN3', 'Applicatoin', 1, '1st', '2024-2025'),
(439, 'ITP 135', 'App', 23413, '1st', '2024-2025'),
(440, 'ITP 3', 'Application Dev', 2, '1st', '2024-2025'),
(441, 'ITBAN3', 'CApstone', 2, '1st', '2024-2025'),
(442, 'Programming 2', 'Nothing', 23413, '1st', '2024-2025'),
(443, 'Prog2', 'Programming 2', 23413, 'Mid Year', '2024-2025');

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `task_id` int(11) NOT NULL,
  `task` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'pending',
  `subject_id` int(11) NOT NULL,
  `duration` varchar(254) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `task_code` varchar(254) NOT NULL,
  `description` text NOT NULL,
  `sem` int(11) DEFAULT NULL,
  `school_year` varchar(254) DEFAULT NULL,
  `date_sub` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  `sec` varchar(254) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `points` int(11) NOT NULL DEFAULT 100
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`attendance_id`);

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`facultyID`);

--
-- Indexes for table `performance`
--
ALTER TABLE `performance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`task_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `attendance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `faculty`
--
ALTER TABLE `faculty`
  MODIFY `facultyID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23415;

--
-- AUTO_INCREMENT for table `performance`
--
ALTER TABLE `performance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `studentID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=444;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
