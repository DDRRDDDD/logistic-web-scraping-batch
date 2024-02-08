CREATE DATABASE IF NOT EXISTS `batch-status`;
CREATE DATABASE IF NOT EXISTS `allocation-data`;

GRANT ALL ON `batch-status`.* TO 'user'@'%';
GRANT ALL ON `allocation-data`.* TO 'user'@'%';

FLUSH PRIVILEGES;

