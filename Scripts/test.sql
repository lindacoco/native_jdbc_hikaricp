select user(), database ();

select * from department d ;
select  deptno, deptname, floor from department d ;

desc employee;
select  e.empno, e.empname, e.title, m.empname as manager_name, e.manager as manager_no, e.salary, e.dno ,deptname
from employee e left join employee m on e.manager = m.empno left join department d on e.dno = d.deptno
where e.dno =1;


select * from department d ;
insert into department values(5,'마케팅',0);
update  department set deptname = '마케팅2', floor = 10 where deptno =5;
delete from department where deptno =5;