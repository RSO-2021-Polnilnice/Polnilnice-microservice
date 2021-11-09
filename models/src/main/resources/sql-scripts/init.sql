INSERT INTO polnilnice (ime, lokacijalat, lokacijalng) VALUES ('Init SQL polnilnica 1', 46.050838, 14.458901);
INSERT INTO polnilnice (ime, lokacijalat, lokacijalng) VALUES ('Init SQL polnilnica 2', 46.048455, 14.497404);
INSERT INTO polnilnice (ime, lokacijalat, lokacijalng) VALUES ('Init SQL polnilnica 3', 46.073305, 14.582251);

INSERT INTO ocene (polnilnica_id, user_id, ocena, besedilo) VALUES (1, 1, 4, 'Neki je s kablom narobe.');
INSERT INTO ocene (polnilnica_id, user_id, ocena, besedilo) VALUES (1, 2, 5, 'Super :)');
INSERT INTO ocene (polnilnica_id, user_id, ocena, besedilo) VALUES (2, 1, 1, 'Polnilnica je bila že zasedena. Katastrofa.');

INSERT INTO termini (polnilnica_id, user_id, datefrom, dateto) VALUES (1, 1, 1636376400, 1636380000);
INSERT INTO termini (polnilnica_id, user_id, datefrom, dateto) VALUES (1, 2, 1636380000, 1636385400);
INSERT INTO termini (polnilnica_id, user_id, datefrom, dateto) VALUES (2, 1, 1636540200, 1636547400);
