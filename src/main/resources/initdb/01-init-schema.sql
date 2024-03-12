CREATE DATABASE IF NOT EXISTS `batch_status`;
CREATE DATABASE IF NOT EXISTS `allocation_data`;

GRANT ALL ON `batch_status`.* TO 'user'@'%';
GRANT ALL ON `allocation_data`.* TO 'user'@'%';

FLUSH PRIVILEGES;

