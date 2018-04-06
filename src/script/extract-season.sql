-- Extrahiert die ersten 11 Meisterschaften aus der Produktionsdatenbank.
-- Die Extraktion dient als Testdatenbasis.
DELETE FROM
    bo_gametipp
WHERE
    id IN
    (
        SELECT
            id
        FROM
            bo_gametipp gt
            JOIN bo_game g ON (g.id = gt.bo_game_ref)
            JOIN bo_gamelist gl ON (gl.id = g.bo_gamelist_ref)
            JOIN bo_season se ON (se.id = gl.bo_season_ref) 
        WHERE
           se.id > 12
    );

DELETE FROM
    bo_goal
WHERE
    id IN
    (
        SELECT
            id
        FROM
            bo_game g
            JOIN bo_gamelist gl ON (gl.id = g.bo_gamelist_ref)
            JOIN bo_season se ON (se.id = gl.bo_season_ref) 
        WHERE
            se.id > 12
    )
;

DELETE FROM
    bo_game
WHERE
    id IN
    (
        SELECT
            id
        FROM
            bo_game g
            JOIN bo_gamelist gl ON (gl.id = g.bo_gamelist_ref)
            JOIN bo_season se ON (se.id = gl.bo_season_ref) 
        WHERE
            se.id > 12
    );

DELETE FROM
    bo_user_season
WHERE
    bo_season_ref > 12
;

DELETE FROM
    bo_team_group
WHERE
    bo_group_ref IN
    (
        SELECT id FROM bo_group WHERE bo_season_ref > 12
    )
;

DELETE FROM
    bo_group
WHERE
    bo_season_ref > 12
;

DELETE FROM
    bo_gamelist
WHERE
    id IN
    (
        SELECT
            id
        FROM
            bo_gamelist gl
            JOIN bo_season se ON (se.id = gl.bo_season_ref)
        WHERE
            se.id > 12
    )
;

DELETE FROM
    bo_season
WHERE
    id > 12
;

ROLLBACK;