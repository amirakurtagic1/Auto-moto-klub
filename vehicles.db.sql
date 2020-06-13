BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "owner" (
	"id"	int,
	"name"	text,
	"surname"	text,
	"parent_name"	text,
	"date_of_birth"	date,
	"place_of_birth"	int,
	"living_address"	text,
	"living_place"	int,
	"jmbg"	text,
	CONSTRAINT "vlasnik_mjesto_id_fk" FOREIGN KEY("place_of_birth") REFERENCES "place"("id"),
	CONSTRAINT "vlasnik_mjesto_id_fk_2" FOREIGN KEY("living_place") REFERENCES "place"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "vehicle" (
	"id"	int,
	"manufacturer"	int,
	"model"	text,
	"chasis_number"	text,
	"plate_number"	text,
	"owner"	int,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "manufacturer" (
	"id"	int,
	"name"	text,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "place" (
	"id"	int,
	"name"	text,
	"postal_number"	text,
	PRIMARY KEY("id")
);
INSERT INTO "owner" VALUES (1,'Meho','Mehic','Mehaga',82800000,1,'Zmaja od Bosne bb',1,'123453452345');
INSERT INTO "vehicle" VALUES (1,1,'Golf','1234154123','A12-O-123',1);
INSERT INTO "manufacturer" VALUES (1,'Volkswagen');
INSERT INTO "manufacturer" VALUES (2,'Renault');
INSERT INTO "manufacturer" VALUES (3,'Ford');
INSERT INTO "place" VALUES (1,'Sarajevo','71000');
INSERT INTO "place" VALUES (2,'Tuzla','72000');
COMMIT;
