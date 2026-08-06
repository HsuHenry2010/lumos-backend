-- 使用 TRUNCATE 與 CASCADE 自動處理所有外鍵與關聯表，一次清空所有舊資料
TRUNCATE TABLE fortress_buy_prices, fortresses, items, teams RESTART IDENTITY CASCADE;

-- 重新建立小隊
INSERT INTO teams (id, name, score) VALUES (1, '第一小隊', 0);
INSERT INTO teams (id, name, score) VALUES (2, '第二小隊', 0);
INSERT INTO teams (id, name, score) VALUES (3, '第三小隊', 0);
INSERT INTO teams (id, name, score) VALUES (4, '第四小隊', 0);
INSERT INTO teams (id, name, score) VALUES (5, '第五小隊', 0);

-- 建立獨立堡壘 (A, B, C, D)
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('A', 100, NULL);
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('B', 100, NULL);
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('C', 100, NULL);
INSERT INTO fortresses (code, defense_power, team_id) VALUES ('D', 100, NULL);

-- 建立四種獨立物件
INSERT INTO items (name, team_id) VALUES ('金幣', NULL);
INSERT INTO items (name, team_id) VALUES ('閃光幣', NULL);
INSERT INTO items (name, team_id) VALUES ('寶石', NULL);
INSERT INTO items (name, team_id) VALUES ('能量點數', NULL);