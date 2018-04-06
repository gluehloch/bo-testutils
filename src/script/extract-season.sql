-- Extrahiert die ersten 11 Meisterschaften aus der Produktionsdatenbank.
-- Die Extraktion dient als Testdatenbasis.
SET autocommit=0;

DELETE FROM
    bo_gametipp
WHERE
    bo_game_ref IN
    (
        SELECT
            g.id
        FROM
            bo_game g
            JOIN bo_gamelist gl ON (gl.id = g.bo_gamelist_ref)
            JOIN bo_season se ON (se.id = gl.bo_season_ref) 
        WHERE
           se.id NOT IN (1, 2, 3, 9, 10, 11, 12)
    );

DELETE FROM
    bo_goal
WHERE
    bo_game_ref IN
    (
        SELECT
            g.id
        FROM
            bo_game g
            JOIN bo_gamelist gl ON (gl.id = g.bo_gamelist_ref)
            JOIN bo_season se ON (se.id = gl.bo_season_ref) 
        WHERE
            se.id NOT IN (1, 2, 3, 9, 10, 11, 12)
    );

DELETE FROM
    bo_game
WHERE
    bo_gamelist_ref IN
    (
        SELECT
            gl.id
        FROM
            bo_gamelist gl
            JOIN bo_season se ON (se.id = gl.bo_season_ref) 
        WHERE
            se.id NOT IN (1, 2, 3, 9, 10, 11, 12)
    );

DELETE FROM
    bo_gamelist
WHERE
    bo_season_ref IN
    (
        SELECT
            se.id
        FROM
            bo_season se
        WHERE
            se.id NOT IN (1, 2, 3, 9, 10, 11, 12)
    );

DELETE FROM
    bo_user_season
WHERE
    bo_season_ref NOT IN (1, 2, 3, 9, 10, 11, 12)
;

DELETE FROM
    bo_team_group
WHERE
    bo_group_ref IN
    (
        SELECT id FROM bo_group WHERE bo_season_ref NOT IN (1, 2, 3, 9, 10, 11, 12)
    );

DELETE FROM
    bo_group
WHERE
    bo_season_ref NOT IN (1, 2, 3, 9, 10, 11, 12)
;

DELETE FROM
    bo_season
WHERE
    id NOT IN (1, 2, 3, 9, 10, 11, 12)
;
