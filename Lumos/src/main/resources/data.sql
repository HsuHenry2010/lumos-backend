-- 依照相依順序先清空舊資料，避免外鍵衝突
DELETE FROM items;
DELETE FROM fortresses;
DELETE FROM teams;

-- 重新建立小隊
INSERT INTO teams (id, name, score) VALUES (1, '第一小隊', 1500);
INSERT INTO teams (id, name, score) VALUES (2, '第二小隊', 850);
INSERT INTO teams (id, name, score) VALUES (3, '第三小隊', 980);
INSERT INTO teams (id, name, score) VALUES (4, '第四小隊', 790);
INSERT INTO teams (id, name, score) VALUES (5, '第五小隊', 1120);

-- 建立獨立堡壘 (A, B, C, D)
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('A', 100, 1);
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('B', 100, 2);
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('C', 100, 3);
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('D', 100, NULL);

-- 建立四種獨立物件
INSERT INTO items (name, team_id) VALUES ('金幣', 1);
INSERT INTO items (name, team_id) VALUES ('閃光幣', NULL);
INSERT INTO items (name, team_id) VALUES ('寶石', NULL);
INSERT INTO items (name, team_id) VALUES ('能量點數', NULL);