
INSERT INTO BOOKMAKER (id, name, web_page_url, label, minimal_amount_to_bet) VALUES (seq_int.nextval, '25', 'http://affiliates.pinnaclesports.com', 'Pinnacle', 1.00);
INSERT INTO BOOKMAKER (id, name, web_page_url, label, minimal_amount_to_bet) VALUES (seq_int.nextval, 'Average', 'Average', 'Average', 1.00);
INSERT INTO BOOKMAKER (id, name, web_page_url, label, minimal_amount_to_bet) VALUES (seq_int.nextval, '102', 'http://affiliates.bet-at-home.com', 'Bet At Home', 1.00);

insert into BOOKMAKER_NAME (ID,NAME,SYSTEM,BOOKMAKER_ID) values(seq_int.nextval,'25','www.betexplorer.com',(select id from bookmaker where label = 'Pinnacle'));
insert into BOOKMAKER_NAME (ID,NAME,SYSTEM,BOOKMAKER_ID) values(seq_int.nextval,'Average','www.betexplorer.com',(select id from bookmaker where label = 'Average'));
insert into BOOKMAKER_NAME (ID,NAME,SYSTEM,BOOKMAKER_ID) values(seq_int.nextval,'102','www.betexplorer.com',(select id from bookmaker where label = 'Bet At Home'));