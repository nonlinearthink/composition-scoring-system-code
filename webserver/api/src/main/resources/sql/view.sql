/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/1/20 1:50:35                            */
/*==============================================================*/

/*==============================================================*/
/* View: comment_view                                                 */
/*==============================================================*/
create view comment_view
as
select co.comment_id, co.comment_body, c.composition_id, c.username target_username, co.username, u.nickname, u.avatar_url, c.title, co.time
from composition c, comment co, user u
where co.composition_id = c.composition_id and u.username = co.username;

/*==============================================================*/
/* View: composition_count                                               */
/*==============================================================*/
create view composition_count
as
select tb1.composition_id, tb5.username, tb5.nickname, tb5.avatar_url, tb1.title, tb1.composition_body, tb1.description, tb1.release_time, tb1.visibility, tb1.valid, tb1.score, tb1.word_score, tb1.grammar_score, tb1.sentence_fluency_score, tb1.length_score, tb1.richness_score, tb1.favorite_count, tb2.support_count, tb3.comment_count, tb4.history_count from(
select c.composition_id, c.title, c.composition_body, c.description, c.release_time, c.visibility, c.valid, count(f.favorite_id) favorite_count, c.score, c.word_score, c.grammar_score, c.sentence_fluency_score, c.length_score, c.richness_score
from composition c 
left join favorite f 
on f.composition_id=c.composition_id
group by c.composition_id) tb1,(
select c.composition_id, count(s.support_id) support_count
from composition c 
left join support s 
on s.composition_id=c.composition_id
group by c.composition_id) tb2,(
select c.composition_id, count(co.comment_id) comment_count
from composition c 
left join comment co
on co.composition_id=c.composition_id
group by c.composition_id) tb3,(
select c.composition_id, count(h.history_id) history_count
from composition c 
left join history h
on h.composition_id=c.composition_id
group by c.composition_id) tb4,(
select c.composition_id, u.username, u.nickname, u.avatar_url
from composition c 
left join user u
on u.username=c.username
group by c.composition_id) tb5
where tb1.composition_id = tb2.composition_id and tb2.composition_id = tb3.composition_id and tb3.composition_id = tb4.composition_id and tb4.composition_id = tb5.composition_id;

/*==============================================================*/
/* View: support_view                                              */
/*==============================================================*/
create view support_view
as
select s.support_id, c.composition_id, c.username target_username, s.username, u.nickname, u.avatar_url, c.title, s.time
from composition c, support s, user u
where s.composition_id = c.composition_id and u.username = s.username;

/*==============================================================*/
/* View: user_view                                              */
/*==============================================================*/
create view user_view
as
select tb1.username, tb1.nickname, tb1.avatar_url, tb1.signature, tb1.follow_count, tb2.composition_count from(
select u.username, u.nickname, u.avatar_url, u.signature, count(*)-1 follow_count
from user u
left join follow f
on u.username = f.target_username
group by u.username) tb1,(
select u.username, count(*)-1 composition_count
from user u
left join composition c
on u.username = c.username
group by u.username) tb2
where tb1.username = tb2.username;

/*==============================================================*/
/* View: report_count                                            */
/*==============================================================*/
create view report_count
as
select a.username, a.composition_report_count, b.comment_report_count, a.composition_report_count+b.comment_report_count count, b.frozen from
(select c.username, count(*) composition_report_count
from user u
left join composition_report c
on u.username=c.username
where c.status=1
group by c.username)a, 
(select co.username, count(*) comment_report_count, frozen
from user u
left join comment_report co
on u.username=co.username
where co.status=1
group by co.username)b
where a.username=b.username
order by count desc