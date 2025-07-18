-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 18, 2025 at 07:54 AM
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
  `status` varchar(254) NOT NULL DEFAULT 'Inactive'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`facultyID`, `fullname`, `gender`, `age`, `email`, `contact`, `datehired`, `username`, `password`, `status`) VALUES
(1, 'Neil A Mutia', 'male', 27, 'mutia@gmail.com', '09123456789', '2025-05-05 07:35:36', 'neil1234', 'nam12345', 'Inactive'),
(2, 'crab king', 'male', 23, 'king@gmail.com', '09873463442', '2025-05-22 06:28:25', 'jhon', '123', 'Inactive'),
(3, 'maramag', 'male', 23, 'mara@gmail.com', '0946627311', '2025-05-04 16:00:00', 'manji', '12345678', 'Inactive'),
(23412, 'rogene abijay', 'male', 31, 'rogene@gmail.com', '091234567893', '2025-05-05 16:00:00', 'rogene', '12345678', 'Inactive'),
(23413, 'lalah', 'mali', 23, 'lalah@gmail.com', '0932874', '2025-07-18 05:24:36', 'lalah', 'lalah', 'Active');

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

--
-- Dumping data for table `performance`
--

INSERT INTO `performance` (`id`, `student_id`, `score`, `task_id`, `file`, `file_path`, `message`, `date_sub`) VALUES
(15, '1', NULL, 8, 0x696d706f7274206a6176612e7574696c2e2a3b0d0a7075626c696320636c6173732064656d6f317b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d2061726773297b0d0a2020202020202020696e742061725b5d203d207b20312c20322c20332c20342c2035207d3b0d0a20202020202020207472797b0d0a202020202020202020202020666f7228696e7420693d303b20693c3d61722e6c656e6774683b20692b2b297b0d0a2020202020202020202020202020202053797374656d2e6f75742e7072696e746c6e2861725b695d293b0d0a2020202020202020202020207d0d0a20202020202020207d636174636828457863657074696f6e2065297b0d0a20202020202020202020202053797374656d2e6f75742e7072696e746c6e28225c6e457863657074696f6e204361746368656422293b0d0a20202020202020207d0d0a202020207d0d0a7d, 'demo4.java', '', '2025-07-15 00:15:20.626960'),
(16, '1', NULL, 8, 0x696d706f7274206a6176612e7574696c2e2a3b0d0a7075626c696320636c6173732064656d6f317b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d2061726773297b0d0a2020202020202020696e742061725b5d203d207b20312c20322c20332c20342c2035207d3b0d0a20202020202020207472797b0d0a202020202020202020202020666f7228696e7420693d303b20693c3d61722e6c656e6774683b20692b2b297b0d0a2020202020202020202020202020202053797374656d2e6f75742e7072696e746c6e2861725b695d293b0d0a2020202020202020202020207d0d0a20202020202020207d636174636828457863657074696f6e2065297b0d0a20202020202020202020202053797374656d2e6f75742e7072696e746c6e28225c6e457863657074696f6e204361746368656422293b0d0a20202020202020207d0d0a202020207d0d0a7d, 'demo4.java', '', '2025-07-15 00:15:20.626960'),
(17, '1', NULL, 8, 0x696d706f7274206a6176612e7574696c2e2a3b0d0a7075626c696320636c6173732064656d6f327b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d2061726773297b0d0a2020202020202020696e742061725b5d203d207b20312c20322c20332c20342c2035207d3b0d0a2020202020202020666f7228696e7420693d303b20693c61722e6c656e6774683b20692b2b297b0d0a20202020202020202020202053797374656d2e6f75742e7072696e746c6e2861725b695d293b0d0a20202020202020207d0d0a202020207d0d0a7d, 'demo2.java', '', '2025-07-15 00:15:20.626960'),
(18, '1', 99.00, 10, 0x696d706f7274206a6176612e7574696c2e2a3b0d0a7075626c696320636c6173732064656d6f337b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d2061726773297b0d0a2020202020202020696e742061725b5d203d207b20312c20322c20332c20342c2035207d3b0d0a2020202020202020666f7228696e7420693a206172297b0d0a20202020202020202020202053797374656d2e6f75742e7072696e746c6e2869293b0d0a20202020202020207d0d0a202020207d0d0a7d, 'demo3.java', 'dff3rfv32', '2025-07-15 00:15:20.626960'),
(19, '27', NULL, 10, 0x7075626c696320636c6173732064656d6f367b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d61726773297b0d0a2020202020202020626f6f6c65616e20616d626f74203d2066616c73653b0d0a2020202020202020537472696e67206172205b5d203d207b22313032222c224d696e676d696e67222c20223130746f32222c202233222c202232227d3b0d0a2020202020202020666f7228537472696e6720693a206172297b0d0a2020202020202020202020207472797b0d0a2020202020202020202020202020202053797374656d2e6f75742e7072696e746c6e28496e74656765722e7061727365496e74286929293b0d0a2020202020202020202020207d63617463682028457863657074696f6e2065297b0d0a0d0a2020202020202020202020207d0d0a20202020202020207d0d0a20202020202020200d0a202020207d0d0a7d, 'demo6.java', 'sad sad sad', '2025-07-15 00:15:20.626960'),
(20, '27', 95.00, 11, 0x7075626c696320636c6173732064656d6f367b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d61726773297b0d0a2020202020202020626f6f6c65616e20616d626f74203d2066616c73653b0d0a2020202020202020537472696e67206172205b5d203d207b22313032222c224d696e676d696e67222c20223130746f32222c202233222c202232227d3b0d0a2020202020202020666f7228537472696e6720693a206172297b0d0a2020202020202020202020207472797b0d0a2020202020202020202020202020202053797374656d2e6f75742e7072696e746c6e28496e74656765722e7061727365496e74286929293b0d0a2020202020202020202020207d63617463682028457863657074696f6e2065297b0d0a0d0a2020202020202020202020207d0d0a20202020202020207d0d0a20202020202020200d0a202020207d0d0a7d, 'demo6.java', 'Hey yow, Stress nami', '2025-07-15 00:15:20.626960'),
(21, '27', NULL, 11, 0x696d706f7274206a6176612e7574696c2e2a3b0d0a7075626c696320636c6173732064656d6f327b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d2061726773297b0d0a2020202020202020696e742061725b5d203d207b20312c20322c20332c20342c2035207d3b0d0a2020202020202020666f7228696e7420693d303b20693c61722e6c656e6774683b20692b2b297b0d0a20202020202020202020202053797374656d2e6f75742e7072696e746c6e2861725b695d293b0d0a20202020202020207d0d0a202020207d0d0a7d, 'demo2.java', 'Hey yow, Stress nami', '2025-07-15 00:15:20.626960'),
(22, '27', NULL, 10, 0x696d706f7274206a6176612e7574696c2e2a3b0d0a7075626c696320636c6173732064656d6f317b0d0a202020207075626c69632073746174696320766f6964206d61696e28537472696e675b5d2061726773297b0d0a2020202020202020696e742061725b5d203d207b2031302c20382c2031332c2033342c2035207d3b200d0a20202020202020200d0a202020207d0d0a7d, 'demo1.java', 'asdasd', '2025-07-15 00:15:20.626960');

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
  `contact` int(11) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `birthday` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentID`, `lastname`, `firstname`, `gender`, `age`, `address`, `email`, `contact`, `username`, `password`, `birthday`) VALUES
(1, 'canedo', 'jonathan', 'male', 23, '0912345678', 'jonathan01@gmail.com', 912345678, 'nathan', '090807', '2025-07-13'),
(23, 'bahinting', 'rejeans', 'female', 24, '0977443242', 'rejean@gmail.com', 977443242, 'rejean', '12345678', '2025-07-13'),
(26, 'plenos', 'via', 'female', 23, 'prk 5', 'via@gmail.com', 912345678, 'via', '12345678', '2025-07-13'),
(27, 'lah', 'lah', 'mali', 32, 'lasdk', 'lah@gmail.com', 999999999, 'lah', 'lah', '2025-07-13'),
(28, 'ASd', 'Sda', 'Male', 3, 'asd', 'asd', 951515165, 'lah', 'lahlah', '2021-08-03');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `id` int(11) NOT NULL,
  `code` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `sem` int(11) DEFAULT 1,
  `year` varchar(256) DEFAULT '2024-2025'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`id`, `code`, `description`, `instructor_id`, `sem`, `year`) VALUES
(437, 'ITP 135', 'Application Development', 1, 1, '2024-2025'),
(438, 'ITBAN3', 'Applicatoin', 1, 1, '2024-2025'),
(439, 'ITP 135', 'App', 23413, 1, '2024-2025'),
(440, 'ITP 3', 'Application Dev', 2, 1, '2024-2025'),
(441, 'ITBAN3', 'CApstone', 2, 1, '2024-2025'),
(442, 'Programming 2', 'Nothing', 23413, 1, '2024-2025');

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
  `date_sub` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`task_id`, `task`, `status`, `subject_id`, `duration`, `instructor_id`, `task_code`, `description`, `sem`, `school_year`, `date_sub`) VALUES
(8, 'Activity 2', 'Pending', 437, '1', 1, '123123', 'MNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDfMNSADuiohefoaASDf', NULL, NULL, '2025-07-15 00:31:31.347405'),
(9, 'Activity 10', 'Pending', 438, '1', 1, 'act10-123', 'nfhvlutb;wije', NULL, NULL, '2025-07-15 00:31:31.347405'),
(10, 'Activity 11', 'Pending', 440, '1', 2, 'act11-123', 'asdfbvolaiwm', NULL, NULL, '2025-07-15 00:31:31.347405'),
(11, 'act12', 'Pending', 441, '1:30', 2, 'CAPS123', 'fdavregbaae', NULL, NULL, '2025-07-15 00:31:31.347405'),
(13, 'sad', 'Pending', 439, '1:30', 23413, 'sad', 'asd', NULL, NULL, '2025-07-15 00:31:31.347405'),
(14, 'asd', 'Pending', 439, '2', 23413, 'sadsadsad', 'asd', 1, '2025-2026', '2025-07-15 00:31:31.347405');

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
  MODIFY `facultyID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23414;

--
-- AUTO_INCREMENT for table `performance`
--
ALTER TABLE `performance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `studentID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=443;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
