do
$do$
declare
    survivor_id INTEGER;
   duplicate_id INTEGER;
   dup_name varchar;
BEGIN
  survivor_id := ;
  duplicate_id := ;
  select max(name) into dup_name from team where id = duplicate_id;

update team_name set team_id = survivor_id where team_id = duplicate_id;
insert into team_name (id,proposed_team_name, team_id) values (nextval('seq_int'), dup_name, survivor_id);
update match set home_team_id = survivor_id where home_team_id = duplicate_id;
update match set away_team_id = survivor_id where away_team_id = duplicate_id;
delete from team where id = duplicate_id;
  DELETE FROM team_name d
  WHERE EXISTS (
      SELECT * FROM team_name x
      WHERE x.proposed_team_name = d.proposed_team_name and d.team_id = x.team_id
            AND x.id < d.id
  );
 END
$do$;