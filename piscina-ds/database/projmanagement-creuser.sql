-- with the root
CREATE USER ‘projmanager’@‘%’ IDENTIFIED BY ‘123456’;
CREATE schema proj_management;
GRANT ALL ON proj_management.* to 'projmanager'@'%';
-- from now on, root user should be used only for administration  
