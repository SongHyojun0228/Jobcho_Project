-- JOBCHO 생성 
CREATE USER jobcho identified BY jobcho;
GRANT connect, resource, dba TO jobcho;

-- PROGRAMMERS
CREATE USER PROGRAMMERS identified BY PROGRAMMERS;
GRANT connect, resource, dba TO PROGRAMMERS;