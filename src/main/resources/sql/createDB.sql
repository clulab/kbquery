-- Setup the MySQL database for the KBQuery project:
--   Written by: Tom Hicks. 4/2/2017.
--   Last Modified: Initial creation.

-- Create the database itself
--
CREATE DATABASE `kbqdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

-- Create the application user
--
create user `kbqer`;
set password for `kbqer`@'%' = password('kbqpwd');


-- Grants privileges to application user
--
GRANT USAGE ON *.* TO 'kbqer'@'%';
GRANT ALL PRIVILEGES ON `kbqdb`.* TO 'kbqer'@'%';
