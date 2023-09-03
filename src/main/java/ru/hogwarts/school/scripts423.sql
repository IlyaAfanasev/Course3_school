select student."name", student."age", faculty."name" from student
left join faculty on faculty.id= student.faculty_id

select student."name" from avatar
join student on student."id" = avatar.student_id