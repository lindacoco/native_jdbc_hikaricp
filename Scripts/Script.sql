select user(), database ();

select  * from employee e ; -- pic는 다 null

select  * from department d ;

select  * from department d where deptno =3;
select deptno, deptname, floor from department d where deptno =3;

select empno,empname,title,manager,salary,dno,pic from employee e ;
update employee set empname="배수지", title="사원",manager="4377",salary="2000000",dno=3 where empno=1999;
delete from employee where empno = 1999;

insert into department values(5,'마케팅',8);
update department set deptname ='마케팅3',floor =7 where deptno =0;
delete from department  where  deptno = 5;
delete from employee where empno =1004;

